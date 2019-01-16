package org.wlgzs.index_evaluation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.index_evaluation.pojo.EmploymentPractice;
import org.wlgzs.index_evaluation.pojo.Query;
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
    @PostMapping("/importData")
    public ModelAndView importData(Integer year,Model model, HttpServletRequest request){
        if(year==null){
            model.addAttribute("msg","请选择年份");
            log.info("请选择年份");
            return new ModelAndView("redirect:/employmentRate/findAll");
        }
        if(employmentPracticeService.importData(year, request)){
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
     * 导出就业创业实践指数
     * @param year
     * @param response
     * @throws IOException
     */
    @GetMapping("/exportData")
    public void exportData(int year, HttpServletResponse response) throws IOException {
        employmentPracticeService.exportData(year, response);
    }

    /**
     * 查询全部就业创业实践
     * @param model
     * @param pageNum
     * @param pageSize
     */
    @GetMapping("/findAll")
    public ModelAndView findAll(Query query, Model model, @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                @RequestParam(name = "pageSize", defaultValue = "16") int pageSize){
        Page<EmploymentPractice> practicePage = new Page<>(pageNum,pageSize);
        QueryWrapper<EmploymentPractice> practiceQueryWrapper = new QueryWrapper<>();
        if (query.getYear() != null){
            practiceQueryWrapper.eq("year",query.getYear());
        }
        if (query.getCollege() != "" && query.getCollege() != null){
            practiceQueryWrapper.eq("college",query.getCollege());
        }
        IPage<EmploymentPractice> page = employmentPracticeService.page(practicePage, practiceQueryWrapper);
        model.addAttribute("current",page.getCurrent());  //当前页数
        model.addAttribute("pages",page.getPages());   //总页数
        model.addAttribute("lists",page.getRecords());   //集合
        model.addAttribute("query",query);
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
    public ModelAndView deleteYear(Integer year,Model model){
        if(year==null){
            model.addAttribute("msg","请选择年份");
            log.info("请选择年份");
            return new ModelAndView("redirect:/employmentRate/findAll");
        }
        if(employmentPracticeService.deleteYear(year)){
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
