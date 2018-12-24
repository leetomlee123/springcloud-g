package com.example.user.service;

import com.example.user.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = Exception.class)
public interface IUser {
    int addUser(User user);

    List<User> login(User user);

    boolean registerCheck(User user);

    int active(Integer id);
}
