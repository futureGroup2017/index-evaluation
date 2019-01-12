package org.wlgzs.index_evaluation.service;

import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.transaction.annotation.Transactional;
import org.wlgzs.index_evaluation.pojo.Year;
import org.wlgzs.index_evaluation.util.result.Result;

/**
 * @author zsh
 * @company wlgzs
 * @create 2019-01-12 15:45
 * @Describe
 */
public interface YearService {

    //查询所有年份
    @Transactional
    Page<Year> findAllYear(int pageNum, int pageSize);

    //通过年份名称查询
    @Transactional
    Year findByName(Integer name);

    //添加年份
    @Transactional
    Integer add(Year year);

    //删除年份
    @Transactional
    Integer delete(Integer id);

    //修改年份
    @Transactional
    Integer update(Year year);

}
