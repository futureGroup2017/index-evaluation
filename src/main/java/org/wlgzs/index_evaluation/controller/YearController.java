package org.wlgzs.index_evaluation.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.index_evaluation.pojo.Year;
import org.wlgzs.index_evaluation.service.YearService;

import java.util.List;

/**
 * @author zsh
 * @company wlgzs
 * @create 2019-01-12 15:32
 * @Describe 年份控制器
 */

@RestController
@RequestMapping("/year")
@Log4j2
public class YearController {

    @Autowired
    private YearService yearService;

    @GetMapping("/to")
    public ModelAndView to(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("yearAdministration");
        modelAndView.addObject("allYear",yearService.findAllYear());
        return modelAndView;
    }

    //添加年份
    @PostMapping("add")
    @ResponseBody
    public Integer add(Year year){
        log.info(year);
        if (yearService.findByName(year.getYearName()) == null){
            Integer add = yearService.add(year);
            return add;
        }else {
            log.info("重复");
            return 0;
        }
    }

    //删除年份
    @GetMapping("/delete")
    @ResponseBody
    public Integer delete(Integer id){
        log.info(id);
        Integer delete = yearService.delete(id);
        log.info(delete);
        return delete;
    }

    //修改年份
    @PostMapping("/update")
    @ResponseBody
    public Integer update(Year year){
        Integer update = 0;
        if (yearService.findByName(year.getYearName()) == null){
            update = yearService.update(year);
        }
        log.info(update);
        return update;
    }

    //通过name查询
    @RequestMapping("/findByName")
    public ModelAndView findName(Integer name){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("yearAdministration");
        if (name == null){
            List<Year> allYear = yearService.findAllYear();
            modelAndView.addObject("allYear",allYear);
        }else {
            Year byName = yearService.findByName(name);
            modelAndView.addObject("allYear",byName);
        }
        return modelAndView;
    }
}
