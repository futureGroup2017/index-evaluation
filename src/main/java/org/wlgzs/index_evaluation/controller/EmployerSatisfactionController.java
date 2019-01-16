package org.wlgzs.index_evaluation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.index_evaluation.pojo.EmployerSatisfaction;
import org.wlgzs.index_evaluation.pojo.Query;
import org.wlgzs.index_evaluation.service.EmployerSatisfactionService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author 武凯焱
 * @date 2019/1/13 17:48
 * @Description:
 */
@RestController
@RequestMapping("/es")
@Log4j2
public class EmployerSatisfactionController {

    @Resource
    private EmployerSatisfactionService  empService;

    /**
     * 导入原始数据
     * @param multipartFile
     * @param year
     * @throws IOException
     */
    @PostMapping("/import")
    public void importExcel(@RequestParam("file") MultipartFile multipartFile,String year) throws IOException {
        List<EmployerSatisfaction> list = empService.importExcel(multipartFile,year);
        boolean isTrue =  empService.add(list);
        System.out.println(isTrue);
        }

    /**
     * 导出数据
     * @param response
     * @param year
     * @throws IOException
     */
    @GetMapping ("/export")
    public void importExcel(HttpServletResponse response,String year) throws IOException {
        if (year!=null && !year.equals(""))
        empService.exportData(Integer.parseInt(year),response);
    }
    @GetMapping("/search")
    public ModelAndView search(Model model, Query query,
                               @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                               @RequestParam(name = "pageSize", defaultValue = "16") int pageSize)  {
        Page<EmployerSatisfaction> employerSatisfactionPage = new Page<>(pageNum,pageSize);
        QueryWrapper<EmployerSatisfaction> employerSatisfactionQueryWrapper  = new QueryWrapper<>();
        if (query.getYear()!=null){
            employerSatisfactionQueryWrapper.eq("year",query.getYear());
        }
        if (query.getCollege() != null && !query.getCollege().equals("")){
            employerSatisfactionQueryWrapper.eq("college",query.getCollege());
        }
        IPage<EmployerSatisfaction> iPage = empService.page(employerSatisfactionPage,employerSatisfactionQueryWrapper);
        model.addAttribute("current",iPage.getCurrent());//当前页数
        model.addAttribute("pages",iPage.getPages());//总页数
        model.addAttribute("employerSatisfactions",iPage.getRecords());//所有的数据集合
        model.addAttribute("query",query);
        return new ModelAndView("");
    }
    @GetMapping("/delete")
    public ModelAndView delete(Model model,Integer year,
                               @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                               @RequestParam(name = "pageSize", defaultValue = "16") int pageSize){
    empService.delete(model,year,pageNum,pageSize);
    return new ModelAndView();
    }
    @GetMapping("")
    public ModelAndView findAll(Model model, @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                @RequestParam(name = "pageSize", defaultValue = "16") int pageSize){
        Page<EmployerSatisfaction> employerSatisfactionPage = new Page<>(pageNum,pageSize);
        QueryWrapper<EmployerSatisfaction> employerSatisfactionQueryWrapper  = new QueryWrapper<>();
        IPage<EmployerSatisfaction> iPage = empService.page(employerSatisfactionPage,employerSatisfactionQueryWrapper);
        model.addAttribute("employerSatisfactions",iPage.getRecords());
        return new ModelAndView("employment");

    }

}
