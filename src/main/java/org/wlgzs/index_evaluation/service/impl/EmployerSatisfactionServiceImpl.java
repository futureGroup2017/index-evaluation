package org.wlgzs.index_evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.index_evaluation.dao.EmployerSatisfactionMapper;
import org.wlgzs.index_evaluation.pojo.EmployerSatisfaction;
import org.wlgzs.index_evaluation.service.EmployerSatisfactionService;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 武凯焱
 * @date 2019/1/14 8:10
 * @Description:
 */
@Service
public class EmployerSatisfactionServiceImpl extends ServiceImpl<EmployerSatisfactionMapper, EmployerSatisfaction> implements EmployerSatisfactionService {
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
            int level1 = Integer.parseInt(row.getCell(1).getStringCellValue());
            int level2 = Integer.parseInt(row.getCell(2).getStringCellValue());
            int level3 = Integer.parseInt(row.getCell(3).getStringCellValue());
            int level4 = Integer.parseInt(row.getCell(4).getStringCellValue());
            int level5 = Integer.parseInt(row.getCell(5).getStringCellValue());
            int num =  level1+level2+level3+level4+level5;
            double level  =(double) Math.round((level1+level2*0.8d+level3*0.6d+level4*0.4d+level5*0.2d)*100d/num*0.1895d*1000d)/1000d;
            int ability1 = Integer.parseInt(row.getCell(6).getStringCellValue());
            int ability2 = Integer.parseInt(row.getCell(7).getStringCellValue());
            int ability3 = Integer.parseInt(row.getCell(8).getStringCellValue());
            int ability4 = Integer.parseInt(row.getCell(9).getStringCellValue());
            int ability5 = Integer.parseInt(row.getCell(10).getStringCellValue());
            int num1 =  ability1+ability2+ability3+ability4+ability5;
            double ability  =(double) Math.round((ability1+ability2*0.8d+ability3*0.6d+ability4*0.4d+ability5*0.2d)*100d/num1*0.242d*1000d)/1000;
            int match1 = Integer.parseInt(row.getCell(11).getStringCellValue());
            int match2 = Integer.parseInt(row.getCell(12).getStringCellValue());
            int match3 = Integer.parseInt(row.getCell(13).getStringCellValue());
            int match4 = Integer.parseInt(row.getCell(14).getStringCellValue());
            int match5 = Integer.parseInt(row.getCell(15).getStringCellValue());
            int num2 =  match1+match2+match3+match4+match5;
            double match  =(double) Math.round((match1+match2*0.8d+match3*0.6d+match4*0.4d+match5*0.2d)*100d/num2*0.2225d*1000d)/1000d;
            System.out.println((match1*1.0d+match2*0.8d+match3*0.6d+match4*0.4d+match5*0.2d)*100d/num2*0.2225d+" dfhsdufghsdjghsghgh");
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
}
