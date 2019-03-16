package org.wlgzs.index_evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.index_evaluation.pojo.EmployerSatisfaction;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author 武凯焱
 * @date 2019/1/14 8:09
 * @Description:
 */
public interface EmployerSatisfactionService extends IService<EmployerSatisfaction> {
    @Transactional
    //导入excel表
    List<EmployerSatisfaction> importExcel(MultipartFile file,String year)throws IOException;
    //数据添加到数据库
    @Transactional
    boolean add(List<EmployerSatisfaction> employerSatisfactions);
    //导出excel表
    void exportData(int year, HttpServletResponse response) throws IOException;
    //删除数据
    boolean delete(String year); @Transactional
        //导入excel表
    boolean NewImportExcel(MultipartFile file,String year)throws IOException;

}
