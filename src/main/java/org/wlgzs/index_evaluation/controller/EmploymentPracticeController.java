package org.wlgzs.index_evaluation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wlgzs.index_evaluation.enums.Result;
import org.wlgzs.index_evaluation.service.EmploymentPracticeService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author AlgerFan
 * @date Created in 2019/1/14 15
 * @Description 就业创业实践指数
 */
@RestController
@RequestMapping("/employmentPractice")
public class EmploymentPracticeController {

    @Resource
    private EmploymentPracticeService employmentPracticeService;

    @RequestMapping("/importData")
    private Result importData(int year, HttpServletRequest request){
        return employmentPracticeService.importData(year, request);
    }
}
