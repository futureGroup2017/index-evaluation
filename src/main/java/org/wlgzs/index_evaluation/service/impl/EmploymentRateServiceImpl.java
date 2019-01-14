package org.wlgzs.index_evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.wlgzs.index_evaluation.dao.EmploymentRateMapper;
import org.wlgzs.index_evaluation.enums.Result;
import org.wlgzs.index_evaluation.enums.ResultCodeEnum;
import org.wlgzs.index_evaluation.pojo.EmploymentRate;
import org.wlgzs.index_evaluation.service.EmploymentRateService;
import org.wlgzs.index_evaluation.util.ExcelUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author AlgerFan
 * @date Created in 2019/1/13 17
 * @Description
 */
@Service
@Log4j2
public class EmploymentRateServiceImpl extends ServiceImpl<EmploymentRateMapper, EmploymentRate> implements EmploymentRateService {
    @Resource
    private EmploymentRateMapper employmentRateMapper;

    @Override
    public Result importData(int year, HttpServletRequest request) {
        //获取上传的文件
        MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
        MultipartFile file = multipart.getFile("file");
        if(file==null || file.getOriginalFilename()==null || year==0){
            Result result = new Result(ResultCodeEnum.UNSAVE);
            result.setMsg("上传失败 ");
            return result;
        }
        List<List<Object>> listob;
        List<EmploymentRate> employmentRateList = new ArrayList<>();
        try {
            InputStream in = file.getInputStream();
            DecimalFormat decimalFormat = new DecimalFormat(".000");//构造方法的字符格式这里如果小数不足2位,会以0补足.
            listob = ExcelUtil.getBankListByExcel(in,file.getOriginalFilename());
            //遍历listob数据，把数据放到List中
            for (int i = 1; i < listob.size(); i++) {
                EmploymentRate employmentRate = new EmploymentRate();
                //通过遍历实现把每一列封装成一个model中，再把所有的model用List集合装载
                //System.out.println(listob.get(i));
                employmentRate.setCollege(String.valueOf(listob.get(i).get(0)));
                employmentRate.setFirstEmploymentRate(String.valueOf(listob.get(i).get(1)));
                employmentRate.setLastEmploymentRate(String.valueOf(listob.get(i).get(2)));
                //初次就业率指数=初次就业率*100*0.2495
                float firstIndex = (float) ((Float.parseFloat(employmentRate.getFirstEmploymentRate()))*100*0.2495);
                employmentRate.setFirstIndex(decimalFormat.format(firstIndex));
                //年终就业率指数=年终就业率*100*0.586
                float lastIndex = (float) (Float.parseFloat(employmentRate.getLastEmploymentRate())*100*0.586);
                employmentRate.setLastIndex(decimalFormat.format(lastIndex));
                //最终就业率指数=（初次就业率指数+年终就业率指数）*0.2495
                float index = (float) ((Float.parseFloat(employmentRate.getFirstIndex()) + Float.parseFloat(employmentRate.getLastIndex())) * 0.2495);
                employmentRate.setEmploymentRateIndex(decimalFormat.format(index));
                employmentRate.setYear(year);
                employmentRateList.add(employmentRate);
                employmentRateMapper.insert(employmentRate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("就业率数据："+employmentRateList);
        return new Result(ResultCodeEnum.SAVE,employmentRateList);
    }
}
