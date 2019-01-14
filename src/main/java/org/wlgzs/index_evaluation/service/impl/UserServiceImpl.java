package org.wlgzs.index_evaluation.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.wlgzs.index_evaluation.dao.UserDao;
import org.wlgzs.index_evaluation.pojo.User;
import org.wlgzs.index_evaluation.service.UserService;

/**
 * @author zsh
 * @company wlgzs
 * @create 2019-01-14 15:01
 * @Describe
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Override
    public User findByUserName(String userName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name",userName);
        return baseMapper.selectOne(queryWrapper);
    }
}
