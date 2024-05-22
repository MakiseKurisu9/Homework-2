package com.book.servlet.manage;

import com.book.entity.Book;
import com.book.service.BorrowService;
import com.book.utils.ThymeleafUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.Context;

import java.io.IOException;

@WebServlet("/addBook")
public class addBookServlet extends HttpServlet {

    BorrowService service;

    @Override
    public void init() throws ServletException {
        service = new com.book.service.impl.BorrowService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context context = new Context();
        ThymeleafUtil.process("add-book.html",context,resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Book book = new Book();
        book.setTitle(req.getParameter("title"));
        book.setDesc(req.getParameter("desc"));
        book.setPrice(Double.parseDouble(req.getParameter("price")));
        service.InsertBook(book);
        resp.sendRedirect("books");
    }
}
