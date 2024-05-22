package com.book.servlet.manage;

import com.book.service.BorrowService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/returnBook")
public class ReturnServlet extends HttpServlet{

    BorrowService service;

    @Override
    public void init() throws ServletException {
        service = new com.book.service.impl.BorrowService();
    }

    //链接会发起doGet请求
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        service.returnBorrow(id);
        resp.sendRedirect("index");
    }
}
