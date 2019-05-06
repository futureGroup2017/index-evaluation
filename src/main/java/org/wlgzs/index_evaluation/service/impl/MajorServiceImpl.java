package org.wlgzs.index_evaluation.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.index_evaluation.dao.MajorDao;
import org.wlgzs.index_evaluation.pojo.Major;
import org.wlgzs.index_evaluation.service.MajorService;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author 武凯焱
 * @date 2019/1/13 11:15
 * @Description:
 */
@Service
@Log4j2
public class MajorServiceImpl extends ServiceImpl<MajorDao,Major> implements MajorService {
    @Override
    public boolean importExcel(@RequestParam("file") MultipartFile file) throws IOException {
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

        Sheet sheet = wb.getSheetAt(0);
        //先获取第一个学院的名字
        String colleage_name  = sheet.getRow(1).getCell(0).getStringCellValue();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            String tempColleageName = row.getCell(0).getStringCellValue();
            String majorName = row.getCell(1).getStringCellValue();

            if ((tempColleageName!=null && !tempColleageName.equals(""))){
                Major major = new Major(majorName,tempColleageName);
                baseMapper.insert(major);
                colleage_name = tempColleageName;
            }
            else {
                Major major = new Major(majorName,colleage_name);
                baseMapper.insert(major);
            }
        }
        return true;
    }


}
