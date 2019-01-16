package org.wlgzs.index_evaluation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.index_evaluation.pojo.EmploymentRate;
import org.wlgzs.index_evaluation.pojo.Query;
import org.wlgzs.index_evaluation.service.EmploymentRateService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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
    @PostMapping("/importData")
    public ModelAndView importData(Integer year,Model model,HttpServletRequest request){
        if(year==null){
            model.addAttribute("msg","请选择年份");
            log.info("请选择年份");
            return new ModelAndView("redirect:/employmentRate/findAll");
        }
        if(employmentRateService.importData(year,request)){
            model.addAttribute("msg","导入成功");
            log.info("导入成功");
            return new ModelAndView("redirect:/employmentRate/findAll");
        } else {
            model.addAttribute("msg","导入失败");
            log.info("导入失败");
            return new ModelAndView("redirect:/employmentRate/findAll");
        }
    }

    /**
     * 导出就业率指数
     * @param year
     * @param response
     * @throws IOException
     */
    @GetMapping("/exportData")
    public void exportData(int year, HttpServletResponse response) throws IOException {
        employmentRateService.exportData(year, response);
    }

    /**
     * 查询全部就业率
     * @param model
     * @param pageNum
     * @param pageSize
     */
    @GetMapping("/findAll")
    public ModelAndView findAll(Query query, Model model, @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                @RequestParam(name = "pageSize", defaultValue = "16") int pageSize){
        Page<EmploymentRate> ratePage = new Page<>(pageNum,pageSize);
        QueryWrapper<EmploymentRate> rateQueryWrapper = new QueryWrapper<>();
        if (query.getYear() != null){
            rateQueryWrapper.eq("year",query.getYear());
        }
        if (query.getCollege() != "" && query.getCollege() != null){
            rateQueryWrapper.eq("college",query.getCollege());
        }
        IPage<EmploymentRate> page = employmentRateService.page(ratePage, rateQueryWrapper);
        Set<Integer> years = new HashSet<>();
        for (int i = 0; i < page.getRecords().size(); i++) {
            years.add(page.getRecords().get(i).getYear());
        }
        model.addAttribute("current",page.getCurrent());  //当前页数
        model.addAttribute("pages",page.getPages());   //总页数
        model.addAttribute("lists",page.getRecords());   //集合
        model.addAttribute("query",query);
        model.addAttribute("allYear",years);//年份
        model.addAttribute("msg","查询成功");
        log.info("查询成功:"+page.getRecords());
        return new ModelAndView("employmentRate");
    }

    /**
     * 按照年份删除数据
     * @param year
     */
    @DeleteMapping("/deleteYear")
    public ModelAndView deleteYear(Integer year,Model model){
        if(year==null){
            model.addAttribute("msg","请选择年份");
            log.info("请选择年份");
            return new ModelAndView("redirect:/employmentRate/findAll");
        }
        if(employmentRateService.deleteYear(year)){
            model.addAttribute("msg","删除成功");
            log.info("删除成功");
            return new ModelAndView("redirect:/employmentRate/findAll");
        } else {
            model.addAttribute("msg","删除失败");
            log.info("删除失败");
            return new ModelAndView("redirect:/employmentRate/findAll");
        }
    }
}
