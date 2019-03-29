package org.wlgzs.index_evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.wlgzs.index_evaluation.dao.GradeMapper;
import org.wlgzs.index_evaluation.pojo.Grade;
import org.wlgzs.index_evaluation.service.GradeService;

/**
 * @author 武凯焱
 * @date 2019/3/26 21:56
 * @Description:
 */
@Service
@Log4j2
public class GradeServiceImpl extends ServiceImpl<GradeMapper,Grade> implements GradeService {
}
