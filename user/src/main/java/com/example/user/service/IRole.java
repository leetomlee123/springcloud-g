package com.example.user.service;

import com.example.user.model.User;
import com.example.user.model.UserRole;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional(rollbackFor = Exception.class)
public interface IRole {
    List<UserRole> getRoleByUserName(User user);
}
