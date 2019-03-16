package org.wlgzs.index_evaluation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.index_evaluation.enums.Result;
import org.wlgzs.index_evaluation.enums.ResultCodeEnum;
import org.wlgzs.index_evaluation.pojo.EmployerSatisfaction;
import org.wlgzs.index_evaluation.pojo.Query;
import org.wlgzs.index_evaluation.pojo.Year;
import org.wlgzs.index_evaluation.service.EmployerSatisfactionService;
import org.wlgzs.index_evaluation.service.YearService;

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
    private EmployerSatisfactionService empService;
    @Resource
    private YearService yearService;

    /**
     * 导入中间数据
     *
     * @param multipartFile
     * @param year
     * @throws IOException
     */
    @PostMapping("/oederImport")
    public Result orderImportExcel(@RequestParam("file") MultipartFile multipartFile, String year) throws IOException {
        if (multipartFile != null) {
            String string = multipartFile.getOriginalFilename();
            if (!string.contains("5.用人单位满意度指数样表.xlsx")) {
                return new Result(-1, "请确认文件名是否为--<5.用人单位满意度样表.xlsx>");
            }
        }
        QueryWrapper<EmployerSatisfaction> queryWrapper = new QueryWrapper<EmployerSatisfaction>();
        if (year != null && !year.equals("")) {
            queryWrapper.eq("year", Integer.parseInt(year));
            queryWrapper.last("limit 2");
            List<EmployerSatisfaction> list = empService.list(queryWrapper);
            if (list != null && list.size() > 0) {
                return new Result(0, "导入数据重复");
            }
        }
        List<EmployerSatisfaction> list = empService.importExcel(multipartFile, year);
        boolean isTrue = empService.add(list);
        if (isTrue) {
            return new Result(1, "导入成功");
        } else {
            return new Result(-1, "导入失败");
        }

    }

    /**
     * 导出数据
     *
     * @param response
     * @param year
     * @throws IOException
     */
    @GetMapping("/export")
    public void importExcel(HttpServletResponse response, String year) throws IOException {
        if (year != null && !year.equals(""))
            empService.exportData(Integer.parseInt(year), response);
    }

    @GetMapping("/search")
    public ModelAndView search(Model model, Query query,
                               @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                               @RequestParam(name = "pageSize", defaultValue = "16") int pageSize) {
        Page<EmployerSatisfaction> employerSatisfactionPage = new Page<>(pageNum, pageSize);
        QueryWrapper<EmployerSatisfaction> employerSatisfactionQueryWrapper = new QueryWrapper<>();
        if (query.getYear() != null) {
            employerSatisfactionQueryWrapper.eq("year", query.getYear());
        }
        if (query.getCollege() != null && !query.getCollege().equals("")) {
            employerSatisfactionQueryWrapper.eq("college", query.getCollege());
        }
        List<Year> allYear = yearService.findAllYear();
        model.addAttribute("allYear", allYear);
        IPage<EmployerSatisfaction> iPage = empService.page(employerSatisfactionPage, employerSatisfactionQueryWrapper);
        model.addAttribute("current", iPage.getCurrent());//当前页数
        model.addAttribute("pages", iPage.getPages());//总页数
        model.addAttribute("employerSatisfactions", iPage.getRecords());//所有的数据集合
        model.addAttribute("query", query);
        return new ModelAndView("employment");
    }

    @GetMapping("/delete")
    public Result delete(String year) {
        boolean isDelete = empService.delete(year);
        if (isDelete) {
            return new Result(1, "删除成功");
        } else {
            return new Result(-1, "没有该年份数据");
        }
    }
    /**
     * 导入原始数据
     */
    @PostMapping("/import")
    public Result importExcel(@RequestParam("file") MultipartFile multipartFile, String year) throws IOException {
        QueryWrapper<EmployerSatisfaction> queryWrapper = new QueryWrapper<EmployerSatisfaction>();
        if (year != null && !year.equals("")) {
            queryWrapper.eq("year", Integer.parseInt(year));
            queryWrapper.last("limit 2");
            List<EmployerSatisfaction> list = empService.list(queryWrapper);
            if (list != null && list.size() > 0) {
                return new Result(0, "导入数据重复");
            }
        }
        boolean isTrue = empService.NewImportExcel(multipartFile,year);
        if (isTrue){
            return new Result(ResultCodeEnum.SUCCESS,"导入成功");
        }
        else {
            return new Result(ResultCodeEnum.FAIL,"导入失败");
        }
    }
}
