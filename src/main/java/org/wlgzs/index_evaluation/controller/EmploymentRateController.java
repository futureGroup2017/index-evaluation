package org.wlgzs.index_evaluation.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wlgzs.index_evaluation.enums.Result;
import org.wlgzs.index_evaluation.service.EmploymentRateService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author AlgerFan
 * @date Created in 2019/1/13 17
 * @Description 就业率指数
 */
@RestController
@RequestMapping("/employmentRate")
@Log4j2
public class EmploymentRateController {

    @Resource
    private EmploymentRateService employmentRateService;

    /**
     * 导入学院、初次就业率、年终就业率
     * @param request
     * @param year
     */
    @RequestMapping("/importData")
    public Result importData(int year,HttpServletRequest request){
        return employmentRateService.importData(year,request);
    }
}
