package org.wlgzs.index_evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.index_evaluation.pojo.TeachersStructure;

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

    //单个添加
    @Transactional
    Integer add(TeachersStructure teachersStructure);
}
