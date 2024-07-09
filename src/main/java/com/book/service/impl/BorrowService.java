package com.book.service.impl;

import com.book.dao.BookMapper;
import com.book.dao.StudentMapper;
import com.book.dao.UserMapper;
import com.book.entity.Book;
import com.book.entity.Borrow;
import com.book.entity.Student;
import com.book.entity.User;
import com.book.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.*;
import java.util.stream.Collectors;

public class BorrowService implements com.book.service.BorrowService {
    //返回借出去的书的信息
    @Override
    public List<Borrow> getBorrowList() {
        try(SqlSession sqlSession = MybatisUtil.getSession()) {
            BookMapper mapper = sqlSession.getMapper(BookMapper.class);
            List<Borrow> list = mapper.getBorrowList();
            return list;
        }
    }

    //还书
    @Override
    public void returnBorrow(String id) {
        try(SqlSession sqlSession = MybatisUtil.getSession()) {
            BookMapper mapper = sqlSession.getMapper(BookMapper.class);
            mapper.DeleteBorrow(id);
        }
    }

    //返回可以借出去的书
    @Override
    public List<Book> getActiveBookList() {
        Set<Integer> set = new HashSet<>();
        //存储借出去的书
        this.getBorrowList().forEach(borrow -> set.add(borrow.getBook_id()));
        try(SqlSession sqlSession = MybatisUtil.getSession()) {
            BookMapper mapper = sqlSession.getMapper(BookMapper.class);
        return  mapper.getBookList()
                      .stream()
                //留下不在BorrowList的书
                      .filter(book -> !set.contains(book.getBid()))
                      .collect(Collectors.toList());
        }
    }


//返回学生信息
    @Override
    public List<Student> getStudentList() {
        try(SqlSession sqlSession = MybatisUtil.getSession()) {
            StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
            return mapper.getStudent();
        }
    }
//添加借阅信息
    @Override
    public void addBorrow(int sid, int bid) {
        try(SqlSession sqlSession = MybatisUtil.getSession()) {
            BookMapper mapper = sqlSession.getMapper(BookMapper.class);
            mapper.InsertBorrow(sid, bid);
        }
    }
//返回所有书籍的信息，包含借阅与否
    @Override
    public Map<Book,Boolean> getAllBook() {
        Set<Integer> set = new HashSet<>();
        //存储借出去的书
        this.getBorrowList().forEach(borrow -> set.add(borrow.getBook_id()));
        try(SqlSession sqlSession = MybatisUtil.getSession()) {
            Map<Book,Boolean> map = new LinkedHashMap<>();
            BookMapper mapper = sqlSession.getMapper(BookMapper.class);
            //遍历书籍，存储书的信息，用contains判断set中是否有这本书
            mapper.getBookList().forEach(book ->
                    map.put(book,set.contains(book.getBid())));
            return map;
        }
    }

    @Override
    public void deleteBook(int bid) {
        try (SqlSession sqlSession = MybatisUtil.getSession()){
            BookMapper mapper = sqlSession.getMapper(BookMapper.class);
            mapper.DeleteBook(bid);
        }
    }

    @Override
    public void InsertBook(Book book) {
        try (SqlSession sqlSession = MybatisUtil.getSession()){
            BookMapper mapper = sqlSession.getMapper(BookMapper.class);
            mapper.InsertBook(book.getTitle(), book.getDesc(), book.getPrice());
        }
    }
}
