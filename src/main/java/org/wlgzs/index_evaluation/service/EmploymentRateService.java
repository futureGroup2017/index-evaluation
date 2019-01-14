package org.wlgzs.index_evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.wlgzs.index_evaluation.enums.Result;
import org.wlgzs.index_evaluation.pojo.EmploymentRate;

import javax.servlet.http.HttpServletRequest;

/**
 * @author AlgerFan
 * @date Created in 2019/1/13 17
 * @Description
 */
public interface EmploymentRateService extends IService<EmploymentRate> {

    /**
     * 导入学院、初次就业率、年终就业率
     * @param year
     * @param request
     */
    Result importData(int year, HttpServletRequest request);
}
