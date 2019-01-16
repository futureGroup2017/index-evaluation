package org.wlgzs.index_evaluation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.index_evaluation.dao.EmploymentMapper;
import org.wlgzs.index_evaluation.pojo.Employment;
import org.wlgzs.index_evaluation.pojo.TeachersStructure;
import org.wlgzs.index_evaluation.service.EmploymentService;
import org.wlgzs.index_evaluation.util.ExcelUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
    public List<Employment> importExcelInfo(InputStream in, MultipartFile file) {
        List<List<Object>> listob;
        List<Employment> employmentArrayList = new ArrayList<>();
        try {
            listob = ExcelUtil.getBankListByExcel(in,file.getOriginalFilename());
            //遍历listob数据，把数据放到List中
            for (int i = 1; i < listob.size(); i++) {
                List<Object> ob = listob.get(i);
                //通过遍历实现把每一列封装成一个model中，再把所有的model用List集合装载
                Employment employment = new Employment();
                //学院
                employment.setCollege(String.valueOf(ob.get(0)));
                //参与调查人数
                employment.setParNum(Integer.parseInt(String.valueOf(ob.get(1))));
                //专业知识能力：非常强，很强，一般，不强，很不强
                employment.setMB1111(Integer.parseInt(String.valueOf(ob.get(2))));
                employment.setMB1112(Integer.parseInt(String.valueOf(ob.get(3))));
                employment.setMB1113(Integer.parseInt(String.valueOf(ob.get(4))));
                employment.setMB1114(Integer.parseInt(String.valueOf(ob.get(5))));
                employment.setMB1115(Integer.parseInt(String.valueOf(ob.get(6))));
                //通用知识能力：非常强，很强，一般，不强，很不强
                employment.setMB1121(Integer.parseInt(String.valueOf(ob.get(7))));
                employment.setMB1122(Integer.parseInt(String.valueOf(ob.get(8))));
                employment.setMB1123(Integer.parseInt(String.valueOf(ob.get(9))));
                employment.setMB1124(Integer.parseInt(String.valueOf(ob.get(10))));
                employment.setMB1125(Integer.parseInt(String.valueOf(ob.get(11))));
                //求职应聘能力：非常强，很强，一般，不强，很不强
                employment.setMB1131(Integer.parseInt(String.valueOf(ob.get(12))));
                employment.setMB1132(Integer.parseInt(String.valueOf(ob.get(13))));
                employment.setMB1133(Integer.parseInt(String.valueOf(ob.get(14))));
                employment.setMB1134(Integer.parseInt(String.valueOf(ob.get(15))));
                employment.setMB1135(Integer.parseInt(String.valueOf(ob.get(16))));
                //社会兼职经历：1周，半月，1个月，2个月，3个月以上
                employment.setMB1211(Integer.parseInt(String.valueOf(ob.get(17))));
                employment.setMB1212(Integer.parseInt(String.valueOf(ob.get(18))));
                employment.setMB1213(Integer.parseInt(String.valueOf(ob.get(19))));
                employment.setMB1214(Integer.parseInt(String.valueOf(ob.get(20))));
                employment.setMB1215(Integer.parseInt(String.valueOf(ob.get(21))));
                //“非学历、费荣誉”证书：3个以上，3个，2个，1个，0个
                employment.setMB1221(Integer.parseInt(String.valueOf(ob.get(22))));
                employment.setMB1222(Integer.parseInt(String.valueOf(ob.get(23))));
                employment.setMB1223(Integer.parseInt(String.valueOf(ob.get(24))));
                employment.setMB1224(Integer.parseInt(String.valueOf(ob.get(25))));
                employment.setMB1225(Integer.parseInt(String.valueOf(ob.get(26))));
                //社会职务：有，无
                employment.setMB1231(Integer.parseInt(String.valueOf(ob.get(27))));
                employment.setMB1232(Integer.parseInt(String.valueOf(ob.get(28))));
                //求职积极程度：很积极，积极，一般，不积极，很不积极
                employment.setMB1311(Integer.parseInt(String.valueOf(ob.get(29))));
                employment.setMB1312(Integer.parseInt(String.valueOf(ob.get(30))));
                employment.setMB1313(Integer.parseInt(String.valueOf(ob.get(31))));
                employment.setMB1314(Integer.parseInt(String.valueOf(ob.get(32))));
                employment.setMB1315(Integer.parseInt(String.valueOf(ob.get(33))));
                //自我效能感人数：很自信，自信，一般，不自信，很不自信
                employment.setMB1321(Integer.parseInt(String.valueOf(ob.get(34))));
                employment.setMB1322(Integer.parseInt(String.valueOf(ob.get(35))));
                employment.setMB1323(Integer.parseInt(String.valueOf(ob.get(36))));
                employment.setMB1324(Integer.parseInt(String.valueOf(ob.get(37))));
                employment.setMB1325(Integer.parseInt(String.valueOf(ob.get(38))));
                //就业起薪：2001-3000，3001-4000，4001-5000，5001-6000，6001-7000，7000以上
                employment.setMB211(Integer.parseInt(String.valueOf(ob.get(39))));
                employment.setMB212(Integer.parseInt(String.valueOf(ob.get(40))));
                employment.setMB213(Integer.parseInt(String.valueOf(ob.get(41))));
                employment.setMB214(Integer.parseInt(String.valueOf(ob.get(42))));
                employment.setMB215(Integer.parseInt(String.valueOf(ob.get(43))));
                employment.setMB216(Integer.parseInt(String.valueOf(ob.get(44))));
                //专业对口状态：很对口，对口，一般，不对口，很不对口
                employment.setMB2211(Integer.parseInt(String.valueOf(ob.get(45))));
                employment.setMB2212(Integer.parseInt(String.valueOf(ob.get(46))));
                employment.setMB2213(Integer.parseInt(String.valueOf(ob.get(47))));
                employment.setMB2214(Integer.parseInt(String.valueOf(ob.get(48))));
                employment.setMB2215(Integer.parseInt(String.valueOf(ob.get(49))));
                //“能力-岗位”适配度：很匹配，匹配，一般，不匹配，很不匹配
                employment.setMB2221(Integer.parseInt(String.valueOf(ob.get(50))));
                employment.setMB2222(Integer.parseInt(String.valueOf(ob.get(51))));
                employment.setMB2223(Integer.parseInt(String.valueOf(ob.get(52))));
                employment.setMB2224(Integer.parseInt(String.valueOf(ob.get(53))));
                employment.setMB2225(Integer.parseInt(String.valueOf(ob.get(54))));
                //月薪兑付状态：正常，拖欠
                employment.setMB2311(Integer.parseInt(String.valueOf(ob.get(55))));
                employment.setMB2312(Integer.parseInt(String.valueOf(ob.get(56))));
                //“五险一金”执行状态：正常，拖欠
                employment.setMB2321(Integer.parseInt(String.valueOf(ob.get(57))));
                employment.setMB2322(Integer.parseInt(String.valueOf(ob.get(58))));
                //成长发展空间：很宽广，宽广，一般，不宽广，很不宽广
                employment.setMB2331(Integer.parseInt(String.valueOf(ob.get(59))));
                employment.setMB2332(Integer.parseInt(String.valueOf(ob.get(60))));
                employment.setMB2333(Integer.parseInt(String.valueOf(ob.get(61))));
                employment.setMB2334(Integer.parseInt(String.valueOf(ob.get(62))));
                employment.setMB2335(Integer.parseInt(String.valueOf(ob.get(63))));
                //工作满意度：很满意，满意，一般，不满意，很不满意
                employment.setMB2341(Integer.parseInt(String.valueOf(ob.get(64))));
                employment.setMB2342(Integer.parseInt(String.valueOf(ob.get(65))));
                employment.setMB2343(Integer.parseInt(String.valueOf(ob.get(66))));
                employment.setMB2344(Integer.parseInt(String.valueOf(ob.get(67))));
                employment.setMB2345(Integer.parseInt(String.valueOf(ob.get(68))));
                //预期就业年限：10年以上，8-10年，3-7年，2年，1年
                employment.setMB241(Integer.parseInt(String.valueOf(ob.get(69))));
                employment.setMB242(Integer.parseInt(String.valueOf(ob.get(70))));
                employment.setMB243(Integer.parseInt(String.valueOf(ob.get(71))));
                employment.setMB244(Integer.parseInt(String.valueOf(ob.get(72))));
                employment.setMB245(Integer.parseInt(String.valueOf(ob.get(73))));
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
}
