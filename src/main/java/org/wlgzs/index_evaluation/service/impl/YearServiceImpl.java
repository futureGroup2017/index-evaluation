package org.wlgzs.index_evaluation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.wlgzs.index_evaluation.dao.YearDao;
import org.wlgzs.index_evaluation.pojo.Year;
import org.wlgzs.index_evaluation.service.YearService;

/**
 * @author zsh
 * @company wlgzs
 * @create 2019-01-12 15:46
 * @Describe
 */
@Service
public class YearServiceImpl extends ServiceImpl<YearDao,Year> implements YearService {

    @Override
    public IPage<Year> findAllYear(int pageNum, int pageSize) {
        Page<Year> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Year> yearEntityWrapper = new QueryWrapper<>();
        return baseMapper.selectPage(page,yearEntityWrapper);
    }

    @Override
    public Year findByName(Integer name) {
        Year year = new Year();
        QueryWrapper<Year> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("yearName",name);
        year = baseMapper.selectOne(queryWrapper);
        return year;
    }

    @Override
    public Integer add(Year year) {
        return baseMapper.insert(year);
    }

    @Override
    public Integer delete(Integer id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public Integer update(Year year) {
        return baseMapper.updateById(year);
    }
}
