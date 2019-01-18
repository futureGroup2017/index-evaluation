package org.wlgzs.index_evaluation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.index_evaluation.dao.StudentQualityMapper;
import org.wlgzs.index_evaluation.pojo.EmployerSatisfaction;
import org.wlgzs.index_evaluation.pojo.StudentQuality;
import org.wlgzs.index_evaluation.service.StudentQualityService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 武凯焱
 * @date 2019/1/15 17:34
 * @Description:
 */
@Service
@Log4j2
public class StudentQualityServiceImpl extends ServiceImpl<StudentQualityMapper, StudentQuality> implements StudentQualityService {

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
                majorRecognition = (fist_volunteer_num / studentsNum) / (wmaxFistVolunteerNum / studentsNum) * 100 * 0.7 + (afterVolunteerNum / studentsNum) / (wmaxAfterVolunteerNum / studentsNum) * 100 * 0.3;
                collegeEntrance = (average_score / maxAverage) * 100;
                majorAdvantage = (majorRecognition * 0.557 + collegeEntrance * 0.443) * 0.445;
            } else if (i == mark) {
                continue;
            }
            //处理理科数据
            else {
                majorRecognition = (fist_volunteer_num / studentsNum) / (lmaxFistVolunteerNum / studentsNum) * 100 * 0.7 + (afterVolunteerNum / studentsNum) / (lmaxAfterVolunteerNum / studentsNum) * 100 * 0.3;
                collegeEntrance = (average_score / lmaxAverage) * 100;
                majorAdvantage = (majorRecognition * 0.557 + collegeEntrance * 0.443) * 0.445;
            }
            StudentQuality studentQuality = new StudentQuality(majorName, (int) fist_volunteer_num, (int) studentsNum, (int) afterVolunteerNum, reserveDecimal(average_score, 3), reserveDecimal(majorRecognition, 3), reserveDecimal(collegeEntrance, 3), reserveDecimal(majorAdvantage, 3),Integer.parseInt(year));
            studentQualityList.add(studentQuality);
        }
        add(studentQualityList);

        //开始处理对口本科数据表单
        for (int k=1;k<=2;k++) {
            Sheet sheet1 = wb.getSheetAt(k);
            List<Double> list = new ArrayList<>();
            //找出对口和专升本生本科的最高值
            for (int i = 1; i <= sheet1.getLastRowNum(); i++) {
                Row row = sheet1.getRow(i);
              //  System.out.println(sheet1.getLastRowNum()+"dsgsdg");
                if ( row==null || row.getCell(1) == null) {
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
        for(int l=3; l<=4;l++) {
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
            System.out.println(wmaxValue + "fasdfsdfsdfsd");
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
            add(studentList);
        }
        return  true;
    }
    public  double[] findMax(int index,int end,Sheet sheet){
        double wmaxFistVolunteerNum = 0; // 文科专业1志愿报考人数最高值
        double wmaxAfterVolunteerNum = 0; //文科专业2-5志愿报考人数最高值
        double maxAverage = 0;           //文科录取平均分最高值
        for (int j = index; j < end; j++) {
            Row row1 = sheet.getRow(j);
            if (row1.getCell(0)==null){
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
            if (wmaxAfterVolunteerNum< afterTemp){
                wmaxAfterVolunteerNum = afterTemp;
            }
            if (maxAverage<average){
                maxAverage = average;
            }
        }
        double[] result = {wmaxFistVolunteerNum,wmaxAfterVolunteerNum,maxAverage};
        return result;
    }
    //找出体育艺术的最高值
    public double findTheMax(int index,int end,Sheet sheet){
       List<Double> arrayList  = new ArrayList();
        for (int j = index; j < end; j++) {
            Row row1 = sheet.getRow(j);
            if (row1.getCell(0) == null) {
                break;
            }
            row1.getCell(1).setCellType(Cell.CELL_TYPE_NUMERIC);
            row1.getCell(2).setCellType(Cell.CELL_TYPE_NUMERIC);
            arrayList.add(row1.getCell(1).getNumericCellValue()/row1.getCell(2).getNumericCellValue());
        }
        double maxValue = 0;
        for (double temp:arrayList
             ) {
            if (maxValue<temp){
                maxValue =temp ;
            }
        }
        return  maxValue;
    }
    public void add(List<StudentQuality> studentQualityList){
        for (StudentQuality student: studentQualityList
             ) {
            baseMapper.insert(student);
        }
    }
    public double reserveDecimal(double num,int n){
        double divid = Math.pow(10,n);
       return  (double)Math.round(num*divid)/divid;

    }
}