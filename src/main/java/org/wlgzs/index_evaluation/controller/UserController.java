package org.wlgzs.index_evaluation.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wlgzs.index_evaluation.pojo.User;
import org.wlgzs.index_evaluation.service.UserService;

/**
 * @author zsh
 * @company wlgzs
 * @create 2019-01-14 15:09
 * @Describe
 */
@RestController
@RequestMapping("/user")
@Log4j2
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public void login(User user){
        User u = userService.findByUserName(user.getUserName());
        if (u == null){
            log.info("用户不存在");
        }else {
            if (u.getPassword().equals(user.getPassword())){
                log.info("登陆成功");
            }else {
                log.info("用户名或密码错误");
            }
        }
    }
}
