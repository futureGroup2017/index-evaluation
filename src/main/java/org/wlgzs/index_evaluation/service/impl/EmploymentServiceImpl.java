package org.wlgzs.index_evaluation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.index_evaluation.dao.EmploymentMapper;
import org.wlgzs.index_evaluation.pojo.Employment;
import org.wlgzs.index_evaluation.service.EmploymentService;
import org.wlgzs.index_evaluation.util.ExcelUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zsh
 * @company wlgzs
 * @create 2019-01-14 10:02
 * @Describe
 */
@Service
@Log4j2
public class EmploymentServiceImpl extends ServiceImpl<EmploymentMapper, Employment> implements EmploymentService {

    @Override
    public List<Employment> importExcelInfo(InputStream in, MultipartFile file,Integer year) {
        List<List<Object>> listob;
        List<Employment> employmentArrayList = new ArrayList<>();
        try {
            listob = ExcelUtil.getBankListByExcel(in,file.getOriginalFilename());
            //遍历listob数据，把数据放到List中
            for (int i = 0; i < listob.size(); i++) {
                List<Object> ob = listob.get(i);
                //通过遍历实现把每一列封装成一个model中，再把所有的model用List集合装载
                Employment employment = findByCollegeAndYear(String.valueOf(ob.get(0)),year);
                if (employment == null){
                    continue;
                }
                //参与调查人数
                employment.setParNum(employment.getParNum()+1);
                //专业知识能力
                if ("非常强".equals(String.valueOf(ob.get(10)))){
                    employment.setMB1111(employment.getMB1111()+1);
                }else if ("很强".equals(String.valueOf(ob.get(10)))){
                    employment.setMB1112(employment.getMB1112()+1);
                }else if ("一般".equals(String.valueOf(ob.get(10)))){
                    employment.setMB1113(employment.getMB1113()+1);
                }else if ("不强".equals(String.valueOf(ob.get(10)))){
                    employment.setMB1114(employment.getMB1114()+1);
                }else if ("很不强".equals(String.valueOf(ob.get(10)))){
                    employment.setMB1115(employment.getMB1115()+1);
                }
                //通用知识能力
                if ("非常强".equals(String.valueOf(ob.get(11)))){
                    employment.setMB1121(employment.getMB1121()+1);
                }else if ("很强".equals(String.valueOf(ob.get(11)))){
                    employment.setMB1122(employment.getMB1122()+1);
                }else if ("一般".equals(String.valueOf(ob.get(11)))){
                    employment.setMB1123(employment.getMB1123()+1);
                }else if ("不强".equals(String.valueOf(ob.get(11)))){
                    employment.setMB1124(employment.getMB1124()+1);
                }else if ("很不强".equals(String.valueOf(ob.get(11)))){
                    employment.setMB1125(employment.getMB1125()+1);
                }
                //求职应聘能力
                if ("非常强".equals(String.valueOf(ob.get(12)))){
                    employment.setMB1131(employment.getMB1131()+1);
                }else if ("很强".equals(String.valueOf(ob.get(12)))){
                    employment.setMB1132(employment.getMB1132()+1);
                }else if ("一般".equals(String.valueOf(ob.get(12)))){
                    employment.setMB1133(employment.getMB1133()+1);
                }else if ("不强".equals(String.valueOf(ob.get(12)))){
                    employment.setMB1134(employment.getMB1134()+1);
                }else if ("很不强".equals(String.valueOf(ob.get(12)))){
                    employment.setMB1135(employment.getMB1135()+1);
                }
                //社会兼职经历
                if ("1周内".equals(String.valueOf(ob.get(14)))){
                    employment.setMB1215(employment.getMB1215()+1);
                }else if ("半月".equals(String.valueOf(ob.get(14)))){
                    employment.setMB1214(employment.getMB1214()+1);
                }else if ("1个月".equals(String.valueOf(ob.get(14)))){
                    employment.setMB1213(employment.getMB1213()+1);
                }else if ("2个月".equals(String.valueOf(ob.get(14)))){
                    employment.setMB1212(employment.getMB1212()+1);
                }else if ("3个月及以上".equals(String.valueOf(ob.get(14)))){
                    employment.setMB1211(employment.getMB1211()+1);
                }
                //“非学历、费荣誉”证书
                if ("3个以上".equals(String.valueOf(ob.get(15)))){
                    employment.setMB1221(employment.getMB1221()+1);
                }else if ("3个".equals(String.valueOf(ob.get(15)))){
                    employment.setMB1222(employment.getMB1222()+1);
                }else if ("2个".equals(String.valueOf(ob.get(15)))){
                    employment.setMB1223(employment.getMB1223()+1);
                }else if ("1个".equals(String.valueOf(ob.get(15)))){
                    employment.setMB1224(employment.getMB1224()+1);
                }else if ("0个".equals(String.valueOf(ob.get(15)))){
                    employment.setMB1225(employment.getMB1225()+1);
                }
                //社会职务：有，无
                if ("其他".equals(String.valueOf(ob.get(9)))){
                    employment.setMB1232(employment.getMB1232()+1);
                }else {
                    employment.setMB1231(employment.getMB1231()+1);
                }
                //求职积极程度
                if ("很积极".equals(String.valueOf(ob.get(21)))){
                    employment.setMB1311(employment.getMB1311()+1);
                }else if ("积极".equals(String.valueOf(ob.get(21)))){
                    employment.setMB1312(employment.getMB1312()+1);
                }else if ("一般".equals(String.valueOf(ob.get(21)))){
                    employment.setMB1313(employment.getMB1313()+1);
                }else if ("不积极".equals(String.valueOf(ob.get(21)))){
                    employment.setMB1314(employment.getMB1314()+1);
                }else if ("很不积极".equals(String.valueOf(ob.get(21)))){
                    employment.setMB1315(employment.getMB1315()+1);
                }
                //自我效能感人数
                if ("很自信".equals(String.valueOf(ob.get(22)))){
                    employment.setMB1321(employment.getMB1321()+1);
                }else if ("自信".equals(String.valueOf(ob.get(22)))){
                    employment.setMB1322(employment.getMB1322()+1);
                }else if ("一般".equals(String.valueOf(ob.get(22)))){
                    employment.setMB1323(employment.getMB1323()+1);
                }else if ("不自信".equals(String.valueOf(ob.get(22)))){
                    employment.setMB1324(employment.getMB1324()+1);
                }else if ("很不自信".equals(String.valueOf(ob.get(22)))){
                    employment.setMB1325(employment.getMB1325()+1);
                }
                //专业对口状态
                if ("很对口".equals(String.valueOf(ob.get(35)))){
                    employment.setMB2211(employment.getMB2211()+1);
                }else if ("对口".equals(String.valueOf(ob.get(35)))){
                    employment.setMB2212(employment.getMB2212()+1);
                }else if ("一般".equals(String.valueOf(ob.get(35)))){
                    employment.setMB2213(employment.getMB2213()+1);
                }else if ("不对口".equals(String.valueOf(ob.get(35)))){
                    employment.setMB2214(employment.getMB2214()+1);
                }else if ("很不对口".equals(String.valueOf(ob.get(35)))){
                    employment.setMB2215(employment.getMB2215()+1);
                }
                //“能力-岗位”适配度
                if ("很匹配".equals(String.valueOf(ob.get(37)))){
                    employment.setMB2221(employment.getMB2221()+1);
                }else if ("匹配".equals(String.valueOf(ob.get(37)))){
                    employment.setMB2222(employment.getMB2222()+1);
                }else if ("一般".equals(String.valueOf(ob.get(37)))){
                    employment.setMB2223(employment.getMB2223()+1);
                }else if ("不匹配".equals(String.valueOf(ob.get(37)))){
                    employment.setMB2224(employment.getMB2224()+1);
                }else if ("很不匹配".equals(String.valueOf(ob.get(37)))){
                    employment.setMB2225(employment.getMB2225()+1);
                }
                //月薪兑付状态
                if ("正常".equals(String.valueOf(ob.get(32)))){
                    employment.setMB2311(employment.getMB2311()+1);
                }else if ("拖欠".equals(String.valueOf(ob.get(32)))){
                    employment.setMB2312(employment.getMB2312()+1);
                }
                //“五险一金”执行状态
                if ("正常".equals(String.valueOf(ob.get(33)))){
                    employment.setMB2321(employment.getMB2321()+1);
                }else if ("拖欠".equals(String.valueOf(ob.get(33)))){
                    employment.setMB2322(employment.getMB2322()+1);
                }
                //成长发展空间
                if ("很宽广".equals(String.valueOf(ob.get(38)))){
                    employment.setMB2331(employment.getMB2331()+1);
                }else if ("宽广".equals(String.valueOf(ob.get(38)))){
                    employment.setMB2332(employment.getMB2332()+1);
                }else if ("一般".equals(String.valueOf(ob.get(38)))){
                    employment.setMB2333(employment.getMB2333()+1);
                }else if ("不宽广".equals(String.valueOf(ob.get(38)))){
                    employment.setMB2334(employment.getMB2334()+1);
                }else if ("很不宽广".equals(String.valueOf(ob.get(38)))){
                    employment.setMB2335(employment.getMB2335()+1);
                }
                //工作满意度
                if ("很满意".equals(String.valueOf(ob.get(39)))){
                    employment.setMB2341(employment.getMB2341()+1);
                }else if ("满意".equals(String.valueOf(ob.get(39)))){
                    employment.setMB2342(employment.getMB2342()+1);
                }else if ("一般".equals(String.valueOf(ob.get(39)))){
                    employment.setMB2343(employment.getMB2343()+1);
                }else if ("不满意".equals(String.valueOf(ob.get(39)))){
                    employment.setMB2344(employment.getMB2344()+1);
                }else if ("很不满意".equals(String.valueOf(ob.get(39)))){
                    employment.setMB2345(employment.getMB2345()+1);
                }
                //预期就业年限
                if ("10年以上".equals(String.valueOf(ob.get(40)))){
                    employment.setMB241(employment.getMB241()+1);
                }else if ("8-10年".equals(String.valueOf(ob.get(40)))){
                    employment.setMB242(employment.getMB242()+1);
                }else if ("3-7年".equals(String.valueOf(ob.get(40)))){
                    employment.setMB243(employment.getMB243()+1);
                }else if ("2年".equals(String.valueOf(ob.get(40)))){
                    employment.setMB244(employment.getMB244()+1);
                }else if ("1年".equals(String.valueOf(ob.get(40)))){
                    employment.setMB245(employment.getMB245()+1);
                }
                update(employment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return findByYear(year);
    }

    @Override
    public List<Employment> importExcelInfo1(InputStream in, MultipartFile file) {
        List<List<Object>> listob;
        List<Employment> employmentArrayList = new ArrayList<>();
        try {
            DecimalFormat df4 = new DecimalFormat("#.0000");
            listob = ExcelUtil.getBankListByExcel(in,file.getOriginalFilename());
            //遍历listob数据，把数据放到List中
            for (int i = 1; i < listob.size(); i++) {
                List<Object> ob = listob.get(i);
                //通过遍历实现把每一列封装成一个model中，再把所有的model用List集合装载
                Employment employment = new Employment();
                //学院
                employment.setCollege(String.valueOf(ob.get(0)));
                //参与调查人数
                employment.setParNum(0);
                //就业起薪指数,保留四位小数
                employment.setB21(Double.parseDouble(String.valueOf(ob.get(1))));
                //专业知识能力：非常强，很强，一般，不强，很不强
                employment.setMB1111(0);
                employment.setMB1112(0);
                employment.setMB1113(0);
                employment.setMB1114(0);
                employment.setMB1115(0);
                //通用知识能力：非常强，很强，一般，不强，很不强
                employment.setMB1121(0);
                employment.setMB1122(0);
                employment.setMB1123(0);
                employment.setMB1124(0);
                employment.setMB1125(0);
                //求职应聘能力：非常强，很强，一般，不强，很不强
                employment.setMB1131(0);
                employment.setMB1132(0);
                employment.setMB1133(0);
                employment.setMB1134(0);
                employment.setMB1135(0);
                //社会兼职经历：1周，半月，1个月，2个月，3个月以上
                employment.setMB1211(0);
                employment.setMB1212(0);
                employment.setMB1213(0);
                employment.setMB1214(0);
                employment.setMB1215(0);
                //“非学历、费荣誉”证书：3个以上，3个，2个，1个，0个
                employment.setMB1221(0);
                employment.setMB1222(0);
                employment.setMB1223(0);
                employment.setMB1224(0);
                employment.setMB1225(0);
                //社会职务：有，无
                employment.setMB1231(0);
                employment.setMB1232(0);
                //求职积极程度：很积极，积极，一般，不积极，很不积极
                employment.setMB1311(0);
                employment.setMB1312(0);
                employment.setMB1313(0);
                employment.setMB1314(0);
                employment.setMB1315(0);
                //自我效能感人数：很自信，自信，一般，不自信，很不自信
                employment.setMB1321(0);
                employment.setMB1322(0);
                employment.setMB1323(0);
                employment.setMB1324(0);
                employment.setMB1325(0);
                //就业起薪：2001-3000，3001-4000，4001-5000，5001-6000，6001-7000，7000以上
                employment.setMB211(0);
                employment.setMB212(0);
                employment.setMB213(0);
                employment.setMB214(0);
                employment.setMB215(0);
                employment.setMB216(0);
                //专业对口状态：很对口，对口，一般，不对口，很不对口
                employment.setMB2211(0);
                employment.setMB2212(0);
                employment.setMB2213(0);
                employment.setMB2214(0);
                employment.setMB2215(0);
                //“能力-岗位”适配度：很匹配，匹配，一般，不匹配，很不匹配
                employment.setMB2221(0);
                employment.setMB2222(0);
                employment.setMB2223(0);
                employment.setMB2224(0);
                employment.setMB2225(0);
                //月薪兑付状态：正常，拖欠
                employment.setMB2311(0);
                employment.setMB2312(0);
                //“五险一金”执行状态：正常，拖欠
                employment.setMB2321(0);
                employment.setMB2322(0);
                //成长发展空间：很宽广，宽广，一般，不宽广，很不宽广
                employment.setMB2331(0);
                employment.setMB2332(0);
                employment.setMB2333(0);
                employment.setMB2334(0);
                employment.setMB2335(0);
                //工作满意度：很满意，满意，一般，不满意，很不满意
                employment.setMB2341(0);
                employment.setMB2342(0);
                employment.setMB2343(0);
                employment.setMB2344(0);
                employment.setMB2345(0);
                //预期就业年限：10年以上，8-10年，3-7年，2年，1年
                employment.setMB241(0);
                employment.setMB242(0);
                employment.setMB243(0);
                employment.setMB244(0);
                employment.setMB245(0);
                employmentArrayList.add(employment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employmentArrayList;
    }

    @Override
    public Integer add(Employment employment) {
        return baseMapper.insert(employment);
    }

    @Override
    public void export(Integer year, HttpServletResponse response) {
        QueryWrapper<Employment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("year",year);
        List<Employment> SignList = baseMapper.selectList(queryWrapper);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("就业状态指数导出表");
        sheet.setDefaultRowHeightInPoints(20);
        HSSFPrintSetup ps = sheet.getPrintSetup();
        ps.setLandscape(false); // 打印方向，true：横向，false：纵向
        ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE); //纸张
        sheet.setHorizontallyCenter(true);//设置打印页面为水平居中

        //设置要导出的文件的名字
        String fileName = year+"就业状态指数.xls";
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        String[] headers = { "学院", "知识能力结构40.75", "标识性优势31.35", "择业精神27.9", "就业起薪28.55",
                "岗位胜任度24.2", "就业现状满意度28","预期就业年限19.25" ,
                "个体就业潜力44.8","个体就业表现55.2","就业状态指数25.3"};
        //headers表示excel表中第一行的表头
        HSSFRow row1 = sheet.createRow(0);
        //设置行高
        row1.setHeightInPoints(30);
        //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
        for (int i = 0;i<11;i++){
            sheet.setColumnWidth(i, 19 * 256);
        }
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
            HSSFCell cell = row1.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellStyle(style2);
            cell.setCellValue(text);
        }

        //在表中存放查询到的数据放入对应的列
        HSSFCell cell;

        for (int i = 0; i < SignList.size(); i++) {
            Employment employment = SignList.get(i);// 获取sign对象
            HSSFRow row = sheet.createRow(rowNum);
            //设置行高
            row.setHeightInPoints(25);
            //学院
            if (employment.getCollege() != null) {
                cell = row.createCell(0);// 创建第i+1行第0列
                cell.setCellValue(employment.getCollege());// 设置第i+1行第0列的值
                cell.setCellStyle(style);// 设置风格
            }
            //知识能力结构40.75
            cell = row.createCell(1); // 创建第i+1行第1列
            cell.setCellValue(employment.getB11());// 设置第i+1行第1列的值
            cell.setCellStyle(style); // 设置风格
            //标识性优势31.35
            cell = row.createCell(2);
            cell.setCellValue(employment.getB12());
            cell.setCellStyle(style);
            //择业精神27.9
            cell = row.createCell(3);
            cell.setCellValue(employment.getB13());
            cell.setCellStyle(style);
            //就业起薪28.55
            cell = row.createCell(4);
            cell.setCellValue(employment.getB21());
            cell.setCellStyle(style);
            //岗位胜任度24.2
            cell = row.createCell(5);
            cell.setCellValue(employment.getB22());
            cell.setCellStyle(style);
            //就业现状满意度28
            cell = row.createCell(6);
            cell.setCellValue(employment.getB23());
            cell.setCellStyle(style);
            //预期就业年限19.25
            cell = row.createCell(7);
            cell.setCellValue(employment.getB24());
            cell.setCellStyle(style);
            //个体就业潜力44.8
            cell = row.createCell(8);
            cell.setCellValue(employment.getA1());
            cell.setCellStyle(style);
            //个体就业表现55.2
            cell = row.createCell(9);
            cell.setCellValue(employment.getA2());
            cell.setCellStyle(style);
            //就业状态指数25.3
            cell = row.createCell(10);
            cell.setCellValue(employment.getEmploymentStatus());
            cell.setCellStyle(style);
            rowNum++;
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        try {
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }catch (IllegalStateException ee){
            log.info("IllegalStateException异常");
        }
    }

    @Override
    public Integer delete(Employment employment) {
        return baseMapper.deleteById(employment.getEmId());
    }

    @Override
    public List<Employment> findByYear(Integer year) {
        QueryWrapper<Employment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("year",year);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public Employment findByCollegeAndYear(String college, Integer year) {
        QueryWrapper<Employment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("college",college).eq("year",year);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Integer update(Employment employment) {
        return baseMapper.updateById(employment);
    }
}
