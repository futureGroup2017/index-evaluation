package org.wlgzs.index_evaluation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.index_evaluation.dao.EmployerSatisfactionMapper;
import org.wlgzs.index_evaluation.pojo.EmployerSatisfaction;
import org.wlgzs.index_evaluation.service.EmployerSatisfactionService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 武凯焱
 * @date 2019/1/14 8:10
 * @Description:
 */
@Service
public class EmployerSatisfactionServiceImpl extends ServiceImpl<EmployerSatisfactionMapper, EmployerSatisfaction> implements EmployerSatisfactionService {
    @Resource
    EmployerSatisfactionMapper employerSatisfactionMapper;
    @Override
    public List<EmployerSatisfaction> importExcel(MultipartFile file,String year) throws IOException {
        int time = Integer.parseInt(year);
        List<EmployerSatisfaction> employerSatisfactionList  = new ArrayList<>();
        String fileName  = file.getOriginalFilename();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            return null;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            wb = new XSSFWorkbook(is);
        } else {
            wb = new HSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(1);
        System.out.println(sheet.getLastRowNum());
        for (int i = 3; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);

            if (row == null) {
                continue;
            }
            for (int j=0; j<row.getLastCellNum();j++) {
            row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);//设置读取转String类型
                String temp  = row.getCell(j).getStringCellValue();
                if (temp == null || temp.equals("")){
                    row.getCell(j).setCellValue("0");
                }
        }
            String college = row.getCell(0).getStringCellValue();
            //处理 毕业生的精神状态与工作水平的数据
            //毕业生的精神状态与工作水平指数 =  （（非常满意人数*1+满意人数*0.8+一般人数*0.6+不满意人数*0.4+非常不满意人数*0.2）*100/人数总和）*0.1895
            int level1 = Integer.parseInt(row.getCell(1).getStringCellValue());
            int level2 = Integer.parseInt(row.getCell(2).getStringCellValue());
            int level3 = Integer.parseInt(row.getCell(3).getStringCellValue());
            int level4 = Integer.parseInt(row.getCell(4).getStringCellValue());
            int level5 = Integer.parseInt(row.getCell(5).getStringCellValue());
            int num =  level1+level2+level3+level4+level5;
            double level  =(double) Math.round((level1+level2*0.8d+level3*0.6d+level4*0.4d+level5*0.2d)*100d/num*0.1895d*1000d)/1000d;
            //毕业生的综合素质能力指数 =  （（非常满意人数*1+满意人数*0.8+一般人数*0.6+不满意人数*0.4+非常不满意人数*0.2）*100/人数总和）*0.242
            int ability1 = Integer.parseInt(row.getCell(6).getStringCellValue());
            int ability2 = Integer.parseInt(row.getCell(7).getStringCellValue());
            int ability3 = Integer.parseInt(row.getCell(8).getStringCellValue());
            int ability4 = Integer.parseInt(row.getCell(9).getStringCellValue());
            int ability5 = Integer.parseInt(row.getCell(10).getStringCellValue());
            int num1 =  ability1+ability2+ability3+ability4+ability5;
            double ability  =(double) Math.round((ability1+ability2*0.8d+ability3*0.6d+ability4*0.4d+ability5*0.2d)*100d/num1*0.242d*1000d)/1000;
            //毕业生的“能力-岗位”匹配度能力指数 =  （（非常满意人数*1+满意人数*0.8+一般人数*0.6+不满意人数*0.4+非常不满意人数*0.2）*100/人数总和）*0.2225
            int match1 = Integer.parseInt(row.getCell(11).getStringCellValue());
            int match2 = Integer.parseInt(row.getCell(12).getStringCellValue());
            int match3 = Integer.parseInt(row.getCell(13).getStringCellValue());
            int match4 = Integer.parseInt(row.getCell(14).getStringCellValue());
            int match5 = Integer.parseInt(row.getCell(15).getStringCellValue());
            int num2 =  match1+match2+match3+match4+match5;
            double match  =(double) Math.round((match1+match2*0.8d+match3*0.6d+match4*0.4d+match5*0.2d)*100d/num2*0.2225d*1000d)/1000d;
            //对毕业生的工作满意度能力指数 =  （（非常满意人数*1+满意人数*0.8+一般人数*0.6+不满意人数*0.4+非常不满意人数*0.2）*100/人数总和）*0.2
            int satisfaction1 = Integer.parseInt(row.getCell(16).getStringCellValue());
            int satisfaction2 = Integer.parseInt(row.getCell(17).getStringCellValue());
            int satisfaction3 = Integer.parseInt(row.getCell(18).getStringCellValue());
            int satisfaction4 = Integer.parseInt(row.getCell(19).getStringCellValue());
            int satisfaction5 = Integer.parseInt(row.getCell(20).getStringCellValue());
            int num3 =  satisfaction1+satisfaction2+satisfaction3+satisfaction4+satisfaction5;
            double satisfaction  =(double) Math.round((satisfaction1+satisfaction2*0.8d+satisfaction3*0.6d+satisfaction4*0.4d+satisfaction5*0.2d)*100d/num3*0.1945*1000d)/1000d;
           /* int sustain1 = Integer.parseInt(row.getCell(1).getStringCellValue());
            int sustain2 = Integer.parseInt(row.getCell(2).getStringCellValue());
            int sustain3 = Integer.parseInt(row.getCell(3).getStringCellValue());
            int sustain4 = Integer.parseInt(row.getCell(4).getStringCellValue());
            int sustain5 = Integer.parseInt(row.getCell(5).getStringCellValue());
            double num4 =  sustain1+sustain2+sustain3+sustain4+sustain5;
            double sustain  =(double) Math.round((sustain1+sustain2*0.8d+sustain3*0.6d+sustain4*0.4d+sustain5*0.2d)*100d/num4*0.1895*1000)/1000;*/
           //用人单位满意度指数 =( 精神状态与工作水平指数 +综合素质能力指数+“能力-岗位”匹配度能力指数+工作满意度能力指数)*0.1325;
           double  satisfactionIndex  = (double) Math.round((level+ability+match+satisfaction)*132.5)/1000;
            EmployerSatisfaction em  = new EmployerSatisfaction(college,level,level1,level2,level3,level4,level5,
                    ability,ability1,ability2,ability3,ability4,ability5,match,match1,match2,match3,match4,match5,
                    satisfaction,satisfaction1,satisfaction2,satisfaction3,satisfaction4,satisfaction5,satisfactionIndex,time);
            employerSatisfactionList.add(em);
        }
        return employerSatisfactionList;

    }
    @Override
    public boolean add(List<EmployerSatisfaction> employerSatisfactions){
        if (employerSatisfactions==null){
            return false;
        }
        for (EmployerSatisfaction e:
             employerSatisfactions) {
            baseMapper.insert(e);
        }
        return true;
    }
    public void exportData(int year, HttpServletResponse response) throws IOException{
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("信息表");
        //设置要导出的文件的名字
        String fileName = year+"年用人单位满意度指数.xls";
        fileName = URLEncoder.encode(fileName, "UTF-8");
        int rowNum = 1;
        String[] headers = {"学院", "毕业生的精神状态与工作态度", "毕业生的综合素质能力", "毕业生的“能力-岗位”匹配度", "对毕业生的工作满意度", "用人单位满意度指数","平均值"};
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
        QueryWrapper<EmployerSatisfaction> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("year",year);
        List<EmployerSatisfaction> employerSatisfactionList = employerSatisfactionMapper.selectList(queryWrapper);

        HSSFCell cell;
        double num = 0;
        for (EmployerSatisfaction employerSatisfaction:
                employerSatisfactionList) {
            num+=employerSatisfaction.getSatisfactionIndex();
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.setHeightInPoints(25);
            //创建各列
            cell = row1.createCell(0);
            cell.setCellValue(employerSatisfaction.getCollege());
            style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            cell.setCellStyle(style);
            cell = row1.createCell(1);
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cell.setCellValue(employerSatisfaction.getLevel());
            cell.setCellStyle(style);
            cell = row1.createCell(2);
            cell.setCellValue(employerSatisfaction.getAbility());
            cell.setCellStyle(style);
            cell = row1.createCell(3);
            cell.setCellValue(employerSatisfaction.getMatched());
            cell.setCellStyle(style);
            cell = row1.createCell(4);
            cell.setCellValue(employerSatisfaction.getSatisfaction());
            cell.setCellStyle(style);
            cell = row1.createCell(5);
            cell.setCellValue(employerSatisfaction.getSatisfactionIndex());
            cell.setCellStyle(style);
            cell = row1.createCell(6);
            cell.setCellStyle(style);
            rowNum++;
        }
        //创建合并单元格  ---begin
        CellRangeAddress region = new CellRangeAddress(1, employerSatisfactionList.size(), 6, 6);// 下标从0开始 起始行号，终止行号， 起始列号，终止列号
        sheet.addMergedRegion(region);  //添加
        cell = sheet.getRow(1).getCell(6);
        double arrage = num/employerSatisfactionList.size();
        DecimalFormat df = new DecimalFormat("#.000");
        cell.setCellValue(df.format(arrage));   //向合并的单元格设置值
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }
    public void delete(Model model,Integer year,int pageNum,int pageSize){
        QueryWrapper<EmployerSatisfaction> queryWrapper = new QueryWrapper<>();
        Page<EmployerSatisfaction> page = new Page<>(pageNum,pageSize);
        if (year!=null){
            List<EmployerSatisfaction> employerSatisfactions = baseMapper.selectList(queryWrapper);
            IPage<EmployerSatisfaction> iPage = baseMapper.selectPage(page,queryWrapper);
            for (EmployerSatisfaction em:employerSatisfactions
                    ) {
                baseMapper.deleteById(em.getEsId());
            }
            model.addAttribute("current",iPage.getCurrent());//当前页数
            model.addAttribute("pages",iPage.getPages());//总页数
            model.addAttribute("employerSatisfactions",iPage.getRecords());//所有的数据集合

        }
        else {
            IPage<EmployerSatisfaction> iPage = baseMapper.selectPage(page,queryWrapper);
            model.addAttribute("msg","请选择年份");
            model.addAttribute("current",iPage.getCurrent());//当前页数
            model.addAttribute("pages",iPage.getPages());//总页数
            model.addAttribute("employerSatisfactions",iPage.getRecords());//所有的数据集合
        }

    }
}
