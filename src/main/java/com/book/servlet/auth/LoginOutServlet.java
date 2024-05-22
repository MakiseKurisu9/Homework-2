package com.book.servlet.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/logout")
public class LoginOutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("user");
        //清理cookie
        Cookie cookieUsername= new Cookie("username","username");
        cookieUsername.setMaxAge(0);
        Cookie cookiePassword= new Cookie("password","password");
        cookiePassword.setMaxAge(0);
        resp.addCookie(cookieUsername);
        resp.addCookie(cookiePassword);

        resp.sendRedirect("login");
    }
}
