package org.wlgzs.index_evaluation.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
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
public class YearServiceImpl implements YearService {

    @Autowired
    private YearDao yearDao;

    @Override
    public Page<Year> findAllYear(int pageNum, int pageSize) {
        Page<Year> page = new Page<>(pageNum,pageSize);
        Wrapper<Year> yearEntityWrapper = new EntityWrapper<>();
        page.setRecords(yearDao.selectPage(page,yearEntityWrapper));
        return page;
    }

    @Override
    public Year findByName(Integer name) {
        Year year = new Year();
        year.setYearName(name);
        year = yearDao.selectOne(year);
        return year;
    }

    @Override
    public Integer add(Year year) {
        return yearDao.insert(year);
    }

    @Override
    public Integer delete(Integer id) {
        return yearDao.deleteById(id);
    }

    @Override
    public Integer update(Year year) {
        return yearDao.updateById(year);
    }
}
