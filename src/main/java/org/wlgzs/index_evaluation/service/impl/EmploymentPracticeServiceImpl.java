package org.wlgzs.index_evaluation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.wlgzs.index_evaluation.dao.EmploymentPracticeMapper;
import org.wlgzs.index_evaluation.enums.Result;
import org.wlgzs.index_evaluation.enums.ResultCodeEnum;
import org.wlgzs.index_evaluation.pojo.EmploymentPractice;
import org.wlgzs.index_evaluation.service.EmploymentPracticeService;
import org.wlgzs.index_evaluation.util.ExcelUtilPratice;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author AlgerFan
 * @date Created in 2019/1/14 15
 * @Description 就业创业实践指数
 */
@Service
@Log4j2
public class EmploymentPracticeServiceImpl extends ServiceImpl<EmploymentPracticeMapper, EmploymentPractice> implements EmploymentPracticeService {

    @Resource
    private EmploymentPracticeMapper employmentPracticeMapper;

    @Override
    public Result importData(Integer year, HttpServletRequest request) {
        if(year==null){
            log.info("请选择年份");
            return new Result(ResultCodeEnum.SELECTYEAR);
        }
        //获取上传的文件
        MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
        MultipartFile file = multipart.getFile("file");
        if(file==null || file.getOriginalFilename()==null || year==0){
            return new Result(ResultCodeEnum.UNIMport);
        }
        if(!file.getOriginalFilename().equals("6.就业创业实践指数样表.xlsx")){
            Result result = new Result(ResultCodeEnum.FAIL);
            result.setMsg("上传文件错误，请确认是<6.就业创业实践指数样表.xlsx>");
            return result;
        }
        QueryWrapper<EmploymentPractice> queryWrappers = new QueryWrapper<>();
        queryWrappers.eq("year", year);
        queryWrappers.last("limit 2");
        List<EmploymentPractice> list = employmentPracticeMapper.selectList(queryWrappers);
        if (list != null && list.size() > 0) {
            return new Result(0, "导入数据重复");
        }
        List<List<Object>> listob;
        List<EmploymentPractice> employmentPractices = new ArrayList<>();
        try {
            InputStream in = file.getInputStream();
            DecimalFormat decimalFormat = new DecimalFormat("###0.0000");//构造方法的字符格式这里如果小数不足3位,会以0补足.
            listob = ExcelUtilPratice.getBankListByExcel(in,file.getOriginalFilename());
            //decimalFormat.setRoundingMode(RoundingMode.HALF_UP);//四舍五入
            //参赛总人数
            double total = 0;
            //最高获奖质量积分
            double quality1 = 0;
            double quality2 = 0;
            double quality3 = 0;
            double quality4 = 0;
            //项目数量最高值
            double number = 0;
            //项目质量最高值
            double quality = 0;
            for (List<Object> objects1 : listob) {
                if(objects1.get(1)==null || objects1.get(1).equals("") || objects1.get(1).equals("0.0")) objects1.set(1,1);
                if(objects1.get(2)==null || objects1.get(2).equals("") || objects1.get(2).equals("0.0")) objects1.set(2,1);
                if(objects1.get(3)==null || objects1.get(3).equals("") || objects1.get(3).equals("0.0")) objects1.set(3,1);
                if(objects1.get(4)==null || objects1.get(4).equals("") || objects1.get(4).equals("0.0")) objects1.set(4,0);
                if(objects1.get(5)==null || objects1.get(5).equals("") || objects1.get(5).equals("0.0")) objects1.set(5,0);
                if(objects1.get(6)==null || objects1.get(6).equals("") || objects1.get(6).equals("0.0")) objects1.set(6,0);
                if(objects1.get(7)==null || objects1.get(7).equals("") || objects1.get(7).equals("0.0")) objects1.set(7,0);
                if(objects1.get(8)==null || objects1.get(8).equals("") || objects1.get(8).equals("0.0")) objects1.set(8,0);
                if(objects1.get(9)==null || objects1.get(9).equals("") || objects1.get(9).equals("0.0")) objects1.set(9,0);
                if(objects1.get(10)==null || objects1.get(10).equals("") || objects1.get(10).equals("0.0")) objects1.set(10,0);
                if(objects1.get(11)==null || objects1.get(11).equals("") || objects1.get(11).equals("0.0")) objects1.set(11,0);
                total += Double.parseDouble(String.valueOf(objects1.get(4)));
                double v1 = Double.parseDouble(String.valueOf(objects1.get(5)));
                if (v1 > quality1) quality1 = v1;
                double v2 = Double.parseDouble(String.valueOf(objects1.get(6)));
                if (v2 > quality2) quality2 = v2;
                double v3 = Double.parseDouble(String.valueOf(objects1.get(7)));
                if (v3 > quality3) quality3 = v3;
                double v4 = Double.parseDouble(String.valueOf(objects1.get(8)));
                if (v4 > quality4) quality4 = v4;
                double v5 = Double.parseDouble(String.valueOf(objects1.get(9)));
                if (v5 > number) number = v5;
                double v6 = Double.parseDouble(String.valueOf(objects1.get(10)));
                if (v6 > quality) quality = v6;
            }
            //遍历listob数据，把数据放到List中
            for (List<Object> objects : listob) {
                EmploymentPractice employmentPractice = new EmploymentPractice();
                //通过遍历实现把每一列封装成一个model中，再把所有的model用List集合装载
                employmentPractice.setCollege(String.valueOf(objects.get(0)));
                //参赛人数比-1(职业生涯规划大赛)
                employmentPractice.setM11(Double.parseDouble(String.valueOf(objects.get(1))));
                //参赛人数比-1(简历大赛)
                employmentPractice.setM12(Double.parseDouble(String.valueOf(objects.get(2))));
                //参赛人数比-1(创业大赛)
                employmentPractice.setM13(Double.parseDouble(String.valueOf(objects.get(3))));
                //参赛人数2-省创业大赛
                employmentPractice.setM14(Double.parseDouble(String.valueOf(objects.get(4))));
                //处理数据——M1: 参赛人数比47.5
                if(total == 0) {
                    employmentPractice.setPeopleNumber(Double.parseDouble(decimalFormat.format(((employmentPractice.getM11() +
                            employmentPractice.getM12() + employmentPractice.getM13())*0.7)*0.475)));
                } else {
                    employmentPractice.setPeopleNumber(Double.parseDouble(decimalFormat.format(((employmentPractice.getM11() +
                            employmentPractice.getM12() + employmentPractice.getM13())*0.7 + (employmentPractice.getM14()/total)*0.3)*0.475)));
                }
                //获奖质量积分-1(生涯规划大赛)
                employmentPractice.setM21(Double.parseDouble(String.valueOf(objects.get(5))));
                //获奖质量积分-1(简历大赛)
                employmentPractice.setM22(Double.parseDouble(String.valueOf(objects.get(6))));
                //获奖质量积分-1(创业大赛)
                employmentPractice.setM23(Double.parseDouble(String.valueOf(objects.get(7))));
                //获奖质量积分-2-省创业大赛
                employmentPractice.setM24(Double.parseDouble(String.valueOf(objects.get(8))));
                //处理数据——M2: 获奖质量比52.5
                double dou = 0;
                if(quality1!=0) {
                    dou += employmentPractice.getM21()/quality1;
                } else if(quality2!=0) {
                    dou += employmentPractice.getM22()/quality2;
                } else if(quality3!=0) {
                    dou += employmentPractice.getM23()/quality3;
                } else if(quality4!=0) {
                    dou += employmentPractice.getM24()/quality4;
                }
                employmentPractice.setQuality(Double.parseDouble(decimalFormat.format((dou)*0.525)));

                //项目数量积分
                employmentPractice.setM31(Double.parseDouble(String.valueOf(objects.get(9))));
                //处理数据——M3: 项目数量比47
                if(number==0) {
                    employmentPractice.setProjectNumber(Double.parseDouble(decimalFormat.format(0)));
                } else {
                    employmentPractice.setProjectNumber(Double.parseDouble(decimalFormat.format((employmentPractice.getM31()/number)*0.47)));
                }
                //项目质量积分
                employmentPractice.setM41(Double.parseDouble(String.valueOf(objects.get(10))));
                //处理数据——M4: 项目质量比53
                if(quality==0) {
                    employmentPractice.setProjectQuality(Double.parseDouble(decimalFormat.format(0)));
                } else {
                    employmentPractice.setProjectQuality(Double.parseDouble(decimalFormat.format(((employmentPractice.getM41() / quality) * 0.53))));
                }
                /*
                特色项目
                 */
                employmentPractice.setFeaturedWork(Double.parseDouble(String.valueOf(objects.get(11))));

                /*
                处理数据——就业创业实践指数
                 */
                employmentPractice.setPractice(Double.parseDouble(decimalFormat.format(
                        (employmentPractice.getPeopleNumber() + employmentPractice.getQuality())*0.39 +
                                (employmentPractice.getProjectNumber() + employmentPractice.getProjectQuality())*0.285 +
                                employmentPractice.getFeaturedWork()*0.325)));
                employmentPractice.setYear(year);
                employmentPractices.add(employmentPractice);
                employmentPracticeMapper.insert(employmentPractice);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("就业创业实践数据："+employmentPractices);
        return new Result(ResultCodeEnum.IMport);
    }

    @Override
    public void exportData(int year, HttpServletResponse response) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        sheet.setDefaultRowHeightInPoints(20);
        HSSFPrintSetup ps = sheet.getPrintSetup();
        ps.setLandscape(false); // 打印方向，true：横向，false：纵向
        ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE); //纸张
        sheet.setHorizontallyCenter(true);//设置打印页面为水平居中

        //设置要导出的文件的名字
        String fileName = year+"就业创业类实践指数.xls";
        fileName = URLEncoder.encode(fileName, "UTF-8");
        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        String[] headers = {"学院", "参赛人数比", "获奖质量比",
                "项目数量比47", "项目质量比53", "特色工作32.5", "就业创业实践指数"};
        //headers表示excel表中第一行的表头
        HSSFRow row = sheet.createRow(0);
        //设置行高
        row.setHeightInPoints(30);
        //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
        sheet.setColumnWidth(0, 19 * 256);
        sheet.setColumnWidth(1, 19 * 256);
        sheet.setColumnWidth(2, 19 * 256);
        sheet.setColumnWidth(3, 19 * 256);
        sheet.setColumnWidth(4, 19 * 256);
        sheet.setColumnWidth(5, 19 * 256);
        sheet.setColumnWidth(6, 19 * 256);
        //其他表样式
        HSSFCellStyle style = workbook.createCellStyle();
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 11);//设置字体大小
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
        font2.setFontHeightInPoints((short) 11);//设置字体大小
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
        QueryWrapper<EmploymentPractice> rateQueryWrapper = new QueryWrapper<>();
        rateQueryWrapper.eq("year",year);
        List<EmploymentPractice> employmentRateList = employmentPracticeMapper.selectList(rateQueryWrapper);
        //在表中存放查询到的数据放入对应的列
        HSSFCell cell;
        for (EmploymentPractice employmentPractice : employmentRateList) {
            HSSFRow row1 = sheet.createRow(rowNum);
            //设置行高
            row1.setHeightInPoints(25);
            cell = row1.createCell(0);
            cell.setCellValue(employmentPractice.getCollege());
            cell.setCellStyle(style);
            cell = row1.createCell(1);
            cell.setCellValue(employmentPractice.getPeopleNumber());
            cell.setCellStyle(style);
            cell = row1.createCell(2);
            cell.setCellValue(employmentPractice.getQuality());
            cell.setCellStyle(style);
            cell = row1.createCell(3);
            cell.setCellValue(employmentPractice.getProjectNumber());
            cell.setCellStyle(style);
            cell = row1.createCell(4);
            cell.setCellValue(employmentPractice.getProjectQuality());
            cell.setCellStyle(style);
            cell = row1.createCell(5);
            cell.setCellValue(employmentPractice.getFeaturedWork());
            cell.setCellStyle(style);
            cell = row1.createCell(6);
            cell.setCellValue(employmentPractice.getPractice());
            cell.setCellStyle(style);
            rowNum++;
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

    @Override
    public boolean deleteYear(int year) {
        if(year==0) return false;
        QueryWrapper<EmploymentPractice> practiceQueryWrapper = new QueryWrapper<>();
        practiceQueryWrapper.eq("year",year);
        List<EmploymentPractice> employmentPractices = employmentPracticeMapper.selectList(practiceQueryWrapper);
        if(employmentPractices==null) return false;
        employmentPracticeMapper.delete(practiceQueryWrapper);
        return true;
    }

}
