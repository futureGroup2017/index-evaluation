package org.wlgzs.index_evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.wlgzs.index_evaluation.dao.StudentQualityMapper;
import org.wlgzs.index_evaluation.pojo.StudentQuality;
import org.wlgzs.index_evaluation.service.StudentQualityService;

/**
 * @author 武凯焱
 * @date 2019/1/15 17:34
 * @Description:
 */
@Service
public class StudentQualityServiceImpl  extends ServiceImpl<StudentQualityMapper,StudentQuality> implements StudentQualityService {
}
