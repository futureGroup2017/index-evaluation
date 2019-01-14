package org.wlgzs.index_evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.index_evaluation.pojo.Employment;

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
}
