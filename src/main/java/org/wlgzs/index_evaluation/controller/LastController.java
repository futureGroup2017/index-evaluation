package org.wlgzs.index_evaluation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wlgzs.index_evaluation.pojo.*;
import org.wlgzs.index_evaluation.service.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author AlgerFan
 * @date Created in 2019/1/21 16
 * @Description
 */
@RestController
@Log4j2
public class LastController {
    @Resource
    private CollegeService collegeService;
    @Resource
    private StudentQualityService studentQualityService;
    @Resource
    private TeachersStructureService structureService;
    @Resource
    private EmploymentService employmentService;
    @Resource
    private EmploymentRateService rateService;
    @Resource
    private EmployerSatisfactionService satisfactionService;
    @Resource
    private EmploymentPracticeService practiceService;

    @RequestMapping("/lastExport")
    public void lastExport(int year, HttpServletResponse response) throws IOException {
        List<Last> lastList = new ArrayList<>();
        List<College> list = collegeService.list(null);
        for (College college : list) {
            lastList.add(new Last(college.getCollegeName()));
        }
        //生源质量指数10.08   colleageQuality
        List<StudentQuality> list1 = studentQualityService.getQualityIndex(year);
        log.info("list1: " + list1);
        for (StudentQuality studentQuality : list1) {
            if (studentQuality != null) {
                for (Last last : lastList) {
                    if (studentQuality.getColleageName().equals(last.getCollege())) {
                        last.setStudentQuality(String.valueOf(studentQuality.getColleageQuality()));
                    }
                }
            }
        }
        //师资结构指数11.07   A2
        QueryWrapper<TeachersStructure> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("year", year);
        List<TeachersStructure> list2 = structureService.list(queryWrapper2);
        log.info("list2: " + list2.size());

        for (TeachersStructure teachersStructure : list2) {
            if (teachersStructure != null) {
                for (Last last : lastList) {
                    if (teachersStructure.getCollegeName().equals(last.getCollege())) {
                        last.setTeachersStructure(String.valueOf(teachersStructure.getA2()));
                    }
                }
            }
        }
        //就业状态指数25.3   employmentStatus
        QueryWrapper<Employment> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("year", year);
        List<Employment> list3 = employmentService.list(queryWrapper3);
        log.info("list3: " + list3.size());
        for (Employment employment : list3) {
            if (employment != null) {
                for (Last last : lastList) {
                    if (employment.getCollege().equals(last.getCollege())) {
                        last.setEmployment(String.valueOf(employment.getEmploymentStatus()));
                    }
                }
            }
        }
        //就业率指数24.95    employmentRateIndex
        QueryWrapper<EmploymentRate> queryWrapper4 = new QueryWrapper<>();
        queryWrapper4.eq("year", year);
        List<EmploymentRate> list4 = rateService.list(queryWrapper4);
        log.info("就业率指数24.95  list4: " + list4.size());
        for (EmploymentRate rate : list4) {
            if (rate != null) {
                for (Last last : lastList) {
                    if (rate.getCollege().equals(last.getCollege())) {
                        last.setEmploymentRate(String.valueOf(rate.getEmploymentRateIndex()));
                    }
                }
            }
        }
        //用人满意度指数13.25   satisfactionIndex
        QueryWrapper<EmployerSatisfaction> queryWrapper5 = new QueryWrapper<>();
        queryWrapper5.eq("year", year);
        List<EmployerSatisfaction> list5 = satisfactionService.list(queryWrapper5);
        log.info("用人满意度指数13.25  list5: " + list5.size());
        for (EmployerSatisfaction satisfaction : list5) {
            if (satisfaction != null) {
                for (Last last : lastList) {
                    if (satisfaction.getCollege().equals(last.getCollege())) {
                        last.setEmployerSatisfaction(String.valueOf(satisfaction.getSatisfactionIndex()));
                    }
                }
            }
        }
        //就业创业实践指数15.35   practice
        QueryWrapper<EmploymentPractice> queryWrapper6 = new QueryWrapper<>();
        queryWrapper6.eq("year", year);
        List<EmploymentPractice> list6 = practiceService.list(queryWrapper6);
        log.info("就业创业实践指数15.35  list6: " + list6.size());
        for (EmploymentPractice practice : list6) {
            if (practice != null) {
                for (Last last : lastList) {
                    if (practice.getCollege().equals(last.getCollege())) {
                        last.setEmploymentPractice(String.valueOf(practice.getPractice()));
                    }
                }
            }
        }
        //最终指数
        double index;
        DecimalFormat decimalFormat = new DecimalFormat("###0.00");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        for (Last last : lastList) {
            if(last.getCollege()!=null && last.getStudentQuality()!=null && last.getTeachersStructure()!=null && last.getEmployment()!=null
                    && last.getEmploymentRate()!=null && last.getEmployerSatisfaction()!=null && last.getEmploymentPractice()!=null){
                index = Double.parseDouble(last.getStudentQuality())+ Double.parseDouble(last.getTeachersStructure())
                        + Double.parseDouble(last.getEmployment()) + Double.parseDouble(last.getEmploymentRate())
                        + Double.parseDouble(last.getEmployerSatisfaction()) + Double.parseDouble(last.getEmploymentPractice());
                last.setLastEmployment(decimalFormat.format(new BigDecimal(String.valueOf(index))));
            }
        }
        log.info("最终指数:" + lastList);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        sheet.setDefaultRowHeightInPoints(20);
        HSSFPrintSetup ps = sheet.getPrintSetup();
        ps.setLandscape(false); // 打印方向，true：横向，false：纵向
        ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE); //纸张
        sheet.setHorizontallyCenter(true);//设置打印页面为水平居中

        //设置要导出的文件的名字
        String fileName = year + "年本科毕业生就业竞争力指数.xls";
        fileName = URLEncoder.encode(fileName, "UTF-8");
        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        String[] headers = {"学院", "生源质量指数10.08", "师资结构指数11.07", "就业状态指数25.3",
                "就业率指数24.95", "用人满意度指数13.25", "就业创业实践指数15.35", "就业竞争力指数"};
        //headers表示excel表中第一行的表头
        HSSFRow row = sheet.createRow(0);
        //设置行高
        row.setHeightInPoints(30);
        //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
        sheet.setColumnWidth(0, 18 * 256);
        sheet.setColumnWidth(1, 25 * 256);
        sheet.setColumnWidth(2, 25 * 256);
        sheet.setColumnWidth(3, 25 * 256);
        sheet.setColumnWidth(4, 25 * 256);
        sheet.setColumnWidth(5, 30 * 256);
        sheet.setColumnWidth(6, 30 * 256);
        sheet.setColumnWidth(7, 25 * 256);
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
        font2.setFontHeightInPoints((short) 14);//设置字体大小
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

        //在表中存放查询到的数据放入对应的列
        HSSFCell cell;
        for (Last last : lastList) {
            HSSFRow row1 = sheet.createRow(rowNum);
            //设置行高
            row1.setHeightInPoints(25);
            cell = row1.createCell(0);
            cell.setCellStyle(style);
            if(last.getCollege()!=null) {
                cell.setCellValue(last.getCollege());
            }
            cell = row1.createCell(1);
            cell.setCellStyle(style);
            if(last.getStudentQuality()!=null){
                cell.setCellValue(Double.parseDouble(last.getStudentQuality()));
            }
            cell = row1.createCell(2);
            cell.setCellStyle(style);
            if(last.getTeachersStructure()!=null) {
                cell.setCellValue(Double.parseDouble(last.getTeachersStructure()));
            }
            cell = row1.createCell(3);
            cell.setCellStyle(style);
            if(last.getEmployment()!=null) {
                cell.setCellValue(Double.parseDouble(last.getEmployment()));
            }
            cell = row1.createCell(4);
            cell.setCellStyle(style);
            if(last.getEmploymentRate()!=null){
                cell.setCellValue(Double.parseDouble(last.getEmploymentRate()));
            }
            cell = row1.createCell(5);
            cell.setCellStyle(style);
            if(last.getEmployerSatisfaction()!=null){
                cell.setCellValue(Double.parseDouble(last.getEmployerSatisfaction()));
            }
            cell = row1.createCell(6);
            cell.setCellStyle(style);
            if(last.getEmploymentPractice()!=null){
                cell.setCellValue(Double.parseDouble(last.getEmploymentPractice()));
            }
            cell = row1.createCell(7);
            cell.setCellStyle(style);
            if(last.getLastEmployment()!=null){
                cell.setCellValue(Double.parseDouble(last.getLastEmployment()));
            }
            rowNum++;
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }
}
