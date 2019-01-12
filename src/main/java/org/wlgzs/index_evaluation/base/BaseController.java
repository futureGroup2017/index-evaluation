package org.wlgzs.index_evaluation.base;

import org.wlgzs.index_evaluation.service.YearService;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * @description 简化控制层代码
 */
public abstract class BaseController implements Serializable {

    //自动注入业务层
    @Resource
    private YearService yearService;
    //@Resource
    //private CollegeService collegeService;
}
