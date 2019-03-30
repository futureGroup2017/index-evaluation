package org.wlgzs.index_evaluation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.wlgzs.index_evaluation.dao.EmploymentRateMapper;
import org.wlgzs.index_evaluation.enums.Result;
import org.wlgzs.index_evaluation.enums.ResultCodeEnum;
import org.wlgzs.index_evaluation.pojo.EmploymentRate;
import org.wlgzs.index_evaluation.service.EmploymentRateService;
import org.wlgzs.index_evaluation.util.ExcelUtil;
import org.wlgzs.index_evaluation.util.ExcelUtilTwo;

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
 * @date Created in 2019/1/13 17
 * @Description 就业率指数
 */
@Service
@Log4j2
public class EmploymentRateServiceImpl extends ServiceImpl<EmploymentRateMapper, EmploymentRate> implements EmploymentRateService {
    @Resource
    private EmploymentRateMapper employmentRateMapper;

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
        if(!file.getOriginalFilename().equals("4.就业率指数样表.xlsx")){
            Result result = new Result(ResultCodeEnum.FAIL);
            result.setMsg("上传文件错误，请确认是<4.就业率指数样表.xlsx>");
            return result;
        }
        QueryWrapper<EmploymentRate> queryWrappers = new QueryWrapper<>();
        queryWrappers.eq("year", year);
        queryWrappers.last("limit 2");
        List<EmploymentRate> list = employmentRateMapper.selectList(queryWrappers);
        if (list != null && list.size() > 0) {
            return new Result(0, "导入数据重复");
        }
        List<List<Object>> listob;
        List<EmploymentRate> employmentRateList = new ArrayList<>();
        try {
            InputStream in = file.getInputStream();
            DecimalFormat decimalFormat = new DecimalFormat("###0.0000");//构造方法的字符格式这里如果小数不足3位,会以0补足.
            listob = ExcelUtilTwo.getBankListByExcel(in,file.getOriginalFilename());
            //遍历listob数据，把数据放到List中
            for (List<Object> objects : listob) {
                EmploymentRate employmentRate = new EmploymentRate();
                //通过遍历实现把每一列封装成一个model中，再把所有的model用List集合装载
                employmentRate.setCollege(String.valueOf(objects.get(0)));
                employmentRate.setFirstEmploymentRate(String.valueOf(objects.get(1)));
                employmentRate.setLastEmploymentRate(String.valueOf(objects.get(2)));
                //初次就业率指数=初次就业率*100*0.2495
                double firstIndex = ((Double.parseDouble(employmentRate.getFirstEmploymentRate())) * 100 * 0.2495);
                employmentRate.setFirstIndex(decimalFormat.format(firstIndex));
                //年终就业率指数=年终就业率*100*0.586
                double lastIndex = (Double.parseDouble(employmentRate.getLastEmploymentRate()) * 100 * 0.586);
                employmentRate.setLastIndex(decimalFormat.format(lastIndex));
                //就业率指数=（初次就业率指数+年终就业率指数）*0.2495
                double index = ((Double.parseDouble(employmentRate.getFirstIndex()) + Double.parseDouble(employmentRate.getLastIndex())) * 0.2495);
                employmentRate.setEmploymentRateIndex(decimalFormat.format(index));
                employmentRate.setYear(year);
                employmentRateList.add(employmentRate);
                employmentRateMapper.insert(employmentRate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("就业率数据："+employmentRateList);
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
        String fileName = year+"年就业率指数.xls";
        fileName = URLEncoder.encode(fileName, "UTF-8");
        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        String[] headers = {"学院", "初次就业率", "年终就业率", "初次就业率指数", "年终就业率指数", "就业率指数"};
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
        QueryWrapper<EmploymentRate> rateQueryWrapper = new QueryWrapper<>();
        rateQueryWrapper.eq("year",year);
        List<EmploymentRate> employmentRateList = employmentRateMapper.selectList(rateQueryWrapper);
        //在表中存放查询到的数据放入对应的列
        HSSFCell cell;
        for (EmploymentRate employmentRate : employmentRateList) {
            HSSFRow row1 = sheet.createRow(rowNum);
            //设置行高
            row1.setHeightInPoints(25);
            cell = row1.createCell(0);
            cell.setCellValue(employmentRate.getCollege());
            cell.setCellStyle(style);
            cell = row1.createCell(1);
            cell.setCellValue(Double.parseDouble(employmentRate.getFirstEmploymentRate()));
            cell.setCellStyle(style);
            cell = row1.createCell(2);
            cell.setCellValue(Double.parseDouble(employmentRate.getLastEmploymentRate()));
            cell.setCellStyle(style);
            cell = row1.createCell(3);
            cell.setCellValue(Double.parseDouble(employmentRate.getFirstIndex()));
            cell.setCellStyle(style);
            cell = row1.createCell(4);
            cell.setCellValue(Double.parseDouble(employmentRate.getLastIndex()));
            cell.setCellStyle(style);
            cell = row1.createCell(5);
            cell.setCellValue(Double.parseDouble(employmentRate.getEmploymentRateIndex()));
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
        QueryWrapper<EmploymentRate> rateQueryWrapper = new QueryWrapper<>();
        rateQueryWrapper.eq("year",year);
        List<EmploymentRate> employmentRates = employmentRateMapper.selectList(rateQueryWrapper);
        if(employmentRates == null) return false;
        employmentRateMapper.delete(rateQueryWrapper);
        return true;
    }
}
