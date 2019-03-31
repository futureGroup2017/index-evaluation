package org.wlgzs.index_evaluation.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.index_evaluation.pojo.User;
import org.wlgzs.index_evaluation.pojo.Year;
import org.wlgzs.index_evaluation.service.UserService;
import org.wlgzs.index_evaluation.service.YearService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;

/**
 * @author zsh
 * @company wlgzs
 * @create 2019-01-14 15:09
 * @Describe
 */
@RestController
@Log4j2
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private YearService yearService;

    @GetMapping("/")
    private ModelAndView toLogin(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

   @GetMapping("/toindex")
    private ModelAndView toindex(){
        ModelAndView modelAndView = new ModelAndView();
        List<Year> allYear = yearService.findAllYear();
        modelAndView.addObject("allYear",allYear);
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/doc")
    public String getPDF(HttpServletResponse response) throws IOException {
        String filename = "就业工作考核数据分析系统使用说明书.pdf";
        String path = "./template/" + filename;
        File file = ResourceUtils.getFile(path);
        response.reset();
        response.setContentType("application/pdf");
        FileInputStream fileInputStream = null;
        OutputStream outputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            outputStream = response.getOutputStream();
            outputStream.write(IOUtils.toByteArray(fileInputStream));
            response.setHeader("Content-Disposition",
                    "inline; filename= file");
            outputStream.flush();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(fileInputStream);
            IOUtils.closeQuietly(outputStream);
        }
        return null;
    }

    @PostMapping("/login")
    public ModelAndView login(User user, HttpSession session){
        log.info(user);
        ModelAndView modelAndView = new ModelAndView();
        User u = userService.findByUserName(user.getUserName());
        if (u == null){
            log.info("用户不存在");
            modelAndView.setViewName("login");
            modelAndView.addObject("msg","用户不存在");
        }else {
            if (u.getPassword().equals(user.getPassword())){
                log.info("登陆成功");
                modelAndView.setViewName("index");
                session.setAttribute("user",u);
                List<Year> allYear = yearService.findAllYear();
                modelAndView.addObject("allYear",allYear);
                return modelAndView;
            }else {
                log.info("用户名或密码错误");
                modelAndView.setViewName("login");
                modelAndView.addObject("msg","用户名或密码错误");
            }
        }
        return modelAndView;
    }

    @GetMapping("out")
    public ModelAndView out(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        session.removeAttribute("user");
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @PostMapping("/update")
    public ModelAndView update(User user,String old){
        ModelAndView modelAndView = new ModelAndView();
        if (old.equals(userService.findByUserName(user.getUserName()).getPassword())){
            modelAndView.addObject("msg","密码修改成功");
            userService.update(user);
        }else {
            modelAndView.addObject("msg","原密码错误");
        }
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
