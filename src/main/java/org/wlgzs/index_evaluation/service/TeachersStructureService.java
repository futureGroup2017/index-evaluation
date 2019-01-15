package org.wlgzs.index_evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.index_evaluation.pojo.TeachersStructure;
import org.wlgzs.index_evaluation.pojo.Year;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

/**
 * @author zsh
 * @company wlgzs
 * @create 2019-01-13 10:20
 * @Describe
 */
public interface TeachersStructureService extends IService<TeachersStructure> {

    //批量导入
    @Transactional
    List<TeachersStructure> importExcelInfo(InputStream in, MultipartFile file);

    //批量导出
    @Transactional
    void export(Integer year, HttpServletResponse response);

    //单个添加
    @Transactional
    Integer add(TeachersStructure teachersStructure);

    //更新操作
    @Transactional
    Integer update(TeachersStructure teachersStructure);

    //查询所有数据
    @Transactional
    List<TeachersStructure> findAll();

    //根据年份查询
    @Transactional
    List<TeachersStructure> findByYear(Integer year);

    //删除
    @Transactional
    Integer delete(TeachersStructure teachersStructure);
}
