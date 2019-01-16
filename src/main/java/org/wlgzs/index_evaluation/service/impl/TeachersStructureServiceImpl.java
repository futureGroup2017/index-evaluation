package org.wlgzs.index_evaluation.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.index_evaluation.dao.TeachersStructureMapper;
import org.wlgzs.index_evaluation.pojo.EmploymentRate;
import org.wlgzs.index_evaluation.pojo.TeachersStructure;
import org.wlgzs.index_evaluation.service.TeachersStructureService;
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
 * @create 2019-01-13 10:21
 * @Describe
 */
@Log4j2
@Service
public class TeachersStructureServiceImpl extends ServiceImpl<TeachersStructureMapper, TeachersStructure> implements TeachersStructureService {

    @Override
    public List<TeachersStructure> importExcelInfo(InputStream in, MultipartFile file) {
        List<List<Object>> listob;
        List<TeachersStructure> teachersStructuresList = new ArrayList<>();
        DecimalFormat df1 = new DecimalFormat("#");
        try {
            listob = ExcelUtil.getBankListByExcel(in,file.getOriginalFilename());
            //遍历listob数据，把数据放到List中
            for (int i = 0; i < listob.size(); i++) {
                List<Object> ob = listob.get(i);
                TeachersStructure t = new TeachersStructure();
                //通过遍历实现把每一列封装成一个model中，再把所有的model用List集合装载
                t.setCollegeName(String.valueOf(ob.get(0)));
                t.setStuNum(Integer.parseInt(String.valueOf(ob.get(1))));
                t.setTeaNum(Double.parseDouble(String.valueOf(ob.get(2))));
                t.setGraNum(Integer.parseInt(String.valueOf(ob.get(3))));
                t.setSenNum(Integer.parseInt(String.valueOf(ob.get(4))));
                teachersStructuresList.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teachersStructuresList;
    }

    @Override
    public void export(Integer year, HttpServletResponse response) {
        QueryWrapper<TeachersStructure> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("year",year);
        List<TeachersStructure> SignList = baseMapper.selectList(queryWrapper);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("师资结构指数导出表");
        sheet.setDefaultRowHeightInPoints(20);
        HSSFPrintSetup ps = sheet.getPrintSetup();
        ps.setLandscape(false); // 打印方向，true：横向，false：纵向
        ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE); //纸张
        sheet.setHorizontallyCenter(true);//设置打印页面为水平居中

        //设置要导出的文件的名字
        String fileName = year+"师资结构指数.xls";
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        String[] headers = { "学院", "在校生数", "教师总数", "研究生学位教师总数", "高级职务教师总数",
                "生师比", "研究生学位教师占专任教师比例","高级职务教师占专任教师比例" ,
                "生师比合格值","生师比积分","研究生学位教师积分","高级职务教师积分",
                "生师比33.7","高学历教师占比32.9","高职称教师占比33.9","师资结构指数11.07"};
        //headers表示excel表中第一行的表头
        HSSFRow row1 = sheet.createRow(0);
        //设置行高
        row1.setHeightInPoints(30);
        //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
        for (int i = 0;i<16;i++){
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
            TeachersStructure teachersStructure = SignList.get(i);// 获取sign对象
            HSSFRow row = sheet.createRow(rowNum);
            //设置行高
            row.setHeightInPoints(25);
            //学院
            if (teachersStructure.getCollegeName() != null) {
                cell = row.createCell(0);// 创建第i+1行第0列
                cell.setCellValue(teachersStructure.getCollegeName());// 设置第i+1行第0列的值
                cell.setCellStyle(style);// 设置风格
            }
            //在校生数
            if (teachersStructure.getStuNum() != null) {
                cell = row.createCell(1); // 创建第i+1行第1列
                cell.setCellValue(teachersStructure.getStuNum());// 设置第i+1行第1列的值
                cell.setCellStyle(style); // 设置风格
            }
            //教师总数
            cell = row.createCell(2);
            cell.setCellValue(teachersStructure.getTeaNum());
            cell.setCellStyle(style);
            //研究生学位教师总数
            cell = row.createCell(3);
            cell.setCellValue(teachersStructure.getGraNum());
            cell.setCellStyle(style);
            //高级职务教师总数
            cell = row.createCell(4);
            cell.setCellValue(teachersStructure.getSenNum());
            cell.setCellStyle(style);
            //生师比
            cell = row.createCell(5);
            cell.setCellValue(teachersStructure.getB21());
            cell.setCellStyle(style);
            //研究生学位教师占专任教师比例
            cell = row.createCell(6);
            cell.setCellValue(teachersStructure.getB22());
            cell.setCellStyle(style);
            //高级职务教师占专任教师比例
            cell = row.createCell(7);
            cell.setCellValue(teachersStructure.getB23());
            cell.setCellStyle(style);
            //生师比合格值
            cell = row.createCell(8);
            cell.setCellValue(teachersStructure.getQualified());
            cell.setCellStyle(style);
            //生师比积分
            cell = row.createCell(9);
            cell.setCellValue(teachersStructure.getM1());
            cell.setCellStyle(style);
            //研究生学位教师积分
            cell = row.createCell(10);
            cell.setCellValue(teachersStructure.getM2());
            cell.setCellStyle(style);
            //高级职务教师积分
            cell = row.createCell(11);
            cell.setCellValue(teachersStructure.getM3());
            cell.setCellStyle(style);
            //生师比33.7
            cell = row.createCell(12);
            cell.setCellValue(teachersStructure.getW1());
            cell.setCellStyle(style);
            //高学历教师占比32.9
            cell = row.createCell(13);
            cell.setCellValue(teachersStructure.getW2());
            cell.setCellStyle(style);
            //高职称教师占比33.9
            cell = row.createCell(14);
            cell.setCellValue(teachersStructure.getW3());
            cell.setCellStyle(style);
            //师资结构指数11.07
            cell = row.createCell(15);
            cell.setCellValue(teachersStructure.getA2());
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
    public Integer add(TeachersStructure teachersStructure) {
        return baseMapper.insert(teachersStructure);
    }

    @Override
    public Integer update(TeachersStructure teachersStructure) {
        return baseMapper.updateById(teachersStructure);
    }

    @Override
    public List<TeachersStructure> findAll() {
        return baseMapper.selectList(null);
    }

    @Override
    public List<TeachersStructure> findByYear(Integer year) {
        QueryWrapper<TeachersStructure> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("year",year);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public Integer delete(TeachersStructure teachersStructure) {
        return baseMapper.deleteById(teachersStructure.getTsId());
    }
}
