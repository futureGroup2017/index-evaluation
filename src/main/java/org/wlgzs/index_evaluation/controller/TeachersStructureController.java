package org.wlgzs.index_evaluation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.index_evaluation.pojo.TeachersStructure;
import org.wlgzs.index_evaluation.pojo.Year;
import org.wlgzs.index_evaluation.service.TeachersStructureService;
import org.wlgzs.index_evaluation.service.YearService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
    public ModelAndView delete(Integer year,
                               @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                               @RequestParam(name = "pageSize", defaultValue = "16") int pageSize){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("result");
        List<Year> allYear = yearService.findAllYear();
        modelAndView.addObject("allYear",allYear);
        List<TeachersStructure> byYear = teachersStructureService.findByYear(year);
        List<TeachersStructure> allTeachersStructure;
        if (year == null){
            modelAndView.addObject("msg","请选择年份后重试！");
            Page<TeachersStructure> practiceQueryWrapper = new Page<>(pageNum,pageSize);
            QueryWrapper<TeachersStructure> queryWrapper = new QueryWrapper<>();
            IPage<TeachersStructure> iPage = teachersStructureService.page(practiceQueryWrapper,queryWrapper);
            modelAndView.addObject("current",iPage.getCurrent());//当前页数
            modelAndView.addObject("pages",iPage.getPages());//总页数
            modelAndView.addObject("allTeachersStructure",iPage.getRecords());//所有的数据集合
            return modelAndView;
        }
        for (TeachersStructure t :byYear){
            if (teachersStructureService.delete(t) == 0){
                modelAndView.addObject("msg","删除出错，请重试！");
                Page<TeachersStructure> practiceQueryWrapper = new Page<>(pageNum,pageSize);
                QueryWrapper<TeachersStructure> queryWrapper = new QueryWrapper<>();
                IPage<TeachersStructure> iPage = teachersStructureService.page(practiceQueryWrapper,queryWrapper);
                modelAndView.addObject("current",iPage.getCurrent());//当前页数
                modelAndView.addObject("pages",iPage.getPages());//总页数
                modelAndView.addObject("allTeachersStructure",iPage.getRecords());//所有的数据集合
                return modelAndView;
            }
        }
        modelAndView.addObject("msg","删除成功");
        Page<TeachersStructure> practiceQueryWrapper = new Page<>(pageNum,pageSize);
        QueryWrapper<TeachersStructure> queryWrapper = new QueryWrapper<>();
        IPage<TeachersStructure> iPage = teachersStructureService.page(practiceQueryWrapper,queryWrapper);
        modelAndView.addObject("current",iPage.getCurrent());//当前页数
        modelAndView.addObject("pages",iPage.getPages());//总页数
        modelAndView.addObject("allTeachersStructure",iPage.getRecords());//所有的数据集合
        return modelAndView;
    }

    @GetMapping("/to")
    public ModelAndView to(@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                           @RequestParam(name = "pageSize", defaultValue = "16") int pageSize){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("result");
        List<Year> allYear = yearService.findAllYear();
        modelAndView.addObject("allYear",allYear);
        Page<TeachersStructure> practiceQueryWrapper = new Page<>(pageNum,pageSize);
        QueryWrapper<TeachersStructure> queryWrapper = new QueryWrapper<>();
        IPage<TeachersStructure> iPage = teachersStructureService.page(practiceQueryWrapper,queryWrapper);
        modelAndView.addObject("current",iPage.getCurrent());//当前页数
        modelAndView.addObject("pages",iPage.getPages());//总页数
        modelAndView.addObject("allTeachersStructure",iPage.getRecords());//所有的数据集合
        return modelAndView;
    }

    @RequestMapping("/import")
    public ModelAndView impor(HttpServletRequest request, Integer year,
                              @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                              @RequestParam(name = "pageSize", defaultValue = "16") int pageSize) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        List<Year> allYear = yearService.findAllYear();
        modelAndView.addObject("allYear",allYear);
        List<TeachersStructure> allTeachersStructure;
        modelAndView.setViewName("result");
        //获取上传的文件
        MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
        MultipartFile file = multipart.getFile("upfile");
        log.info(file.getOriginalFilename().equals("师资结构指数样表.xlsx"));
        InputStream in = file.getInputStream();
        if (!file.getOriginalFilename().equals("师资结构指数样表.xlsx")){
            modelAndView.addObject("msg","上传文件错误，请确认是师资结构指数样表.xlsx！");
            Page<TeachersStructure> practiceQueryWrapper = new Page<>(pageNum,pageSize);
            QueryWrapper<TeachersStructure> queryWrapper = new QueryWrapper<>();
            IPage<TeachersStructure> iPage = teachersStructureService.page(practiceQueryWrapper,queryWrapper);
            modelAndView.addObject("current",iPage.getCurrent());//当前页数
            modelAndView.addObject("pages",iPage.getPages());//总页数
            modelAndView.addObject("allTeachersStructure",iPage.getRecords());//所有的数据集合
            return modelAndView;
        }
        List<TeachersStructure> teachersStructures = teachersStructureService.importExcelInfo(in, file);
        DecimalFormat df2 = new DecimalFormat("#.00");
        DecimalFormat df3 = new DecimalFormat("#.000");
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
        modelAndView.addObject("msg","上传文件成功！");
        Page<TeachersStructure> practiceQueryWrapper = new Page<>(pageNum,pageSize);
        QueryWrapper<TeachersStructure> queryWrapper = new QueryWrapper<>();
        IPage<TeachersStructure> iPage = teachersStructureService.page(practiceQueryWrapper,queryWrapper);
        System.out.println(iPage);
        modelAndView.addObject("current",iPage.getCurrent());//当前页数
        modelAndView.addObject("pages",iPage.getPages());//总页数
        modelAndView.addObject("allTeachersStructure",iPage.getRecords());//所有的数据集合
        return modelAndView;
    }
}
