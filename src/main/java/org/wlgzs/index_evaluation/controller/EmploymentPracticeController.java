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
import org.wlgzs.index_evaluation.pojo.EmploymentPractice;
import org.wlgzs.index_evaluation.service.EmploymentPracticeService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author AlgerFan
 * @date Created in 2019/1/14 15
 * @Description 就业创业实践指数
 */
@RestController
@RequestMapping("/employmentPractice")
@Log4j2
public class EmploymentPracticeController {

    @Resource
    private EmploymentPracticeService employmentPracticeService;

    /**
     * 导入就业创业实践数据
     * @param year
     * @param request
     */
    @RequestMapping("/importData")
    public Result importData(int year, HttpServletRequest request){
        return employmentPracticeService.importData(year, request);
    }
    @RequestMapping("/exportData")
    public void exportData(int year, HttpServletResponse response) throws IOException {
        employmentPracticeService.exportData(year, response);
    }

    /**
     * 查询全部就业创业实践
     * @param model
     * @param pageNum
     * @param pageSize
     */
    @RequestMapping("/findAll")
    public ModelAndView findAll(Model model, @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                @RequestParam(name = "pageSize", defaultValue = "16") int pageSize){
        Page<EmploymentPractice> practicePage = new Page<>(pageNum,pageSize);
        QueryWrapper<EmploymentPractice> practiceQueryWrapper = new QueryWrapper<>();
        IPage<EmploymentPractice> page = employmentPracticeService.page(practicePage, practiceQueryWrapper);
        model.addAttribute("current",page.getCurrent());  //当前页数
        model.addAttribute("pages",page.getPages());   //总页数
        model.addAttribute("employmentPractices",page.getRecords());   //集合
        Set<Integer> years = new HashSet<>();
        for (int i = 0; i < page.getRecords().size(); i++) {
            years.add(page.getRecords().get(i).getYear());
        }
        model.addAttribute("allYear",years);//年份
        model.addAttribute("msg","查询成功");
        log.info("查询成功:"+page.getRecords());
        return new ModelAndView("employmentPractice");
    }

    /**
     * 按照年份删除数据
     * @param year
     */
    @DeleteMapping("/deleteYear")
    public Result deleteYear(int year){
        return employmentPracticeService.deleteYear(year);
    }


}
