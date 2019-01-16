package org.wlgzs.index_evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.index_evaluation.pojo.Employment;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

/**
 * @author zsh
 * @company wlgzs
 * @create 2019-01-14 10:01
 * @Describe
 */
public interface EmploymentService extends IService<Employment>{

    //批量导入
    @Transactional
    List<Employment> importExcelInfo(InputStream in, MultipartFile file);

    //添加
    @Transactional
    Integer add(Employment employment);

    //批量导出
    @Transactional
    void export(Integer year, HttpServletResponse response);

    //删除
    @Transactional
    Integer delete(Employment employment);

    //通过年份查询
    @Transactional
    List<Employment> findByYear(Integer year);
}
