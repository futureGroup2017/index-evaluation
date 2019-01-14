package org.wlgzs.index_evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import org.wlgzs.index_evaluation.pojo.User;

/**
 * @author zsh
 * @company wlgzs
 * @create 2019-01-14 14:59
 * @Describe
 */
public interface UserService extends IService<User> {

    //通过用户名查找用户
    @Transactional
    User findByUserName(String userName);
}
