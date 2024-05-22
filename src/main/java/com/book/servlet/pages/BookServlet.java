package com.book.servlet.pages;

import com.book.entity.User;
import com.book.service.BorrowService;
import com.book.utils.ThymeleafUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.Context;

import java.io.IOException;

@WebServlet("/books")
public class BookServlet extends HttpServlet {

    BorrowService service;

    @Override
    public void init() throws ServletException {
        service = new com.book.service.impl.BorrowService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context context = new Context();
        User user = (User)req.getSession().getAttribute("user");
        context.setVariable("nickname",user.getNickname());
        context.setVariable("bookList",service.getAllBook());
        ThymeleafUtil.process("books.html",context,resp.getWriter());
    }
}
