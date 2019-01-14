package org.wlgzs.index_evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.wlgzs.index_evaluation.dao.CollegeMapper;
import org.wlgzs.index_evaluation.enums.Result;
import org.wlgzs.index_evaluation.enums.ResultCodeEnum;
import org.wlgzs.index_evaluation.pojo.College;
import org.wlgzs.index_evaluation.service.CollegeService;
import org.wlgzs.index_evaluation.util.ExcelUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author algerfan
 * @since 2019-01-13
 */
@Service
public class CollegeServiceImpl extends ServiceImpl<CollegeMapper, College> implements CollegeService {

    @Resource
    private CollegeMapper collegeMapper;

    @Override
    public Result saveCollege(HttpServletRequest request) {
        //获取上传的文件
        MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
        MultipartFile file = multipart.getFile("file");
        if(file!=null && file.getOriginalFilename()==null){
            Result result = new Result(ResultCodeEnum.UNSAVE);
            result.setMsg("上传失败");
            return result;
        }
        List<College> colleges = new ArrayList<>();
        List<List<Object>> listob;
        try {
            assert file != null;
            InputStream in = file.getInputStream();
            listob = ExcelUtil.getBankListByExcel(in,file.getOriginalFilename());
            //遍历listob数据，把数据放到List中
            for (List<Object> ob : listob) {
                College college = new College();
                //通过遍历实现把每一列封装成一个model中，再把所有的model用List集合装载
                college.setCollegeName(String.valueOf(ob.get(0)));
                collegeMapper.insert(college);
                colleges.add(college);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(ResultCodeEnum.SAVE,colleges);
    }
}
