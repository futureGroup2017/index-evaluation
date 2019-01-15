package org.wlgzs.index_evaluation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.wlgzs.index_evaluation.dao.EmploymentPracticeMapper;
import org.wlgzs.index_evaluation.enums.Result;
import org.wlgzs.index_evaluation.enums.ResultCodeEnum;
import org.wlgzs.index_evaluation.pojo.EmploymentPractice;
import org.wlgzs.index_evaluation.service.EmploymentPracticeService;
import org.wlgzs.index_evaluation.util.ExcelUtilTwo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author AlgerFan
 * @date Created in 2019/1/14 15
 * @Description 就业创业实践指数
 */
@Service
@Log4j2
public class EmploymentPracticeServiceImpl extends ServiceImpl<EmploymentPracticeMapper, EmploymentPractice> implements EmploymentPracticeService {

    @Resource
    private EmploymentPracticeMapper employmentPracticeMapper;

    @Override
    public Result importData(int year, HttpServletRequest request) {
        //获取上传的文件
        MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
        MultipartFile file = multipart.getFile("file");
        if(file==null || file.getOriginalFilename()==null || year==0){
            Result result = new Result(ResultCodeEnum.UNSAVE);
            result.setMsg("上传失败  ");
            return result;
        }
        List<List<Object>> listob;
        List<EmploymentPractice> employmentPractices = new ArrayList<>();
        try {
            InputStream in = file.getInputStream();
            DecimalFormat decimalFormat = new DecimalFormat(".000");//构造方法的字符格式这里如果小数不足2位,会以0补足.
            listob = ExcelUtilTwo.getBankListByExcel(in,file.getOriginalFilename());
            //参赛总人数
            double totalOne = 0;
            double totalTwo = 0;
            //最高获奖质量积分
            double quality1 = 0;
            double quality2 = 0;
            //项目数量最高值
            double number = 0;
            //项目质量最高值
            double quality = 0;
            for (List<Object> objects1 : listob) {
                if(objects1.get(1)==null || objects1.get(1).equals("")) objects1.set(1,0);
                if(objects1.get(2)==null || objects1.get(2).equals("")) objects1.set(2,0);
                if(objects1.get(3)==null || objects1.get(3).equals("")) objects1.set(3,0);
                if(objects1.get(5)==null || objects1.get(5).equals("")) objects1.set(5,0);
                if(objects1.get(6)==null || objects1.get(6).equals("")) objects1.set(6,0);
                if(objects1.get(8)==null || objects1.get(8).equals("")) objects1.set(8,0);
                if(objects1.get(10)==null || objects1.get(10).equals("")) objects1.set(10,0);
                totalOne += Double.parseDouble(String.valueOf(objects1.get(2)));
                totalTwo += Double.parseDouble(String.valueOf(objects1.get(3)));
                double v = Double.parseDouble(String.valueOf(objects1.get(5)));
                if (v > quality1) quality1 = v;
                double v1 = Double.parseDouble(String.valueOf(objects1.get(6)));
                if (v1 > quality2) quality2 = v1;
                double v2 = Double.parseDouble(String.valueOf(objects1.get(8)));
                if (v2 > number) number = v2;
                double v3 = Double.parseDouble(String.valueOf(objects1.get(10)));
                if (v3 > quality) quality = v3;
            }
            //遍历listob数据，把数据放到List中
            for (List<Object> objects : listob) {
                EmploymentPractice employmentPractice = new EmploymentPractice();
                //通过遍历实现把每一列封装成一个model中，再把所有的model用List集合装载
                employmentPractice.setCollege(String.valueOf(objects.get(0)));
                //"参赛人数比-1(职业生涯规划大赛"
                employmentPractice.setM11(Double.parseDouble(String.valueOf(objects.get(1))));
                //"参赛人数比-1(创业大赛）"
                employmentPractice.setM12(Double.parseDouble(String.valueOf(objects.get(2))));
                //"参赛人数比2-省创业大赛总人数34人"
                employmentPractice.setM13(Double.parseDouble(String.valueOf(objects.get(3))));
                //处理数据——M1: 参赛人数比47.5
                employmentPractice.setPeopleNumber(new BigDecimal(((employmentPractice.getM11() +
                        employmentPractice.getM12()/totalOne + employmentPractice.getM13()/totalTwo)*0.475)).setScale(3, RoundingMode.UP).doubleValue());
                //"获奖质量积分-1(生涯规划大赛"
                employmentPractice.setM21(Double.parseDouble(String.valueOf(objects.get(5))));
                //"获奖质量积分-1(创业大赛）"
                employmentPractice.setM22(Double.parseDouble(String.valueOf(objects.get(6))));
                //处理数据——M2: 获奖质量比52.5
                employmentPractice.setQuality(new BigDecimal((employmentPractice.getM21()/quality1 +
                        employmentPractice.getM22()/quality2)*0.525).setScale(3, RoundingMode.UP).doubleValue());
                /*
                处理数据——参赛状态39
                 */
                employmentPractice.setParticipationStatus(new BigDecimal(((employmentPractice.getM11() +
                        employmentPractice.getM12()/totalOne + employmentPractice.getM13()/totalTwo)*0.475 +
                        (employmentPractice.getM21()/quality1 + employmentPractice.getM22()/quality2)*0.525)*39).setScale(3, RoundingMode.UP).doubleValue());
                //项目数量
                employmentPractice.setM31(Double.parseDouble(String.valueOf(objects.get(8))));
                //处理数据——M3: 项目数量比47
                employmentPractice.setProjectNumber(new BigDecimal((employmentPractice.getM31()/number)*0.47).setScale(3, RoundingMode.UP).doubleValue());
                //项目质量
                employmentPractice.setM41(Double.parseDouble(String.valueOf(Double.parseDouble(String.valueOf(objects.get(10))))));
                //处理数据——M4: 项目质量比53
                employmentPractice.setProjectQuality(new BigDecimal(((employmentPractice.getM41()/quality)*0.53)).setScale(3, RoundingMode.UP).doubleValue());
                /*
                处理数据——创业项目28.5
                 */
                employmentPractice.setVentureProject(new BigDecimal((((employmentPractice.getM31()/number)*0.47 +
                        (employmentPractice.getM41()/quality)*0.53)*28.5)).setScale(3, RoundingMode.UP).doubleValue());
                /*
                处理数据——就业创业实践指数
                 */
                employmentPractice.setPractice(new BigDecimal((((employmentPractice.getM11() +
                        employmentPractice.getM12()/totalOne + employmentPractice.getM13()/totalTwo)*0.475 +
                        (employmentPractice.getM21()/quality1 + employmentPractice.getM22()/quality2)*0.525)*39 +
                        ((employmentPractice.getM31()/number)*0.47 + (employmentPractice.getM41()/quality)*0.53)*28.5)*0.1535).setScale(3, RoundingMode.UP).doubleValue());
                employmentPractice.setYear(year);
                employmentPractices.add(employmentPractice);
                employmentPracticeMapper.insert(employmentPractice);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("就业创业实践数据："+employmentPractices);
        return new Result(ResultCodeEnum.SAVE,employmentPractices);
    }

    @Override
    public Result deleteYear(int year) {
        if(year==0) return new Result(ResultCodeEnum.UNDELETE);
        QueryWrapper<EmploymentPractice> practiceQueryWrapper = new QueryWrapper<>();
        practiceQueryWrapper.eq("year",year);
        List<EmploymentPractice> employmentPractices = employmentPracticeMapper.selectList(practiceQueryWrapper);
        if(employmentPractices==null) return new Result(ResultCodeEnum.UNDELETE);
        employmentPracticeMapper.delete(practiceQueryWrapper);
        return new Result(ResultCodeEnum.DELETE,employmentPractices);
    }
}
