package org.wlgzs.index_evaluation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.index_evaluation.dao.CollegeMapper;
import org.wlgzs.index_evaluation.dao.StudentQualityMapper;
import org.wlgzs.index_evaluation.pojo.College;
import org.wlgzs.index_evaluation.pojo.Grade;
import org.wlgzs.index_evaluation.pojo.Major;
import org.wlgzs.index_evaluation.pojo.StudentQuality;
import org.wlgzs.index_evaluation.service.GradeService;
import org.wlgzs.index_evaluation.service.MajorService;
import org.wlgzs.index_evaluation.service.StudentQualityService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

/**
 * @author 武凯焱
 * @date 2019/1/15 17:34
 * @Description:
 */
@Service
@Log4j2
public class StudentQualityServiceImpl extends ServiceImpl<StudentQualityMapper, StudentQuality> implements StudentQualityService {
    @Resource
    MajorService majorService;
    @Resource
    private CollegeMapper collegeMapper;
    @Resource
    private GradeService gradeService;

    @Override
    public boolean importExcel(MultipartFile file, String year) throws IOException {
        List<StudentQuality> studentQualityList = new ArrayList<>();
        String fileName = file.getOriginalFilename();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            return false;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            wb = new XSSFWorkbook(is);
        } else {
            wb = new HSSFWorkbook(is);
        }
        //开始处理第一个表单普通本科专业认可度
        Sheet sheet = wb.getSheetAt(0);
        int mark = 0;
        //找出理科在哪一行
        for (int j = 3; j <= sheet.getLastRowNum(); j++) {
            Row row1 = sheet.getRow(j);
            if (row1.getCell(0).getStringCellValue().equals("理科")) {
                mark = j;
                break;
            }
        }
        //找出文科的三個最高值
        double[] wMax = findMax(3, mark, sheet);
        double wmaxFistVolunteerNum = wMax[0]; // 文科专业1志愿报考人数最高值
        double wmaxAfterVolunteerNum = wMax[1]; //文科专业2-5志愿报考人数最高值
        double maxAverage = wMax[2];           //文科录取平均分最高值


        //找出理科的三個最高值
        double[] lMax = findMax(mark + 1, sheet.getLastRowNum(), sheet);
        double lmaxFistVolunteerNum = lMax[0]; // 理科专业1志愿报考人数最高值
        double lmaxAfterVolunteerNum = lMax[1]; //理科专业2-5志愿报考人数最高值
        double lmaxAverage = lMax[2];           //理科录取平均分最高值
        for (int i = 2; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);

            if (row == null) {
                continue;
            }
            row.getCell(1).setCellType(Cell.CELL_TYPE_NUMERIC);
            row.getCell(2).setCellType(Cell.CELL_TYPE_NUMERIC);
            row.getCell(3).setCellType(Cell.CELL_TYPE_NUMERIC);
            row.getCell(4).setCellType(Cell.CELL_TYPE_NUMERIC);
            String majorName = row.getCell(0).getStringCellValue();
            double fist_volunteer_num = row.getCell(1).getNumericCellValue();
            double studentsNum = row.getCell(2).getNumericCellValue();
            if (majorName == null || majorName.equals("")) {
                continue;
            }
            double afterVolunteerNum = row.getCell(3).getNumericCellValue();
            double average_score = row.getCell(4).getNumericCellValue();
            //mark为理科位置的行数 处理文科的数据
            double majorRecognition = 0;
            double collegeEntrance = 0;
            double majorAdvantage = 0;
            if (i < mark) {
                //System.out.println(fist_volunteer_num+"gdrhgertdyh");
                //专业认可度 = (专业1志愿报考人数/总数)/(文科专业志愿报考人数最高值/总数)*100*0.7 + (2-5理科志愿报考人数/总数)/(文科志愿报考人数最高值/学生总数)*100*0.3
                majorRecognition = (fist_volunteer_num / studentsNum) / (wmaxFistVolunteerNum / studentsNum) * 100 * 0.7 + (afterVolunteerNum / studentsNum) / (wmaxAfterVolunteerNum / studentsNum) * 100 * 0.3;
                collegeEntrance = (average_score / maxAverage) * 100; //高考成绩与文科最高值之比
                //专业优势 = （专业认可度原始*0.557 + 高考成绩与最高值之比*0.433）*0.445
                majorAdvantage = (majorRecognition * 0.557 + collegeEntrance * 0.443) * 0.445;
            } else if (i == mark) {
                continue;
            }
            //处理理科数据
            else {
                majorRecognition = (fist_volunteer_num / studentsNum) / (lmaxFistVolunteerNum / studentsNum) * 100 * 0.7 + (afterVolunteerNum / studentsNum) / (lmaxAfterVolunteerNum / studentsNum) * 100 * 0.3;
                collegeEntrance = (average_score / lmaxAverage) * 100;
                majorAdvantage = (majorRecognition * 0.557 + collegeEntrance * 0.443) * 0.545;
            }
            StudentQuality studentQuality = new StudentQuality(majorName, (int) fist_volunteer_num, (int) studentsNum, (int) afterVolunteerNum, reserveDecimal(average_score, 3), reserveDecimal(majorRecognition, 3), reserveDecimal(collegeEntrance, 3), reserveDecimal(majorAdvantage, 3), Integer.parseInt(year));
            studentQualityList.add(studentQuality);
        }
        add(studentQualityList);
        //处理艺术和体育类的表单
        for (int l = 1; l <= 2; l++) {
            Sheet sheet3 = wb.getSheetAt(l);
            //找出理科在哪一行
            int mark1 = 0;
            for (int j = 3; j <= sheet3.getLastRowNum(); j++) {
                Row row1 = sheet3.getRow(j);
                if (row1.getCell(0).getStringCellValue().equals("理科")) {
                    mark1 = j;
                    break;
                }
            }
            //找出1志愿比值的最高值
            //文科1志愿比值最高值
            double wmaxValue = findTheMax(2, mark1 - 1, sheet3);
            //理科1志愿比值最高值
            double lmaxValue = findTheMax(mark1 + 1, sheet3.getLastRowNum(), sheet3);
            for (int j = 2; j <= sheet3.getLastRowNum(); j++) {
                Row row = sheet3.getRow(j);
                if (row == null || row.getCell(1) == null || row.getCell(1).getNumericCellValue() == 0) {
                    continue;
                }
                row.getCell(1).setCellType(Cell.CELL_TYPE_NUMERIC);
                row.getCell(2).setCellType(Cell.CELL_TYPE_NUMERIC);
                row.getCell(3).setCellType(Cell.CELL_TYPE_NUMERIC);
                row.getCell(4).setCellType(Cell.CELL_TYPE_NUMERIC);
                double num = row.getCell(1).getNumericCellValue();
                double nums = row.getCell(2).getNumericCellValue();
                double average_score = row.getCell(3).getNumericCellValue();
                double maxScore = row.getCell(4).getNumericCellValue();
                //计算文科
                if (j < mark1) {
                    String major = row.getCell(0).getStringCellValue();
                    double majorRecognition = ((num / nums) / wmaxValue) * 100;
                    double collegeEntrance = (average_score / maxScore) * 100;
                    double majorAdvantage = majorRecognition * 0.557 + collegeEntrance * 0.443;
                    StudentQuality studentQuality = new StudentQuality(major, reserveDecimal(average_score, 3), reserveDecimal(majorRecognition, 3), reserveDecimal(collegeEntrance, 3), reserveDecimal(majorAdvantage, 3), Integer.parseInt(year));
                    baseMapper.insert(studentQuality);
                }
                //计算理科
                else if (j > mark1) {
                    String major = row.getCell(0).getStringCellValue();
                    QueryWrapper<StudentQuality> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("year", year);
                    queryWrapper.eq("major_name", major);
                    //查询是否与文科专业相同，若相同求平均值再更新数据，否则算好后直接写入
                    List<StudentQuality> list = baseMapper.selectList(queryWrapper);
                    if (list.size() != 0) {
                        StudentQuality studentQuality = list.get(0);
                        double majorRecognition = (((num / nums) / lmaxValue) * 100 + studentQuality.getMajorRecognition()) / 2;
                        double collegeEntrance = ((average_score / maxScore) * 100 + studentQuality.getCollegeEntrance()) / 2;
                        double majorAdvantage = majorRecognition * 0.557 + collegeEntrance * 0.443;
                        studentQuality.setMajorAdvantage(reserveDecimal(majorAdvantage, 3));
                        studentQuality.setCollegeEntrance(reserveDecimal(collegeEntrance, 3));
                        studentQuality.setMajorRecognition(reserveDecimal(majorRecognition, 3));
                        baseMapper.updateById(studentQuality);
                    } else {
                        double majorRecognition = ((num / nums) / wmaxValue) * 100;
                        double collegeEntrance = (average_score / maxScore) * 100;
                        double majorAdvantage = majorRecognition * 0.557 + collegeEntrance * 0.443;
                        StudentQuality student = new StudentQuality(major, reserveDecimal(average_score, 3), reserveDecimal(majorAdvantage, 3), reserveDecimal(collegeEntrance, 3), reserveDecimal(majorAdvantage, 3), Integer.parseInt(year));
                        baseMapper.insert(student);
                    }
                }

            }

        }

        //开始处理对口本科数据表单
        for (int k = 3; k <= 4; k++) {
            Sheet sheet1 = wb.getSheetAt(k);
            List<Double> list = new ArrayList<>(); //存放平均值/满分 的集合
            //找出对口和专升本生本科的最高值
            for (int i = 1; i <= sheet1.getLastRowNum(); i++) {
                Row row = sheet1.getRow(i);
                //  System.out.println(sheet1.getLastRowNum()+"dsgsdg");
                if (row == null || row.getCell(1) == null) {
                    continue;
                }
                row.getCell(2).setCellType(Cell.CELL_TYPE_NUMERIC);
                row.getCell(3).setCellType(Cell.CELL_TYPE_NUMERIC);
                double averageScore = row.getCell(2).getNumericCellValue();
                double fullMark = row.getCell(3).getNumericCellValue();
                list.add(averageScore / fullMark);
            }
            double maxNum = 0;
            //找出list集合最高值
            for (Double temp : list
                    ) {
                if (maxNum < temp) {
                    maxNum = temp;
                }
            }
            for (int i = 1; i <= sheet1.getLastRowNum(); i++) {
                Row row = sheet1.getRow(i);
                if (row == null || row.getCell(1) == null) {
                    continue;
                }
                String majorName = row.getCell(1).getStringCellValue();
                if (majorName == null || majorName.equals("")) {
                    continue;
                }
                QueryWrapper<StudentQuality> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("major_name", majorName);
                queryWrapper.eq("year", year);
                //查找是否存在与专升本相同的专业
                List<StudentQuality> studentList = baseMapper.selectList(queryWrapper);
                double score = (list.get(i - 1) / maxNum) * 100;
                if (studentList == null || studentList.size() <= 0) {
                    StudentQuality studentQuality = new StudentQuality();
                    studentQuality.setMajorName(majorName);
                    studentQuality.setCollegeEntrance(reserveDecimal(score, 3));
                    studentQuality.setMajorAdvantage(reserveDecimal(score * 0.443, 3));
                    studentQuality.setYear(Integer.parseInt(year));
                    baseMapper.insert(studentQuality);
                } else {
                    StudentQuality studentQuality = studentList.get(0);
                    double entrance = studentQuality.getCollegeEntrance();
                    double result = (entrance + score) / 2;
                    double majorRecognition = studentQuality.getMajorRecognition();
                    double advantage = majorRecognition * 0.557 + result * 0.443;
                    studentQuality.setCollegeEntrance(reserveDecimal(result, 3));
                    studentQuality.setMajorAdvantage(reserveDecimal(advantage, 3));
                    baseMapper.updateById(studentQuality);

                }
            }
        }

        Sheet sheet5 = wb.getSheetAt(5);
        QueryWrapper<StudentQuality> wrapper = new QueryWrapper<StudentQuality>();
        wrapper.eq("year", year);
        List<StudentQuality> qualities = baseMapper.selectList(wrapper);
        Map<String, StudentQuality> studentQualityMap = new HashMap<>();
        for (StudentQuality studentQuality : qualities) {
            studentQualityMap.put(studentQuality.getMajorName(), studentQuality);
        }
        for (int i = 1; i <= sheet5.getLastRowNum(); i++) {
            Row row = sheet5.getRow(i);
            if (row == null || row.getCell(0) == null || row.getCell(0).getStringCellValue().equals("")) {
                continue;
            }
            row.getCell(1).setCellType(Cell.CELL_TYPE_NUMERIC);
            String colleage = row.getCell(0).getStringCellValue();
            double yieldRate = row.getCell(1).getNumericCellValue();
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("collage_name", colleage);
            List<Major> majors = majorService.list(queryWrapper);
            List<StudentQuality> studentQualitys = new ArrayList<>();
            double result = 0;
            double result_average;
            double avrageMajorAdvantage;
            for (Major major : majors) {
                StudentQuality student = studentQualityMap.get(major.getMajorName());
                if (student != null) {
                    result += student.getMajorAdvantage();
                    studentQualitys.add(student);
                } else {
                    continue;
                }
            }
            result_average = result / majors.size();
            avrageMajorAdvantage = (result_average + yieldRate * 0.455) * 0.1008;
            for (StudentQuality studentQuality : studentQualitys) {
                studentQuality.setColleageAdvantage(reserveDecimal(result_average, 3));
                studentQuality.setYieldRate(reserveDecimal(yieldRate, 3));
                studentQuality.setColleageQuality(reserveDecimal(avrageMajorAdvantage, 3));
                studentQuality.setColleageName(colleage.trim());
                System.out.println(studentQuality.toString());
                baseMapper.updateById(studentQuality);
            }
        }
        return true;
    }

    public double[] findMax(int index, int end, Sheet sheet) {
        double wmaxFistVolunteerNum = 0; // 文科专业1志愿报考人数最高值
        double wmaxAfterVolunteerNum = 0; //文科专业2-5志愿报考人数最高值
        double maxAverage = 0;           //文科录取平均分最高值
        for (int j = index; j < end; j++) {
            Row row1 = sheet.getRow(j);
            if (row1.getCell(0) == null) {
                break;
            }
            row1.getCell(1).setCellType(Cell.CELL_TYPE_NUMERIC);
            row1.getCell(3).setCellType(Cell.CELL_TYPE_NUMERIC);
            row1.getCell(4).setCellType(Cell.CELL_TYPE_NUMERIC);
            double fisteTemp = row1.getCell(1).getNumericCellValue();
            double afterTemp = row1.getCell(3).getNumericCellValue();
            double average = row1.getCell(4).getNumericCellValue();
            if (wmaxFistVolunteerNum < fisteTemp) {
                wmaxFistVolunteerNum = fisteTemp;
            }
            if (wmaxAfterVolunteerNum < afterTemp) {
                wmaxAfterVolunteerNum = afterTemp;
            }
            if (maxAverage < average) {
                maxAverage = average;
            }
        }
        double[] result = {wmaxFistVolunteerNum, wmaxAfterVolunteerNum, maxAverage};
        return result;
    }

    //找出体育艺术的最高值
    public double findTheMax(int index, int end, Sheet sheet) {
        List<Double> arrayList = new ArrayList();
        for (int j = index; j <= end; j++) {
            Row row1 = sheet.getRow(j);
            if (row1.getCell(0) == null) {
                break;
            }
            row1.getCell(1).setCellType(Cell.CELL_TYPE_NUMERIC);
            row1.getCell(2).setCellType(Cell.CELL_TYPE_NUMERIC);
            arrayList.add(row1.getCell(1).getNumericCellValue() / row1.getCell(2).getNumericCellValue());
        }
        double maxValue = 0;
        for (double temp : arrayList
                ) {
            if (maxValue < temp) {
                maxValue = temp;
            }
        }
        return maxValue;
    }

    public void add(List<StudentQuality> studentQualityList) {
        for (StudentQuality student : studentQualityList
                ) {
            baseMapper.insert(student);
        }
    }

    public double reserveDecimal(double num, int n) {
        double divid = Math.pow(10, n);
        return (double) Math.round(num * divid) / divid;

    }

    //删除数据
    public boolean delete(Integer year) {
        QueryWrapper<StudentQuality> queryWrapper = new QueryWrapper<>();
        QueryWrapper<Grade> wrapper = new QueryWrapper<>();
        if (year == null) {
            return false;
        } else {
            queryWrapper.eq("year", year);
            List<Grade> grades   = gradeService.list(wrapper);
            List<StudentQuality> studentQualityList = baseMapper.selectList(queryWrapper);
            if (studentQualityList != null && grades!=null) {
                for (StudentQuality student : studentQualityList) {
                    baseMapper.deleteById(student.getQualityId());
                }
                for (Grade grade: grades) {
                    gradeService.removeById(grade.getGradeId());
                }
            } else {
                return false;
            }
        }
        return true;
    }

    public void exportData(int year, HttpServletResponse response) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("信息表1");
        //设置要导出的文件的名字
        String fileName = year + "生源质量结果.xls";
        fileName = URLEncoder.encode(fileName, "UTF-8");
        int rowNum = 1;
        String[] headers = {"学院", "专业", "专业认可度原始", "专业认可度55.7", "高考成绩与最高值比", "高考成绩44.3", "专业优势54.5", "分学院优势", "报到率45.5"};
        HSSFRow row = sheet.createRow(0);
        //设置行高
        row.setHeightInPoints(42);
        //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
        sheet.setColumnWidth(0, 17 * 256);
        sheet.setColumnWidth(1, 22 * 256);
        sheet.setColumnWidth(2, 22 * 256);
        sheet.setColumnWidth(3, 22 * 256);
        sheet.setColumnWidth(4, 22 * 256);
        sheet.setColumnWidth(5, 22 * 256);
        sheet.setColumnWidth(6, 22 * 256);
        sheet.setColumnWidth(7, 22 * 256);
        sheet.setColumnWidth(8, 22 * 256);
        //其他表样式
        HSSFCellStyle style = workbook.createCellStyle();
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);//设置字体大小
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//设置字体水平居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        style.setFont(font);
        //表头样式
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        HSSFFont font2 = workbook.createFont();//其他字体样式
        font2.setFontName("宋体");
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        font2.setFontHeightInPoints((short) 10);//设置字体大小
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);//设置字体水平居中
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        style2.setFont(font2);
        //在excel表中添加表头
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellStyle(style2);
            cell.setCellValue(text);
        }
        HSSFCell cell;
        QueryWrapper<College> queryWrapper = new QueryWrapper<>();
        List<College> colleges = collegeMapper.selectList(queryWrapper);
        for (College college : colleges
                ) {
            QueryWrapper<StudentQuality> wrapper = new QueryWrapper<>();
            wrapper.eq("colleage_name", college.getCollegeName());
            wrapper.eq("year", year);
            List<StudentQuality> list = baseMapper.selectList(wrapper);
            for (StudentQuality studentQuality : list
                    ) {
                if (studentQuality == null) {
                    continue;
                }
                log.info(studentQuality.toString());
                HSSFRow row1 = sheet.createRow(rowNum);
                row1.setHeightInPoints(25);
                cell = row1.createCell(0);
                cell.setCellValue(college.getCollegeName());
                cell.setCellStyle(style);
                cell = row1.createCell(1);
                cell.setCellValue(studentQuality.getMajorName());
                cell.setCellStyle(style);
                cell = row1.createCell(2);
                if (studentQuality.getMajorRecognition() != null) {
                    cell.setCellValue(studentQuality.getMajorRecognition());
                }
                cell.setCellStyle(style);
                cell = row1.createCell(3);
                if (studentQuality.getMajorRecognition() != null) {
                    cell.setCellValue(reserveDecimal(studentQuality.getMajorRecognition() * 0.557, 3));
                }
                cell.setCellStyle(style);
                cell = row1.createCell(4);
                cell.setCellValue(reserveDecimal(studentQuality.getCollegeEntrance(), 3));
                cell.setCellStyle(style);
                cell = row1.createCell(5);
                cell.setCellValue(reserveDecimal(studentQuality.getCollegeEntrance() * 0.443, 3));
                cell.setCellStyle(style);
                cell = row1.createCell(6);
                cell.setCellValue(studentQuality.getMajorAdvantage());
                cell.setCellStyle(style);
                cell = row1.createCell(7);
                cell.setCellValue(studentQuality.getColleageAdvantage());
                cell.setCellStyle(style);
                cell = row1.createCell(8);
                cell.setCellValue(studentQuality.getYieldRate());
                cell.setCellStyle(style);
                rowNum++;
            }


        }
        HSSFSheet sheet1 = workbook.createSheet("生源质量指数");
        int rowNum1 = 1;
        String[] headers1 = {"学院", "专业优势54.5", "报到率45.5", "生源质量10.08"};
        HSSFRow row1 = sheet1.createRow(0);
        row1.setHeightInPoints(42);
        //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
        sheet1.setColumnWidth(0, 17 * 256);
        sheet1.setColumnWidth(1, 22 * 256);
        sheet1.setColumnWidth(2, 22 * 256);
        sheet1.setColumnWidth(3, 22 * 256);
        for (int i = 0; i < headers1.length; i++) {
            HSSFCell cell1 = row1.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers1[i]);
            cell1.setCellStyle(style2);
            cell1.setCellValue(text);
        }
        HSSFCell cell1;
        for (College college : colleges
                ) {
            row1 = sheet1.createRow(rowNum1);
            row1.setHeightInPoints(42);
            QueryWrapper<StudentQuality> stuQueryWrapper = new QueryWrapper<>();
            //System.out.println("fhjksdhfklasdjgkn."+college.getCollegeName());
            stuQueryWrapper.eq("colleage_name", college.getCollegeName());
            stuQueryWrapper.eq("year",year);
            List<StudentQuality> studentQualitys = baseMapper.selectList(stuQueryWrapper.last("limit 1"));
            StudentQuality studentQuality = studentQualitys.get(0);
            if (studentQualitys == null) {
                continue;
            }
            cell1 = row1.createCell(0);
            cell1.setCellValue(college.getCollegeName());
            cell1.setCellStyle(style);
            cell = row1.createCell(1);
            cell.setCellValue(studentQuality.getColleageAdvantage());
            cell.setCellStyle(style);
            cell = row1.createCell(2);
            cell.setCellValue(studentQuality.getYieldRate());
            cell.setCellStyle(style);
            cell = row1.createCell(3);
            cell.setCellValue(studentQuality.getColleageQuality());
            cell.setCellStyle(style);
            rowNum1++;
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

    public List<StudentQuality> getQualityIndex(int year) {
        QueryWrapper<College> query = new QueryWrapper<College>();
        List<College> colleges = collegeMapper.selectList(query);
        QueryWrapper<StudentQuality> queryWrapper = new QueryWrapper<StudentQuality>();
        queryWrapper.eq("year", year);
        List<StudentQuality> studentQualitys = baseMapper.selectList(queryWrapper);
        Map<String, StudentQuality> studentQualityMap = new HashMap<>();
        for (StudentQuality studentQuality : studentQualitys
                ) {
            studentQualityMap.put(studentQuality.getColleageName(), studentQuality);
        }
        List<StudentQuality> array = new ArrayList<>();
        for (int i = 0; i < colleges.size(); i++) {
            College collage = colleges.get(i);
            StudentQuality studentQuality = studentQualityMap.get(collage.getCollegeName());
            array.add(i, studentQuality);
        }
        return array;
    }

    public void download(HttpServletResponse res) {
        try {
            String fileName = "就业工作考核数据分析系统模板.zip";
            res.setHeader("content-type", "application/octet-stream;charset=UTF-8");
            res.setContentType("application/octet-stream");
            res.setCharacterEncoding("UTF-8");
            res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "iso-8859-1"));
            byte[] buff = new byte[1024];
            BufferedInputStream bis = null;
            OutputStream os = null;
            try {
                os = res.getOutputStream();
                bis = new BufferedInputStream(new FileInputStream(new File("./template/" + fileName)));
                int i = bis.read(buff);
                while (i != -1) {
                    os.write(buff, 0, buff.length);
                    os.flush();
                    i = bis.read(buff);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {

                        bis.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (os != null) {
                    try {

                        os.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public boolean saveFile(MultipartFile file, String filePath) {
        if (!file.isEmpty()) {
            File saveFile = new File(filePath + "/" + file.getOriginalFilename());
            if (!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }
            try {
                FileOutputStream outputStream = new FileOutputStream(saveFile);
                BufferedOutputStream bf = new BufferedOutputStream(outputStream);
                bf.write(file.getBytes());
                bf.flush();
                bf.close();
                outputStream.close();
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    //上传并保存和解压zip文件
    public boolean upload(MultipartFile file, String string) throws IOException {
        int year = Integer.parseInt(string);
        if (saveFile(file, "./upload")) {
            String zipPath = "./upload/" + file.getOriginalFilename();
            ZipFile zipFile = new ZipFile(zipPath, "GBK");
            String[] names = new String[4];
            int i = 0;
            for (Enumeration<ZipEntry> enumeration = zipFile.getEntries(); enumeration.hasMoreElements(); ) {
                ZipEntry zipEntry = enumeration.nextElement();
                names[i++] = zipEntry.getName();
            }
            if (!uncompress(zipPath)) {
                return false;
            }
            zipFile.close();
            for (int j = 0; j < names.length; j++) {
                String str = names[j];
                if (str.contains("文") || str.contains("理"))
                    analysisExcel(str, year);  //解析文件
            }
            int k = 0;
            for (int j = 0; j < names.length; j++) {
                String str = names[j];
                if (str.contains("报到率")) {
                    k = j;
                    continue;
                }
                if (!(str.contains("文") || str.contains("理")))
                    analysisExcel(str, year);  //解析文件

            }
            QueryWrapper<StudentQuality> wenWrapper = new QueryWrapper<>();
            wenWrapper.eq("mark", 2);
            wenWrapper.eq("year", year);
            QueryWrapper<StudentQuality> liWrapper = new QueryWrapper<>();
            liWrapper.eq("mark", 1);
            liWrapper.eq("year", year);
            List<StudentQuality> wenlist = baseMapper.selectList(wenWrapper);
            List<StudentQuality> liList = baseMapper.selectList(liWrapper);
            double wenMax = findListMax(wenlist, "score");
            double liMax = findListMax(liList, "score");
            List<StudentQuality> studentQualities = new ArrayList<>();
            studentQualities.addAll(liList);
            studentQualities.addAll(wenlist);
            for (StudentQuality su :  studentQualities) {
                int studentsNum = su.getStudentsNum();
                double majorRecognition = 0;
                double majorAdvantage =  0;
                double collegeEntrance = 0;
                if (su.getMark() == 2) {
                    //专业认可度 = (专业1志愿报考人数/总数)/(文科专业志愿报考人数最高值/总数)*100*0.7 + (2-5理科志愿报考人数/总数*4)/(文科志愿报考人数最高值/学生总数)*100*0.3
                     majorRecognition = ( (su.getFistVolunteerNum() / (double)studentsNum) / (findListMax(wenlist, "firstNum") / studentsNum) * 100 * 0.7 + ((double)su.getAfterVolunteerNum() / studentsNum * 4) / (findListMax(wenlist, "afterNum") / studentsNum*4) * 100 * 0.3);
                     collegeEntrance = (su.getAverageScore() / wenMax)*100;
                    //专业优势 = （专业认可度原始*0.557 + 高考成绩与最高值之比*0.433）*0.445
                     majorAdvantage = (majorRecognition * 0.557 + collegeEntrance * 0.443) * 0.445;
                } else if (su.getMark() == 1) {
                    //专业认可度 = (专业1志愿报考人数/总数)/(文科专业志愿报考人数最高值/总数)*100*0.7 + (2-5理科志愿报考人数/总数*4)/(文科志愿报考人数最高值/学生总数)*100*0.3
                     majorRecognition = ((su.getFistVolunteerNum() / (double)studentsNum) / (findListMax(liList, "firstNum") / studentsNum) * 100 * 0.7 + ((double)su.getAfterVolunteerNum() / studentsNum * 4) / (findListMax(liList, "afterNum") / studentsNum*4) * 100 * 0.3);
                     collegeEntrance = (su.getAverageScore() / liMax)*100;
                    //专业优势 = （专业认可度原始*0.557 + 高考成绩与最高值之比*0.433）*0.445
                     majorAdvantage = (majorRecognition * 0.557 + collegeEntrance * 0.443) * 0.445;
                }
                su.setCollegeEntrance(reserveDecimal(collegeEntrance,4));
                su.setMajorRecognition(reserveDecimal(majorRecognition,4));
                su.setMajorAdvantage(reserveDecimal(majorAdvantage,4));
                baseMapper.updateById(su);
            }
            analysisExcel(names[k],year);
            deleteFile("./upload");
            return true;
        } else {
            return false;
        }

    }

    //找出集合的最大值
    public static double findListMax(List<StudentQuality> list, String mark) {
        double max = 0;
        if (mark.equals("score")) {
            for (StudentQuality su : list) {
                if (su.getAverageScore() > max) {
                    max = su.getAverageScore();
                }
            }
        } else if (mark.equals("firstNum")) {  //找第一志愿最大值
            for (StudentQuality su : list) {
                if (su.getFistVolunteerNum() > max) {
                    max = su.getFistVolunteerNum();
                }
            }
        } else if (mark.equals("afterNum")) {
            for (StudentQuality su : list) {
                if (su.getAfterVolunteerNum() > max) {
                    max = su.getAfterVolunteerNum();
                }
            }
        }
        return max;
    }

    //解压zip文件
    public boolean uncompress(String zipPath) throws IOException {
        String outPath = "./upload/";
        ZipFile zipFile = new ZipFile(zipPath, "GBK");
        for (Enumeration<ZipEntry> enumeration = zipFile.getEntries(); enumeration.hasMoreElements(); ) {
            ZipEntry zipEntry = enumeration.nextElement(); //获取zipFile中元素
            //在这判断excel名字
            if (!zipEntry.getName().endsWith(File.separator)) {
                System.out.println("正在解压文件: " + zipEntry.getName());
                File f = new File(outPath + zipEntry.getName());
                if (!f.getParentFile().exists()) {
                    f.getParentFile().mkdirs();
                }
                OutputStream os = new FileOutputStream(outPath + zipEntry.getName());
                BufferedOutputStream bfo = new BufferedOutputStream(os);
                InputStream is = zipFile.getInputStream(zipEntry); //读取元素
                BufferedInputStream bfi = new BufferedInputStream(is);
                CheckedInputStream cos = new CheckedInputStream(bfi, new CRC32()); //检查读取流，采用CRC32算法，保证文件的一致性
                byte[] b = new byte[1024]; //字节数组，每次读取1024个字节
                //循环读取压缩文件的值
                while (cos.read(b) != -1) {
                    bfo.write(b); //写入到新文件
                }
                cos.close();
                bfi.close();
                is.close();
                bfo.close();
                os.close();
            } else {
                //如果为空文件夹，则创建该文件夹
                new File(outPath + zipEntry.getName()).mkdirs();

            }
        }
        System.out.println("解压完成");
        zipFile.close();
        return true;
    }

    //解析Excel文件
    public boolean analysisExcel(String fileName, int year) {
        if (fileName.equals("")) {
            return false;
        }
        if (fileName.contains("理") || fileName.contains("文")) {
            boolean isTrue = fileName.contains("理");
            int last = fileName.lastIndexOf(".");
            String number = fileName.substring(1, last);
            int sums = Integer.parseInt(number);
            try {
                FileInputStream is = new FileInputStream(new File("./upload/" + fileName));
                System.out.println("./upload/" + fileName);
                if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
                    return false;
                }
                Workbook wb = null;
                if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
                    wb = new XSSFWorkbook(is);
                } else {
                    wb = new HSSFWorkbook(is);
                }
                Sheet sheet = wb.getSheetAt(0);
                for (int i = 1; i < sheet.getLastRowNum(); i++) {   // 遍历excel每行的数据
                    Row row = sheet.getRow(i);
                    Cell cell;
                    if (isTrue) {
                        cell = row.getCell(15);
                    } else {
                        cell = row.getCell(14);
                    }
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    String str = cell.getStringCellValue();
                    if (!str.equals("")) {
                        char ch = str.charAt(0);
                        String majorName;
                        if (!(str.contains("（") || str.contains("(")))
                            majorName = str.substring(1, str.length());
                        else {
                            int index = str.indexOf("（");
                            int index1 = str.indexOf("(");
                            if (index != -1)
                                majorName = str.substring(1, index);
                            else
                                majorName = str.substring(1, index1);
                        }
                        if (ch == '1') {   //该专业为第一志愿
                            if (isTrue)
                                findMajor(majorName, year, 1, 1, sums);
                            else
                                findMajor(majorName, year, 1, 2, sums);
                        } else if (ch == '2' || ch == '3' || ch == '4' || ch == '5') {
                            if (isTrue)
                                findMajor(majorName, year, 2, 1, sums);
                            else
                                findMajor(majorName, year, 2, 2, sums);
                        }
                        is.close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (fileName.contains("生源质量总数据")) {
            try {
                FileInputStream is = new FileInputStream(new File("./upload/" + fileName));
                System.out.println("./upload/" + fileName);
                if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
                    return false;
                }
                Workbook wb = null;
                if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
                    wb = new XSSFWorkbook(is);
                } else {
                    wb = new HSSFWorkbook(is);
                }
                Sheet sheet = wb.getSheetAt(0);
                for (int i = 1; i < sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    Cell cell = row.getCell(1); //省份
                    Cell admissionCell = row.getCell(18);  //  录取批次
                    Cell lengthOfSchoolCell = row.getCell(7);  //学年制
                    Cell admissionsCell = row.getCell(19); // 录取方式
                    Cell schoolNameCell = row.getCell(0);
                    Cell majorNameCell = row.getCell(6);
                    lengthOfSchoolCell.setCellType(Cell.CELL_TYPE_STRING);
                    String province = cell.getStringCellValue();
                    String admission = admissionCell.getStringCellValue();
                    String lengthOfSchool = lengthOfSchoolCell.getStringCellValue();
                    String admissions = admissionsCell.getStringCellValue();
                    String schoolName = schoolNameCell.getStringCellValue();
                    String majorName = majorNameCell.getStringCellValue();
                    majorName = jundgeMajorname(majorName);
                    if (check(province, admission, lengthOfSchool, admissions)) {
                        Cell gradeCell = row.getCell(26);    //
                        gradeCell.setCellType(Cell.CELL_TYPE_NUMERIC);
                        double grade = gradeCell.getNumericCellValue();
                        Grade grades = new Grade();
                        grades.setCollegeName(schoolName);
                        grades.setMajor_name(majorName);
                        grades.setCollegeGrade(grade);
                        grades.setYear(year);
                        gradeService.save(grades);
                    }
                }
                QueryWrapper<StudentQuality> wrapper = new QueryWrapper<>();
                wrapper.eq("year", year);
                List<StudentQuality> list = baseMapper.selectList(wrapper);
                for (StudentQuality stu : list) {
                    String majorName = stu.getMajorName();
                    QueryWrapper<Grade> wrap = new QueryWrapper<>();
                    wrap.eq("major_name", majorName);
                    wrapper.eq("year", year);
                    List<Grade> grades = new ArrayList<Grade>();
                    grades = gradeService.list(wrap);
                    double res = 0;
                    if (list != null && list.size() > 0) {
                        for (Grade grade : grades) {
                            res += grade.getCollegeGrade();
                        }
                        double average = res / grades.size();
                        stu.setAverageScore(average);
                        if (stu.getColleageName() == null || stu.getColleageName().equals("")) {
                            stu.setColleageName(grades.get(0).getCollegeName());
                            log.info(grades.get(0).getCollegeName());
                        }
                        baseMapper.updateById(stu);
                    } else {
                        continue;
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (fileName.contains("报到率")) {
            try {
                FileInputStream is = new FileInputStream(new File("./upload/" + fileName));
                System.out.println("./upload/" + fileName);
                if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
                    return false;
                }
                Workbook wb = null;
                if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
                    wb = new XSSFWorkbook(is);
                } else {
                    wb = new HSSFWorkbook(is);
                }

                Sheet sheet = wb.getSheetAt(0);
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row == null || row.getCell(0) == null || row.getCell(0).getStringCellValue().equals("")) {
                        continue;
                    }
                    row.getCell(1).setCellType(Cell.CELL_TYPE_NUMERIC);
                    String colleage = row.getCell(0).getStringCellValue();
                    double yieldRate = row.getCell(1).getNumericCellValue();
                    QueryWrapper<StudentQuality> queryWrapper = new QueryWrapper();
                    queryWrapper.eq("colleage_name", colleage);
                    queryWrapper.eq("year",year);
                    List<StudentQuality> studentQualitys = baseMapper.selectList(queryWrapper);
                    if (studentQualitys==null || studentQualitys.size()<=0){
                        StudentQuality stu = new StudentQuality();
                        stu.setMajorRecognition((double)100);
                        stu.setMajorAdvantage((double)100);
                        stu.setColleageAdvantage((double)100);
                        stu.setYear(year);
                        if (colleage .equals("体育学院")){
                            stu.setColleageName("体育学院");
                            stu.setYieldRate(yieldRate);
                           Double avrageMajorAdvantage = (100*0.545 + yieldRate * 0.455) * 0.1008;
                            stu.setColleageQuality(reserveDecimal(avrageMajorAdvantage,4));
                            baseMapper.insert(stu);
                        }
                       else if (colleage .equals("艺术学院")){
                            stu.setColleageName("艺术学院");
                            stu.setYieldRate(yieldRate);
                            double avrageMajorAdvantage = (100*0.545 + yieldRate * 0.455) * 0.1008;
                            stu.setColleageQuality(reserveDecimal(avrageMajorAdvantage,4));
                            baseMapper.insert(stu);
                        }

                        continue;
                    }
                    double result = 0;
                    double result_average;
                    double avrageMajorAdvantage;
                    for (StudentQuality stu : studentQualitys) {
                        if (stu != null) {
                            result += stu.getMajorAdvantage();
                        } else {
                            continue;
                        }
                    }
                    result_average = result / studentQualitys.size();
                    avrageMajorAdvantage = (result_average + yieldRate * 0.455) * 0.1008;
                    for (StudentQuality studentQuality : studentQualitys) {
                        studentQuality.setColleageAdvantage(reserveDecimal(result_average, 4));
                        studentQuality.setYieldRate(reserveDecimal(yieldRate, 4));
                        studentQuality.setColleageQuality(reserveDecimal(avrageMajorAdvantage, 4));
                        studentQuality.setColleageName(colleage.trim());
                        System.out.println(studentQuality.toString());
                        baseMapper.updateById(studentQuality);
                    }
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    /**
     * 筛选掉不符合条件的数据
     *
     * @param province       省份
     * @param admission      录取批次
     * @param lengthOfSchool 学制
     * @param admissions     录取方式
     * @return
     */
    public static boolean check(String province, String admission, String lengthOfSchool, String admissions) {
        if (!province.contains("河南")) {
            return false;
        }
        if (admission.contains("艺术") || admission.contains("体育")) {
            return false;
        }
        if (lengthOfSchool.equals("2")) {
            return false;
        }
        if (admissions.contains("保送生")) {
            return false;
        }
        return true;
    }
    //查找是否有此专业，若没有，则添加。若存在则在相应志愿项上加1

    /**
     * @param majorName     专业名字
     * @param year          年份
     * @param volunteerMark 1 表示为第一志愿 其它为2~5志愿
     * @param mark          文理科标识 1为文 2为理
     */
    public void findMajor(String majorName, int year, int volunteerMark, int mark, int students_sum) {
        QueryWrapper<StudentQuality> wrapper = new QueryWrapper<>();
        wrapper.eq("major_name", majorName);
        wrapper.eq("year", year);
        List<StudentQuality> list = baseMapper.selectList(wrapper);
        if (list == null || list.size() <= 0) {
            StudentQuality su = new StudentQuality();
            su.setYear(year);
            su.setMajorName(majorName);
            su.setStudentsNum(students_sum);
            if (volunteerMark == 1) {
                su.setFistVolunteerNum(1);
            } else {
                su.setAfterVolunteerNum(1);
            }
            if (mark == 1) {
                su.setMark(1);
            } else {
                su.setMark(2);
            }
            baseMapper.insert(su);
        } else {

            StudentQuality su;
            su = list.get(0);
            if (volunteerMark == 1) {
                su.setFistVolunteerNum(su.getFistVolunteerNum() + 1);
            } else {
                su.setAfterVolunteerNum(su.getAfterVolunteerNum() + 1);
            }
            baseMapper.updateById(su);

        }
    }

    // 判断专业名字是否符合规范
    public String jundgeMajorname(String str) {
        String majorName = str;
        if (!(str.contains("（") || str.contains("(")))
            return majorName;
        else {
            int index = str.indexOf("（");
            int index1 = str.indexOf("(");
            if (index != -1)
                majorName = str.substring(0, index);
            else
                majorName = str.substring(0, index1);
        }
        return majorName;
    }

    // 删除文件
    public boolean deleteFile(String url) {
        File file = new File(url);
        if (file.exists()) {

            deleteDir(file);
            return true;
        } else {
            return false;
        }
    }

    //  递归删除一个目录的文件
    boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] childens = dir.list();
            for (int i = 0; i < childens.length; i++) {
                log.info("ashffghghjkdhgjkdfhgdfdjghh");
                boolean isTrue = deleteDir(new File(dir, childens[i]));
                if (!isTrue) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

}