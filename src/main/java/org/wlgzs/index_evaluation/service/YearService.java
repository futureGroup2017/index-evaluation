package org.wlgzs.index_evaluation.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import org.wlgzs.index_evaluation.pojo.Year;

import java.util.List;

/**
 * @author zsh
 * @company wlgzs
 * @create 2019-01-12 15:45
 * @Describe
 */
public interface YearService extends IService<Year> {

    //查询所有年份
    @Transactional
    List<Year> findAllYear();

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
