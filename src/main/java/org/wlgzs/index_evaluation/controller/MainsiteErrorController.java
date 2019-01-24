package org.wlgzs.index_evaluation.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zsh
 * @company wlgzs
 * @create 2019-01-21 9:23
 * @Describe 404，500
 */
@Controller
public class MainsiteErrorController implements ErrorController {

    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        //获取statusCode:404,500
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if(statusCode == 404){
            modelAndView.setViewName("404");
            return modelAndView;
        }else {
            modelAndView.setViewName("500");
            return modelAndView;
        }
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
