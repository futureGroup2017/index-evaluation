package org.wlgzs.index_evaluation.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.wlgzs.index_evaluation.pojo.TeachersStructure;
import org.wlgzs.index_evaluation.service.TeachersStructureService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
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

    @RequestMapping("/import")
    public void impor(HttpServletRequest request, Model model,Integer year) throws IOException {
        //获取上传的文件
        MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
        MultipartFile file = multipart.getFile("upfile");
        InputStream in = file.getInputStream();
        List<TeachersStructure> teachersStructures = teachersStructureService.importExcelInfo(in, file);
        DecimalFormat df2 = new DecimalFormat("#.00");
        DecimalFormat df3 = new DecimalFormat("#.000");
        for (TeachersStructure t:teachersStructures){
            //设置年份
            t.setYear(year);
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



    }
}
