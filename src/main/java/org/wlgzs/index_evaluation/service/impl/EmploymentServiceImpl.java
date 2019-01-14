package org.wlgzs.index_evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.index_evaluation.dao.EmploymentMapper;
import org.wlgzs.index_evaluation.pojo.Employment;
import org.wlgzs.index_evaluation.service.EmploymentService;
import org.wlgzs.index_evaluation.util.ExcelUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zsh
 * @company wlgzs
 * @create 2019-01-14 10:02
 * @Describe
 */
@Service
@Log4j2
public class EmploymentServiceImpl extends ServiceImpl<EmploymentMapper, Employment> implements EmploymentService {

    @Override
    public List<Employment> importExcelInfo(InputStream in, MultipartFile file) {
        List<List<Object>> listob;
        List<Employment> teachersStructuresList = new ArrayList<>();
        try {
            listob = ExcelUtil.getBankListByExcel(in,file.getOriginalFilename());
            //遍历listob数据，把数据放到List中
            for (int i = 1; i < listob.size(); i++) {
                List<Object> ob = listob.get(i);
                for (Object object:ob){
                    log.info(object);
                }
                /*TeachersStructure t = new TeachersStructure();
                //通过遍历实现把每一列封装成一个model中，再把所有的model用List集合装载
                t.setCollegeName(String.valueOf(ob.get(0)));
                t.setStuNum(Integer.parseInt(String.valueOf(ob.get(1))));
                t.setTeaNum(Double.parseDouble(String.valueOf(ob.get(2))));
                t.setGraNum(Integer.parseInt(String.valueOf(ob.get(3))));
                t.setSenNum(Integer.parseInt(String.valueOf(ob.get(4))));
                teachersStructuresList.add(t);*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teachersStructuresList;
    }
}
