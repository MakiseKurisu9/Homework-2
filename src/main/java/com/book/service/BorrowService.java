package com.book.service;

import com.book.entity.Book;
import com.book.entity.Borrow;
import com.book.entity.Student;

import java.util.List;
import java.util.Map;

//接口和实现类分开，方便更改需求和松耦合
public interface BorrowService {
    List<Borrow> getBorrowList();
    void returnBorrow(String id);
    List<Book> getActiveBookList();
    List<Student> getStudentList();
    void addBorrow(int sid , int bid);
    Map<Book, Boolean> getAllBook();
    void deleteBook(int bid);
    void InsertBook(Book book);
}
