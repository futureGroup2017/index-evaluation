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
import org.wlgzs.index_evaluation.pojo.Employment;
import org.wlgzs.index_evaluation.pojo.Query;
import org.wlgzs.index_evaluation.pojo.Year;
import org.wlgzs.index_evaluation.service.EmploymentService;
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
 * @create 2019-01-14 10:06
 * @Describe
 */
@RestController
@RequestMapping("/em")
@Log4j2
public class EmploymentController {

    @Autowired
    private EmploymentService employmentService;
    @Autowired
    private YearService yearService;

    @GetMapping("/search")
    public ModelAndView search(Query query,
                               @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                               @RequestParam(name = "pageSize", defaultValue = "16") int pageSize) throws UnsupportedEncodingException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("set");
        List<Year> allYear = yearService.findAllYear();
        modelAndView.addObject("allYear", allYear);
        Page<Employment> practiceQueryWrapper = new Page<>(pageNum, pageSize);
        QueryWrapper<Employment> queryWrapper = new QueryWrapper<>();
        if (query.getYear() != null) {
            queryWrapper.eq("year", query.getYear());
        }
        if (query.getCollege() != "" && query.getCollege() != null) {
            queryWrapper.eq("college", query.getCollege());
        }
        IPage<Employment> iPage = employmentService.page(practiceQueryWrapper, queryWrapper);
        modelAndView.addObject("current", iPage.getCurrent());//当前页数
        modelAndView.addObject("pages", iPage.getPages());//总页数
        modelAndView.addObject("allEmployment", iPage.getRecords());//所有的数据集合
        modelAndView.addObject("query", query);
        return modelAndView;
    }

    @RequestMapping("/import")
    @ResponseBody
    public Result impor(@RequestParam(value = "file", required = false) MultipartFile file,Integer year) throws IOException {
        if (year == null) {
            return new Result(0,"请选择年份");
        }
        //获取上传的文件
        InputStream in = file.getInputStream();
        if (!file.getOriginalFilename().equals("3.就业状态指数样表.xlsx")){
            return new Result(0,"上传文件错误，请确认是<3.就业状态指数样表.xlsx>");
        }
        if (employmentService.findByYear(year).size() == 0){
            return new Result(0,"上传文件错误，请先导入<3.就业状态指数就业起薪样表.xlsx>");
        }
        DecimalFormat df3 = new DecimalFormat("#.0000");
        List<Employment> employments = employmentService.importExcelInfo(in, file,year);
        for (Employment e : employments) {
            //知识能力结构40.75
            //计算公式：
            //((专业知识能力非常强*1+很强*0.8+一般*0.6+不强*0.4+很不强*0.2)*100/(专业知识能力人数)*0.3695+
            //(通用知识能力非常强*1+很强*0.8+一般*0.6+不强*0.4+很不强*0.2)*100/(通用知识能力人数)*0.3715+
            //(求职应聘能力非常强*1+很强*0.8+一般*0.6+不强*0.4+很不强*0.2)*100/(求职应聘能力人数)*0.259)*0.4075
            e.setB11(Double.parseDouble(df3.format(
                    ((e.getMB1111() * 1 + e.getMB1112() * 0.8 + e.getMB1113() * 0.6 + e.getMB1114() * 0.4 + e.getMB1115() * 0.2) * 100 / e.getParNum() * 0.3695 +
                            (e.getMB1121() * 1 + e.getMB1122() * 0.8 + e.getMB1123() * 0.6 + e.getMB1124() * 0.4 + e.getMB1125() * 0.2) * 100 / e.getParNum() * 0.3715 +
                            (e.getMB1131() * 1 + e.getMB1132() * 0.8 + e.getMB1133() * 0.6 + e.getMB1134() * 0.4 + e.getMB1135() * 0.2) * 100 / e.getParNum() * 0.259) * 0.4075
            )));

            //标识性优势31.35
            //计算公式：
            //((社会兼职经历1周*0.2+半月*0.4+1个月*0.6+2个月*0.8+3个月以上*1)*100/(社会兼职经历人数)*0.3615+
            //(“非学历、费荣誉”证书3个以上*1+3个*0.8+2个*0.6+1个*0.4+0个*0.2)*100/(“非学历、费荣誉”证书人数)*0.3145+
            //(社会职务有*1+无*0)*100/(社会职务人数)*0.324)*0.3135
            e.setB12(Double.parseDouble(df3.format(
                    ((e.getMB1211() * 1 + e.getMB1212() * 0.8 + e.getMB1213() * 0.6 + e.getMB1214() * 0.4 + e.getMB1215() * 0.2) * 100 / (e.getMB1211()+e.getMB1212()+e.getMB1213()+e.getMB1214()+e.getMB1215()) * 0.3615 +
                            (e.getMB1221() * 1 + e.getMB1222() * 0.8 + e.getMB1223() * 0.6 + e.getMB1224() * 0.4 + e.getMB1225() * 0.2) * 100 / (e.getMB1221()+e.getMB1222()+e.getMB1223()+e.getMB1224()+e.getMB1225()) * 0.3145 +
                            (e.getMB1231() * 1 + e.getMB1232() * 0) * 100.0 / (e.getMB1231()+e.getMB1232()) * 0.324) * 0.3135
            )));

            //择业精神27.9
            //计算公式：
            //((求职积极程度很积极*1+积极*0.8+一般*0.6+不积极*0.4+很不积极*0.2)*100/(求职积极程度人数)*0.496+
            //(自我效能感很自信*1+自信*0.8+一般*0.6+不自信*0.4+很不自信*0.2)*100/(自我效能感人数)*0.504)*0.279
            e.setB13(Double.parseDouble(df3.format(
                    ((e.getMB1311() * 1 + e.getMB1312() * 0.8 + e.getMB1313() * 0.6 + e.getMB1314() * 0.4 + e.getMB1315() * 0.2) * 100 / (e.getMB1311() + e.getMB1312() + e.getMB1313() + e.getMB1314() + e.getMB1315()) * 0.496 +
                            (e.getMB1321() * 1 + e.getMB1322() * 0.8 + e.getMB1323() * 0.6 + e.getMB1324() * 0.4 + e.getMB1325() * 0.2) * 100 / (e.getMB1321() + e.getMB1322() + e.getMB1323() + e.getMB1324() + e.getMB1325()) * 0.504) * 0.279
            )));


            //就业起薪28.55
            e.setB21(e.getB21());

            //岗位胜任度24.2
            //计算公式：
            //((专业对口状态很对口*1+对口*0.8+一般*0.6+不对口*0.4+很不对口*0.2)*100/(专业对口状态人数)*0.4745+
            //(“能力-岗位”适配度很匹配*1+匹配*0.8+一般*0.6+不匹配*0.4+很不匹配*0.2)*100/(“能力-岗位”适配度人数)*0.5255)*0.242
            e.setB22(Double.parseDouble(df3.format(
                    ((e.getMB2211() * 1 + e.getMB2212() * 0.8 + e.getMB2213() * 0.6 + e.getMB2214() * 0.4 + e.getMB2215() * 0.2) * 100 / (e.getMB2211() + e.getMB2212() + e.getMB2213() + e.getMB2214() + e.getMB2215()) * 0.4745 +
                            (e.getMB2221() * 1 + e.getMB2222() * 0.8 + e.getMB2223() * 0.6 + e.getMB2224() * 0.4 + e.getMB2225() * 0.2) * 100 / (e.getMB2221() + e.getMB2222() + e.getMB2223() + e.getMB2224() + e.getMB2225()) * 0.5255) * 0.242
            )));

            //就业现状满意度28
            //计算公式：
            //((月薪兑付状态正常*1+拖欠*0)*100/(月薪兑付状态人数)*0.274+
            //(“五险一金”执行状态正常*1+拖欠*0)*100/(“五险一金”执行状态人数)*0.2095+
            //(成长发展空间很宽广*1+宽广*0.8+一般*0.6+不宽广0.4+很不宽广*0.2)*100/(成长发展空间人数)*0.2425+
            //(工作满意度很满意*1+满意*0.8+一般0.6+不满意*0.4+很不满意*0.2)*100/(工作满意度人数)*0.274)*0.28
            e.setB23(Double.parseDouble(df3.format(
                    ((e.getMB2311() * 1 + e.getMB2312() * 0) * 100.0 / (e.getMB2311() + e.getMB2312()) * 0.274 +
                            (e.getMB2321() * 1 + e.getMB2322() * 0) * 100.0 / (e.getMB2321() + e.getMB2322()) * 0.2095 +
                            (e.getMB2331() * 1 + e.getMB2332() * 0.8 + e.getMB2333() * 0.6 + e.getMB2334() * 0.4 + e.getMB2335() * 0.2) * 100 / (e.getMB2331() + e.getMB2332() + e.getMB2333() + e.getMB2334() + e.getMB2335()) * 0.2425 +
                            (e.getMB2341() * 1 + e.getMB2342() * 0.8 + e.getMB2343() * 0.6 + e.getMB2344() * 0.4 + e.getMB2345() * 0.2) * 100 / (e.getMB2341() + e.getMB2342() + e.getMB2343() + e.getMB2344() + e.getMB2345()) * 0.274) * 0.28
            )));

            //预期就业年限19.25
            //计算公式：
            //(预期就业年限10年以上*1+8-10年*0.8+3-7年*0.6+2年*0.4+1年*0.2)*100/(预期就业年限人数)*0.1925
            e.setB24(Double.parseDouble(df3.format(
                    (e.getMB241() * 1 + e.getMB242() * 0.8 + e.getMB243() * 0.6 + e.getMB244() * 0.4 + e.getMB245() * 0.2) * 100 / (e.getMB241() + e.getMB242() + e.getMB243() + e.getMB244() + e.getMB245()) * 0.1925
            )));

            //个体就业潜力44.8
            //计算公式：
            //(知识能力结构40.75+标识性优势31.35+择业精神27.9)*0.448
            //
            e.setA1(Double.parseDouble(df3.format(
                    (((e.getMB1111() * 1 + e.getMB1112() * 0.8 + e.getMB1113() * 0.6 + e.getMB1114() * 0.4 + e.getMB1115() * 0.2) * 100 / (e.getMB1111()+e.getMB1112()+e.getMB1113()+e.getMB1114()+e.getMB1115()) * 0.3695 +
                            (e.getMB1121() * 1 + e.getMB1122() * 0.8 + e.getMB1123() * 0.6 + e.getMB1124() * 0.4 + e.getMB1125() * 0.2) * 100 / (e.getMB1121()+e.getMB1122()+e.getMB1123()+e.getMB1124()+e.getMB1125()) * 0.3715 +
                            (e.getMB1131() * 1 + e.getMB1132() * 0.8 + e.getMB1133() * 0.6 + e.getMB1134() * 0.4 + e.getMB1135() * 0.2) * 100 / (e.getMB1131()+e.getMB1132()+e.getMB1133()+e.getMB1134()+e.getMB1135()) * 0.259) * 0.4075 +
                            ((e.getMB1211() * 1 + e.getMB1212() * 0.8 + e.getMB1213() * 0.6 + e.getMB1214() * 0.4 + e.getMB1215() * 0.2) * 100 / (e.getMB1211()+e.getMB1212()+e.getMB1213()+e.getMB1214()+e.getMB1215()) * 0.3615 +
                                    (e.getMB1221() * 1 + e.getMB1222() * 0.8 + e.getMB1223() * 0.6 + e.getMB1224() * 0.4 + e.getMB1225() * 0.2) * 100 / (e.getMB1221()+e.getMB1222()+e.getMB1223()+e.getMB1224()+e.getMB1225()) * 0.3145 +
                                    (e.getMB1231() * 1 + e.getMB1232() * 0) * 100.0 / (e.getMB1231()+e.getMB1232()) * 0.324) * 0.3135 +
                            ((e.getMB1311() * 1 + e.getMB1312() * 0.8 + e.getMB1313() * 0.6 + e.getMB1314() * 0.4 + e.getMB1315() * 0.2) * 100 / (e.getMB1311() + e.getMB1312() + e.getMB1313() + e.getMB1314() + e.getMB1315()) * 0.496 +
                                    (e.getMB1321() * 1 + e.getMB1322() * 0.8 + e.getMB1323() * 0.6 + e.getMB1324() * 0.4 + e.getMB1325() * 0.2) * 100 / (e.getMB1321() + e.getMB1322() + e.getMB1323() + e.getMB1324() + e.getMB1325()) * 0.504) * 0.279
                    ) * 0.448
            )));

            //个体就业表现55.2
            //计算公式：
            //(就业起薪28.55+岗位胜任度24.2+就业现状满意度28)*0.552
            e.setA2(Double.parseDouble(df3.format(
                    (e.getB21()+
                            ((e.getMB2211() * 1 + e.getMB2212() * 0.8 + e.getMB2213() * 0.6 + e.getMB2214() * 0.4 + e.getMB2215() * 0.2) * 100 / (e.getMB2211() + e.getMB2212() + e.getMB2213() + e.getMB2214() + e.getMB2215()) * 0.4745 +
                            (e.getMB2221() * 1 + e.getMB2222() * 0.8 + e.getMB2223() * 0.6 + e.getMB2224() * 0.4 + e.getMB2225() * 0.2) * 100 / (e.getMB2221() + e.getMB2222() + e.getMB2223() + e.getMB2224() + e.getMB2225()) * 0.5255) * 0.242 +
                            ((e.getMB2311() * 1 + e.getMB2312() * 0) * 100.0 / (e.getMB2311() + e.getMB2312()) * 0.274 +
                                    (e.getMB2321() * 1 + e.getMB2322() * 0) * 100.0 / (e.getMB2321() + e.getMB2322()) * 0.2095 +
                                    (e.getMB2331() * 1 + e.getMB2332() * 0.8 + e.getMB2333() * 0.6 + e.getMB2334() * 0.4 + e.getMB2335() * 0.2) * 100 / (e.getMB2331() + e.getMB2332() + e.getMB2333() + e.getMB2334() + e.getMB2335()) * 0.2425 +
                                    (e.getMB2341() * 1 + e.getMB2342() * 0.8 + e.getMB2343() * 0.6 + e.getMB2344() * 0.4 + e.getMB2345() * 0.2) * 100 / (e.getMB2341() + e.getMB2342() + e.getMB2343() + e.getMB2344() + e.getMB2345()) * 0.274) * 0.28 +
                            (e.getMB241() * 1 + e.getMB242() * 0.8 + e.getMB243() * 0.6 + e.getMB244() * 0.4 + e.getMB245() * 0.2) * 100 / (e.getMB241() + e.getMB242() + e.getMB243() + e.getMB244() + e.getMB245()) * 0.1925
                    ) * 0.552
            )));


            //就业状态指数25.3
            //计算公式：
            //(个体就业潜力44.8+个体就业表现55.2)*0.253
            e.setEmploymentStatus(Double.parseDouble(df3.format(
                    ((((e.getMB1111() * 1 + e.getMB1112() * 0.8 + e.getMB1113() * 0.6 + e.getMB1114() * 0.4 + e.getMB1115() * 0.2) * 100 / (e.getMB1111()+e.getMB1112()+e.getMB1113()+e.getMB1114()+e.getMB1115()) * 0.3695 +
                            (e.getMB1121() * 1 + e.getMB1122() * 0.8 + e.getMB1123() * 0.6 + e.getMB1124() * 0.4 + e.getMB1125() * 0.2) * 100 / (e.getMB1121()+e.getMB1122()+e.getMB1123()+e.getMB1124()+e.getMB1125()) * 0.3715 +
                            (e.getMB1131() * 1 + e.getMB1132() * 0.8 + e.getMB1133() * 0.6 + e.getMB1134() * 0.4 + e.getMB1135() * 0.2) * 100 / (e.getMB1131()+e.getMB1132()+e.getMB1133()+e.getMB1134()+e.getMB1135()) * 0.259) * 0.4075 +
                            ((e.getMB1211() * 1 + e.getMB1212() * 0.8 + e.getMB1213() * 0.6 + e.getMB1214() * 0.4 + e.getMB1215() * 0.2) * 100 / (e.getMB1211()+e.getMB1212()+e.getMB1213()+e.getMB1214()+e.getMB1215()) * 0.3615 +
                                    (e.getMB1221() * 1 + e.getMB1222() * 0.8 + e.getMB1223() * 0.6 + e.getMB1224() * 0.4 + e.getMB1225() * 0.2) * 100 / (e.getMB1221()+e.getMB1222()+e.getMB1223()+e.getMB1224()+e.getMB1225()) * 0.3145 +
                                    (e.getMB1231() * 1 + e.getMB1232() * 0) * 100.0 / (e.getMB1231()+e.getMB1232()) * 0.324) * 0.3135 +
                            ((e.getMB1311() * 1 + e.getMB1312() * 0.8 + e.getMB1313() * 0.6 + e.getMB1314() * 0.4 + e.getMB1315() * 0.2) * 100 / (e.getMB1311() + e.getMB1312() + e.getMB1313() + e.getMB1314() + e.getMB1315()) * 0.496 +
                                    (e.getMB1321() * 1 + e.getMB1322() * 0.8 + e.getMB1323() * 0.6 + e.getMB1324() * 0.4 + e.getMB1325() * 0.2) * 100 / (e.getMB1321() + e.getMB1322() + e.getMB1323() + e.getMB1324() + e.getMB1325()) * 0.504) * 0.279
                    ) * 0.448 +
                            (e.getB21()+((e.getMB2211() * 1 + e.getMB2212() * 0.8 + e.getMB2213() * 0.6 + e.getMB2214() * 0.4 + e.getMB2215() * 0.2) * 100 / (e.getMB2211() + e.getMB2212() + e.getMB2213() + e.getMB2214() + e.getMB2215()) * 0.4745 +
                                    (e.getMB2221() * 1 + e.getMB2222() * 0.8 + e.getMB2223() * 0.6 + e.getMB2224() * 0.4 + e.getMB2225() * 0.2) * 100 / (e.getMB2221() + e.getMB2222() + e.getMB2223() + e.getMB2224() + e.getMB2225()) * 0.5255) * 0.242 +
                                    ((e.getMB2311() * 1 + e.getMB2312() * 0) * 100.0 / (e.getMB2311() + e.getMB2312()) * 0.274 +
                                            (e.getMB2321() * 1 + e.getMB2322() * 0) * 100.0 / (e.getMB2321() + e.getMB2322()) * 0.2095 +
                                            (e.getMB2331() * 1 + e.getMB2332() * 0.8 + e.getMB2333() * 0.6 + e.getMB2334() * 0.4 + e.getMB2335() * 0.2) * 100 / (e.getMB2331() + e.getMB2332() + e.getMB2333() + e.getMB2334() + e.getMB2335()) * 0.2425 +
                                            (e.getMB2341() * 1 + e.getMB2342() * 0.8 + e.getMB2343() * 0.6 + e.getMB2344() * 0.4 + e.getMB2345() * 0.2) * 100 / (e.getMB2341() + e.getMB2342() + e.getMB2343() + e.getMB2344() + e.getMB2345()) * 0.274) * 0.28 +
                                    (e.getMB241() * 1 + e.getMB242() * 0.8 + e.getMB243() * 0.6 + e.getMB244() * 0.4 + e.getMB245() * 0.2) * 100 / (e.getMB241() + e.getMB242() + e.getMB243() + e.getMB244() + e.getMB245()) * 0.1925
                            ) * 0.552
                    ) * 0.253
            )));
            employmentService.update(e);
        }
        return new Result(1,"上传文件成功！");
    }

    //导入就业起薪
    @RequestMapping("/import1")
    @ResponseBody
    public Result impor1(@RequestParam(value = "file", required = false) MultipartFile file,Integer year) throws IOException {
        if (year == null) {
            return new Result(0,"请选择年份");
        }
        //获取上传的文件
        InputStream in = file.getInputStream();
        if (!file.getOriginalFilename().equals("3.就业状态指数就业起薪样表.xlsx")){
            return new Result(0,"上传文件错误，请确认是<3.就业状态指数就业起薪样表.xlsx>");
        }
        List<Employment> employments = employmentService.importExcelInfo1(in, file);
        for (Employment e :employments){
            //年份
            e.setYear(year);
            employmentService.add(e);
        }
        return new Result(1,"就业起薪导入成功！");
    }


    @RequestMapping("/export")
    public void export(Integer year, HttpServletResponse response){
        log.info("正在导出数据");
        try {
            employmentService.export(year,response);
        } catch (Exception e) {
            log.info("导出错误");
            e.printStackTrace();
        }
        log.info("导出成功");
    }

    @GetMapping("/delete")
    @ResponseBody
    public Result delete(Integer year){
        if (year == null){
            return new Result(0,"请选择年份后重试！");
        }
        List<Employment> byYear = employmentService.findByYear(year);
        for (Employment t :byYear){
            if (employmentService.delete(t) == 0){
                return new Result(0,"删除出错，请重试！");
            }
        }
        return new Result(1,"删除成功");
    }
}
