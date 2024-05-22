package com.book.service.impl;

import com.book.dao.UserMapper;
import com.book.entity.User;
import com.book.utils.MybatisUtil;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.session.SqlSession;

public class UserService implements com.book.service.UserService {

    @Override
    public boolean auth(String username, String password, HttpSession session){
        try(SqlSession sqlSession = MybatisUtil.getSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            User user = mapper.getUser(username,password);
            if(user == null) return false;
            session.setAttribute("user",user);
            return true;
        }
    }
}
