package org.wlgzs.index_evaluation.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.wlgzs.index_evaluation.pojo.Year;

/**
 * @author zsh
 * @company wlgzs
 * @create 2019-01-12 15:39
 * @Describe 年份数据访问层
 */

@Mapper
@Repository
public interface YearDao extends BaseMapper<Year> {

}
