package org.wlgzs.index_evaluation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.index_evaluation.enums.Result;
import org.wlgzs.index_evaluation.pojo.EmploymentRate;
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

    /**
     * 查询全部就业率
     * @param model
     * @param pageNum
     * @param pageSize
     */
    @RequestMapping("/findAll")
    public ModelAndView findAll(Model model, @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                @RequestParam(name = "pageSize", defaultValue = "16") int pageSize){
        Page<EmploymentRate> ratePage = new Page<>(pageNum,pageSize);
        QueryWrapper<EmploymentRate> rateQueryWrapper = new QueryWrapper<>();
        IPage<EmploymentRate> page = employmentRateService.page(ratePage, rateQueryWrapper);
        model.addAttribute("current",page.getCurrent());  //当前页数
        model.addAttribute("pages",page.getPages());   //总页数
        model.addAttribute("employmentRate",page.getRecords());   //集合
        model.addAttribute("msg","查询成功");
        log.info("查询成功:"+page.getRecords());
        return new ModelAndView("test");
    }

    /**
     * 按照年份删除数据
     * @param year
     */
    @DeleteMapping("/deleteYear")
    public Result deleteYear(int year){
        return employmentRateService.deleteYear(year);
    }
}
