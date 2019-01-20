package org.wlgzs.index_evaluation.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.index_evaluation.enums.Result;
import org.wlgzs.index_evaluation.pojo.Query;
import org.wlgzs.index_evaluation.pojo.StudentQuality;
import org.wlgzs.index_evaluation.pojo.Year;
import org.wlgzs.index_evaluation.service.StudentQualityService;
import org.wlgzs.index_evaluation.service.YearService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author 武凯焱
 * @date 2019/1/15 17:31
 * @Description:
 */
@RestController()
@RequestMapping("/sq")
public class StudentQualityController {
    @Resource
    StudentQualityService studentQualityService;
    @Resource
    YearService yearService;
    @PostMapping("import")
    public Result importExcel(MultipartFile file, String year){
        try {
          boolean isTrue =  studentQualityService.importExcel(file, year);
          if (isTrue){
              return new Result(1,"导入成功");
          }
          else {
              return new Result(0,"导入失败");
          }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(1,"导入成功");
    }
    @GetMapping("/search")
    public ModelAndView search(Model model, Query query,
                               @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                               @RequestParam(name = "pageSize", defaultValue = "16") int pageSize)  {
        Page<StudentQuality> page = new Page<>(pageNum,pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        if (query.getYear()!=null){
            queryWrapper.eq("yaer",query.getYear());
        }
        if (query.getMajorName()!=null){
            queryWrapper.eq("major_name",query.getMajorName());
        }
        List<Year> allYear = yearService.findAllYear();
        model.addAttribute("allYear",allYear);
        IPage<StudentQuality> iPage = studentQualityService.page(page,queryWrapper);
        model.addAttribute("current",iPage.getCurrent());//当前页数
        model.addAttribute("pages",iPage.getPages());//总页数
        model.addAttribute("employerSatisfactions",iPage.getRecords());//所有的数据集合
        model.addAttribute("query",query);
        return new ModelAndView("");
    }
    @GetMapping("/delete")
    public Result delete(Integer year){
        boolean isDelete = studentQualityService.delete(year);
        if (isDelete){
            return new Result(1,"删除成功");
        }
        else {
            return  new Result(-1,"没有该年份数据");
        }
    }
    @GetMapping("/searchColleage")
    public ModelAndView searchColleage(Model model, Query query,
                               @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                               @RequestParam(name = "pageSize", defaultValue = "16") int pageSize)  {
        Page<StudentQuality> page = new Page<>(pageNum,pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        if (query.getYear()!=null){
            queryWrapper.eq("yaer",query.getYear());
        }
        if (query.getMajorName()!=null){
            queryWrapper.eq("colleage_name",query.getMajorName());
        }
        List<Year> allYear = yearService.findAllYear();
        model.addAttribute("allYear",allYear);
        IPage<StudentQuality> iPage = studentQualityService.page(page,queryWrapper);
        model.addAttribute("current",iPage.getCurrent());//当前页数
        model.addAttribute("pages",iPage.getPages());//总页数
        model.addAttribute("employerSatisfactions",iPage.getRecords());//所有的数据集合
        model.addAttribute("query",query);
        return new ModelAndView("");
    }
    @GetMapping ("/export")
    public void importExcel(HttpServletResponse response, String year) throws IOException {
        Result result;
        result = new Result(-1,"导出失败");
        try{
        studentQualityService.exportData(Integer.parseInt(year),response);
            }catch (Exception e){
            result = new Result(-1,"导出失败");
        }


    }

}
