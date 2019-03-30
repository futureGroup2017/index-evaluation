package org.wlgzs.index_evaluation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.index_evaluation.enums.Result;
import org.wlgzs.index_evaluation.pojo.Query;
import org.wlgzs.index_evaluation.pojo.TeachersStructure;
import org.wlgzs.index_evaluation.pojo.Year;
import org.wlgzs.index_evaluation.service.TeachersStructureService;
import org.wlgzs.index_evaluation.service.YearService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.List;

/**
 * @author zsh
 * @company wlgzs
 * @create 2019-01-13 10:41
 * @Describe
 */
@RestController
@RequestMapping("/ts")
@Log4j2
public class TeachersStructureController {

    @Autowired
    private TeachersStructureService teachersStructureService;
    @Autowired
    private YearService yearService;

    @GetMapping("/delete")
    @ResponseBody
    public Result delete(Integer year){
        if (year == null){
            return new Result(0,"请选择年份后重试！");
        }
        List<TeachersStructure> byYear = teachersStructureService.findByYear(year);
        for (TeachersStructure t :byYear){
                if (teachersStructureService.delete(t) == 0){
                    return new Result(0,"删除出错，请重试！");
            }
        }
        return new Result(1,"删除成功");
    }

    @RequestMapping("/import")
    public Result impor(@RequestParam(value = "file", required = false) MultipartFile file,Integer year) throws IOException {
        if (year == null){
            return new Result(0,"请选择年份");
        }
        //获取上传的文件
        InputStream in = file.getInputStream();
        if (!file.getOriginalFilename().equals("2.师资结构指数样表.xlsx")){
            return new Result(0,"上传文件错误，请确认是<2.师资结构指数样表.xlsx>");
        }
        QueryWrapper<TeachersStructure> queryWrappers = new QueryWrapper<>();
        queryWrappers.eq("year", year);
        queryWrappers.last("limit 2");
        List<TeachersStructure> list = teachersStructureService.list(queryWrappers);
        if (list != null && list.size() > 0) {
            return new Result(0, "导入数据重复");
        }
        List<TeachersStructure> teachersStructures = teachersStructureService.importExcelInfo(in, file);
        DecimalFormat df2 = new DecimalFormat("#.0000");
        DecimalFormat df3 = new DecimalFormat("#.0000");
        for (TeachersStructure t:teachersStructures){
            //设置年份
            t.setYear(year);
            //设置生师比合格值: 艺术学院,服装学院,体育学院11，其余18
            if (t.getCollegeName().equals("艺术学院") || t.getCollegeName().equals("服装学院") || t.getCollegeName().equals("体育学院"))
                t.setQualified(11);
            else
                t.setQualified(18);
            //计算生师比：学生总数/教师总数
            t.setB21(Double.parseDouble(df3.format(t.getStuNum()/t.getTeaNum())));
            //计算研究生学位教师占专职教师比例：研究生教师数/教师总数
            t.setB22(Double.parseDouble(df3.format(t.getGraNum()/t.getTeaNum())));
            //计算高级职务教师占专职教师比例：高级职务教师数/教师总数
            t.setB23(Double.parseDouble(df3.format(t.getSenNum()/t.getTeaNum())));
            teachersStructureService.add(t);
        }

        //研究生学位教师占专任教师比例最高值
        //高级职务教师占专任教师比例最高值
        double max0=0,max1=0;
        teachersStructures = teachersStructureService.findAll();
        for (TeachersStructure t:teachersStructures){
            if(t.getB22() > max0)
                max0 = t.getB22();
            if (t.getB23() > max1)
                max1 = t.getB23();
        }

        for (TeachersStructure t:teachersStructures){
            double m1;
            if (t.getB21() <= t.getQualified()){
                m1 = 1;
            }else {
                m1 = 1-(t.getB21()-t.getQualified())*0.2;
                if(m1 < 0){
                    m1 = 0;
                }
            }
            //生师比分数：生师比低于合格值，记1分；高一个点，减0.2分，只知为0
            t.setM1(Double.parseDouble(df3.format(m1)));
            //研究生比分数：研究生教师数/教师总数/最高值
            t.setM2(Double.parseDouble(df3.format(t.getGraNum()/t.getTeaNum()/max0)));
            //高级职务比分数：高级职务教师数/教师总数/最高值
            t.setM3(Double.parseDouble(df3.format(t.getSenNum()/t.getTeaNum()/max1)));
            //生师比33.7：生师比分数*33.7
            t.setW1(Double.parseDouble(df2.format(t.getM1()*33.7)));
            //高学历教师占比32.9：研究生教师数/教师总数/最高值*32.9
            t.setW2(Double.parseDouble(df2.format(t.getGraNum()/t.getTeaNum()/max0*32.9)));
            //高职称教师占比33.9：高级职务教师数/教师总数/最高值*33.9
            t.setW3(Double.parseDouble(df2.format(t.getSenNum()/t.getTeaNum()/max1*33.9)));
            //师资结构指数11.07%：(生师比分数*33.7+研究生教师数/教师总数/最高值*32.9+高级职务教师数/教师总数/最高值*33.9)*0.1107
            t.setA2(Double.parseDouble(df2.format((t.getM1()*33.7+t.getGraNum()/t.getTeaNum()/max0*32.9+t.getSenNum()/t.getTeaNum()/max1*33.9)*0.1107)));
            teachersStructureService.update(t);
        }
        return new Result(1,"上传文件成功！");
    }

    @RequestMapping("/export")
    public void export(Integer year, HttpServletResponse response){
        log.info("正在导出数据");
        try {
            teachersStructureService.export(year,response);
        } catch (Exception e) {
            log.info("导出错误");
            e.printStackTrace();
        }
        log.info("导出成功");
    }

    @GetMapping("/search")
    public ModelAndView search(Query query,
                               @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                               @RequestParam(name = "pageSize", defaultValue = "16") int pageSize) throws UnsupportedEncodingException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("result");
        List<Year> allYear = yearService.findAllYear();
        modelAndView.addObject("allYear",allYear);
        Page<TeachersStructure> practiceQueryWrapper = new Page<>(pageNum,pageSize);
        QueryWrapper<TeachersStructure> queryWrapper = new QueryWrapper<>();
        if (query.getYear() != null){
            queryWrapper.eq("year",query.getYear());
        }
        if (query.getCollege() != "" && query.getCollege() != null){
            queryWrapper.eq("college_name",query.getCollege());
        }
        IPage<TeachersStructure> iPage = teachersStructureService.page(practiceQueryWrapper,queryWrapper);
        modelAndView.addObject("current",iPage.getCurrent());//当前页数
        modelAndView.addObject("pages",iPage.getPages());//总页数
        modelAndView.addObject("allTeachersStructure",iPage.getRecords());//所有的数据集合
        modelAndView.addObject("query",query);
        return modelAndView;
    }

}

