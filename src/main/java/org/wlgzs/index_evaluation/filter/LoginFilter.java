/*
package org.wlgzs.index_evaluation.filter;

import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

*/
/**
 * @author zsh
 * @company wlgzs
 * @create 2019-01-20 8:37
 * @Describe
 *//*

@Log4j2
public class LoginFilter implements Filter {

    String[] includeUrls = new String[]{"/login","/css","/js","/images","/out",".ico"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("过滤器初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        String uri = request.getRequestURI();
        String returnUrl = "/";
        //是否需要过滤
        if (uri.equals("/") || !isNeedFilter(uri)) { //不需要过滤直接传给下一个过滤器
            filterChain.doFilter(servletRequest, servletResponse);
        } else { //需要过滤器
            // session中包含user对象,则是登录状态
            if (session != null && session.getAttribute("user") != null) {
                filterChain.doFilter(request, response);
            } else {
                servletRequest.setCharacterEncoding("UTF-8");
                // 转码
                response.setContentType("text/html; charset=UTF-8");
                response.getWriter().println("<script language=\"javascript\">" + "alert(\"没有访问权限，请重新登录！\");"
                        + "if(window.opener==null){window.top.location.href=\"" + returnUrl
                        + "\";}else{window.opener.top.location.href=\"" + returnUrl + "\";window.close();}</script>");
                response.getWriter().close();
                return;
            }
        }
    }

    */
/**
 * @Description: 是否需要过滤
 *//*

    public boolean isNeedFilter(String uri) {
        for (String includeUrl : includeUrls) {
            if(uri.contains(includeUrl)) {
                return false;
            }
        }
        return true;

    }
    @Override
    public void destroy() {
        log.info("过滤器销毁");
    }
}
*/
