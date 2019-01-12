package org.wlgzs.index_evaluation.controller;

import com.baomidou.mybatisplus.plugins.Page;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.index_evaluation.pojo.Year;
import org.wlgzs.index_evaluation.service.YearService;

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

    //添加年份
    @PostMapping("add")
    public void add(Year year){
        if (yearService.findByName(year.getYearName()) == null){
            Integer add = yearService.add(year);
            log.info(add);
        }else {
            log.info("重复");
        }
    }

    //删除年份
    @GetMapping("/delete")
    public void delete(Integer id){
        log.info(id);
        Integer delete = yearService.delete(id);
        log.info(delete);
    }

    //修改年份
    @PostMapping("/update")
    public void update(Year year){
        Integer update = yearService.update(year);
        log.info(update);
    }

    //通过name查询
    @RequestMapping("findByName")
    public void findName(Integer name){
        Year byName = yearService.findByName(name);
        log.info(byName);
    }

    //年份查询
    @RequestMapping("all")
    public ModelAndView selectAll(@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                  @RequestParam(name = "pageSize", defaultValue = "10") int pageSize){
        ModelAndView modelAndView = new ModelAndView();
        Page<Year> pageInfo = yearService.findAllYear(pageNum, pageSize);
        modelAndView.addObject("Number", pageInfo.getCurrent());  //当前页数
        modelAndView.addObject("TotalPages", pageInfo.getPages());      //总页数
        modelAndView.addObject("years", pageInfo.getRecords());    //年份集合
        modelAndView.setViewName("a");
        log.info("所有年份信息为："+pageInfo.getRecords());
        return modelAndView;
    }

}
