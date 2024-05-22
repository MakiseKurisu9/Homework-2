package com.book.Filter;


import com.book.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebFilter("/*")
public class MainFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String url = req.getRequestURL().toString();
        //非请求静态资源，非请求登录页面
        if(!url.contains("static/") && !url.endsWith("login")){
            //如果未登陆过则重定向到login
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");
            if(user == null){
                res.sendRedirect("login");
                return;
            }
        }
        chain.doFilter(req,res);
    }
}
