package org.wlgzs.index_evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.index_evaluation.dao.TeachersStructureMapper;
import org.wlgzs.index_evaluation.pojo.TeachersStructure;
import org.wlgzs.index_evaluation.service.TeachersStructureService;
import org.wlgzs.index_evaluation.util.ExcelUtil;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zsh
 * @company wlgzs
 * @create 2019-01-13 10:21
 * @Describe
 */
@Log4j2
@Service
public class TeachersStructureServiceImpl extends ServiceImpl<TeachersStructureMapper, TeachersStructure> implements TeachersStructureService {

    @Override
    public List<TeachersStructure> importExcelInfo(InputStream in, MultipartFile file) {
        List<List<Object>> listob;
        List<TeachersStructure> teachersStructuresList = new ArrayList<>();
        DecimalFormat df1 = new DecimalFormat("#");
        try {
            listob = ExcelUtil.getBankListByExcel(in,file.getOriginalFilename());
            //遍历listob数据，把数据放到List中
            for (int i = 0; i < listob.size(); i++) {
                List<Object> ob = listob.get(i);
                TeachersStructure t = new TeachersStructure();
                //通过遍历实现把每一列封装成一个model中，再把所有的model用List集合装载
                t.setCollegeName(String.valueOf(ob.get(0)));
                t.setStuNum(Integer.parseInt(String.valueOf(ob.get(1))));
                t.setTeaNum(Double.parseDouble(String.valueOf(ob.get(2))));
                t.setGraNum(Integer.parseInt(String.valueOf(ob.get(3))));
                t.setSenNum(Integer.parseInt(String.valueOf(ob.get(4))));
                t.setQualified(Integer.parseInt(String.valueOf(ob.get(5))));
                teachersStructuresList.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teachersStructuresList;
    }

    @Override
    public Integer add(TeachersStructure teachersStructure) {
        return baseMapper.insert(teachersStructure);
    }

    @Override
    public Integer update(TeachersStructure teachersStructure) {
        return baseMapper.updateById(teachersStructure);
    }

    @Override
    public List<TeachersStructure> findAll() {
        return baseMapper.selectList(null);
    }
}
