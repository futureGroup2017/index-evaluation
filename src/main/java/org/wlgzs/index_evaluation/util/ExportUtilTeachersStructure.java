package org.wlgzs.index_evaluation.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.wlgzs.index_evaluation.pojo.TeachersStructure;

@Log4j2
public class ExportUtilTeachersStructure {
	/*
	 * 因为我这里这个函数要在action类里调用，下面要导出的数据要在数据库里先查询出来，所以里面的函数要传过来一个service对象，
	 * 也就是业务逻辑类的对象，以用来实现相应的业务逻辑
	 */

	public String export(List<TeachersStructure> SignList,
						 HttpServletResponse response,Integer year) throws Exception {

		/*
		 * 设置表头：对Excel每列取名 (必须根据你取的数据编写)
		 */
		String[] tableHeader = { "学院", "在校生数", "教师总数", "研究生学位教师总数", "高级职务教师总数",
				"生师比", "研究生学位教师占专任教师比例","高级职务教师占专任教师比例" ,
		"生师比合格值","生师比积分","研究生学位教师积分","高级职务教师积分",
		"生师比33.7","高学历教师占比32.9","高职称教师占比33.9","师资结构指数11.07"};
		/*
		 * 下面的都可以拷贝不用编写
		 */
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = workbook.createCellStyle(); // 设置表头的类型
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFCellStyle style1 = workbook.createCellStyle(); // 设置数据类型
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("师资结构指数导出表"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		try { /**
				 * 根据是否取出数据，设置header信息
				 *
				 */
			if (SignList.size() < 1) {
				header.setCenter("本次导出没有数据");
			} else {
				header.setCenter("师资结构指数导出表");
				row = sheet.createRow(0);
				row.setHeight((short) 400);
				for (int k = 0; k < cellNumber; k++) {
					cell = row.createCell(k);// 创建第0行第k列
					cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
					sheet.setColumnWidth(k, 8000);// 设置列的宽度
					font.setColor(HSSFFont.COLOR_NORMAL); // 设置单元格字体的颜色.
					font.setFontHeight((short) 350); // 设置单元字体高度
					style1.setFont(font);// 设置字体风格
					cell.setCellStyle(style1);
				}
				/*
				 * // 给excel填充数据这里需要编写
				 * 
				 */
				for (int i = 0; i < SignList.size(); i++) {
					TeachersStructure teachersStructure = SignList.get(i);// 获取sign对象
					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高

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
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * 下面的可以不用编写，直接拷贝
		 *
		 */
		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition", "attachment;filename=" +year+"师资结构指数.xls");
		response.flushBuffer();
		workbook.write(response.getOutputStream());
		return null;
	}
}