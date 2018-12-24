package com.example.invoke.dao;

import com.example.invoke.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author lee
 */

@Repository(value = "userMapper")
public interface UserMapper {
    @Select(value = "select login_name loginName,password from user where login_name=#{username}")
    User findByUser(@Param(value = "username") String username);
}
