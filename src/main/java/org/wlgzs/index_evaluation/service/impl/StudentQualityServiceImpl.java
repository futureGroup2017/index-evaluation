package org.wlgzs.index_evaluation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.index_evaluation.dao.CollegeMapper;
import org.wlgzs.index_evaluation.dao.StudentQualityMapper;
import org.wlgzs.index_evaluation.pojo.College;
import org.wlgzs.index_evaluation.pojo.EmployerSatisfaction;
import org.wlgzs.index_evaluation.pojo.Major;
import org.wlgzs.index_evaluation.pojo.StudentQuality;
import org.wlgzs.index_evaluation.service.MajorService;
import org.wlgzs.index_evaluation.service.StudentQualityService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

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
                //专业认可度 = 专业1志愿报考人数/
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

        //开始处理对口本科数据表单
        for (int k = 1; k <= 2; k++) {
            Sheet sheet1 = wb.getSheetAt(k);
            List<Double> list = new ArrayList<>();
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
                StudentQuality studentQuality = studentQualityList.get(0);
                double score = (list.get(i - 1) / maxNum) * 100;
                if (studentQuality != null) {
                    double entrance = studentQuality.getCollegeEntrance();
                    double result = (entrance + score) / 2;
                    double majorRecognition = studentQuality.getMajorRecognition();
                    double advantage = majorRecognition * 0.557 + result * 0.443;
                    studentQuality.setCollegeEntrance(reserveDecimal(advantage, 3));
                    studentQuality.setMajorAdvantage(reserveDecimal(majorRecognition, 3));
                    baseMapper.updateById(studentQuality);
                } else {
                    continue;
                }
            }
        }
        //处理艺术和体育类的表单
        for (int l = 3; l <= 4; l++) {
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
            double wmaxValue = findTheMax(2, mark1, sheet3);
            //理科1志愿比值最高值
            double lmaxValue = findTheMax(mark + 1, sheet3.getLastRowNum(), sheet3);
            List<StudentQuality> studentList = new ArrayList<>();
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
                if (j < mark1) {
                    String major = row.getCell(0).getStringCellValue();
                    double majorRecognition = ((num / nums) / wmaxValue) * 100;
                    double collegeEntrance = (average_score / maxScore) * 100;
                    double majorAdvantage = majorRecognition * 0.557 + collegeEntrance * 0.443;
                    StudentQuality studentQuality = new StudentQuality(major, reserveDecimal(average_score, 3), reserveDecimal(majorRecognition, 3), reserveDecimal(collegeEntrance, 3), reserveDecimal(majorAdvantage, 3), Integer.parseInt(year));
                    baseMapper.insert(studentQuality);
                } else if (j > mark1) {
                    String major = row.getCell(0).getStringCellValue();
                    QueryWrapper<StudentQuality> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("year", year);
                    queryWrapper.eq("major_name", major);
                    //查询是否与文科专业相同，若相同求平均值再更新数据，否则算好后直接写入
                    List<StudentQuality> list = baseMapper.selectList(queryWrapper);

                    if (list.size() != 0) {
                        StudentQuality studentQuality = list.get(0);
                        double majorRecognition = (((num / nums) / wmaxValue) * 100 + studentQuality.getMajorRecognition()) / 2;
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
        Sheet sheet5 = wb.getSheetAt(5);
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
            for (Major major : majors
                    ) {
                QueryWrapper wrapper = new QueryWrapper();
                wrapper.eq("year", year);
                wrapper.eq("major_name", major.getMajorName());
                StudentQuality student = baseMapper.selectOne(wrapper);
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
                studentQuality.setColleageName(colleage);
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
        for (int j = index; j < end; j++) {
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
        if (year == null) {
            return false;
        } else {
            queryWrapper.eq("year", year);
            List<StudentQuality> studentQualityList = baseMapper.selectList(queryWrapper);
            if (studentQualityList != null) {
                for (StudentQuality student : studentQualityList
                        ) {
                    baseMapper.deleteById(student.getQualityId());
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
                cell.setCellValue(studentQuality.getMajorRecognition());
                cell.setCellStyle(style);
                cell = row1.createCell(3);
                cell.setCellValue(reserveDecimal(studentQuality.getMajorRecognition() * 0.557, 3));
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
            QueryWrapper<Major> majorQueryWrapper = new QueryWrapper<>();
            //System.out.println("fhjksdhfklasdjgkn."+college.getCollegeName());
            majorQueryWrapper.eq("collage_name", college.getCollegeName());
            List<Major> majorList = majorService.list(majorQueryWrapper);
            Major major = majorList.get(0);
            QueryWrapper query = new QueryWrapper();
            query.eq("major_name", major.getMajorName());
            query.eq("year", year);
            StudentQuality studentQuality = baseMapper.selectOne(query);
            if (studentQuality == null) {
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
                bis = new BufferedInputStream(new FileInputStream(new File(".//template//"+fileName)));
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
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}