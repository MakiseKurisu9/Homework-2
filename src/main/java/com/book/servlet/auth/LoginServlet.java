package com.book.servlet.auth;


import com.book.dao.UserMapper;
import com.book.entity.User;
import com.book.service.impl.UserService;
import com.book.utils.MybatisUtil;
import com.book.utils.ThymeleafUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.ibatis.session.SqlSession;
import org.thymeleaf.context.Context;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    UserService service;
    @Override
    public void init() throws ServletException {
        service = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //第二次登录网站
        Cookie[] cookies = req.getCookies();
        if(cookies != null ) {
            //确保稳定性
            String username = null;
            String password = null;
            //遍历cookie，寻找是否有存储的账号信息
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("username")) username = cookie.getValue();
                if (cookie.getName().equals("password")) password = cookie.getValue();
            }
            //找到了账户信息
            if(username != null && password != null) {
                //确定账户信息是否正确
                if(service.auth(username,password,req.getSession())) {
                    //信息正确
                    resp.sendRedirect("index");
                    return;
                }
            }
        }

        //第一次登录网站
        Context context = new Context();
        //处理登录失败
        if(req.getSession().getAttribute("loginFailure") != null) {
            //传给html登陆失败的标记，使用ThymeLeaf修改html，提示登陆失败
            context.setVariable("failure",true);
            //移除标记
            req.getSession().removeAttribute("loginFailure");
        }
        //已经登录后直接跳转index
        if (req.getSession().getAttribute("user") != null) {
            resp.sendRedirect("index");
            return;
        }

        //成功登录网站
        ThymeleafUtil.process("login.html",context,resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String remember = req.getParameter("rememberMe");
        if(service.auth(username,password,req.getSession())) {
            if(remember != null){
                Cookie cookieUsername= new Cookie("username",username);
                cookieUsername.setMaxAge(60 * 60 * 24 *7);
                Cookie cookiePassword= new Cookie("password",password);
                cookiePassword.setMaxAge(60 * 60 * 24 *7);
                resp.addCookie(cookieUsername);
                resp.addCookie(cookiePassword);
            }
            resp.sendRedirect("index");
        }   else {
            //登录失败，在session中添加一个标记，使得其在doGet中有特殊标识
            req.getSession().setAttribute("loginFailure",new Object());
            this.doGet(req,resp);
        }
    }

}
