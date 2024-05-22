package com.book.servlet.manage;

import com.book.service.BorrowService;
import com.book.utils.ThymeleafUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.Context;

import java.io.IOException;

@WebServlet("/addBorrow")
public class addBorrow extends HttpServlet {
    BorrowService service;

    @Override
    public void init() throws ServletException {
        service = new com.book.service.impl.BorrowService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context context = new Context();
        context.setVariable("BookList",service.getActiveBookList());
        context.setVariable("StudentList",service.getStudentList());
        ThymeleafUtil.process("add-borrow.html",context,resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //得到html中的value即id
        int sid = Integer.parseInt(req.getParameter("student"));
        int bid = Integer.parseInt(req.getParameter("book"));
        service.addBorrow(sid,bid);
        resp.sendRedirect("index");
    }
}
