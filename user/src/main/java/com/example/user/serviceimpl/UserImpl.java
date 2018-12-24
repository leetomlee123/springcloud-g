package com.example.user.serviceimpl;

import com.example.user.dao.UserMapper;
import com.example.user.model.User;
import com.example.user.model.UserExample;
import com.example.user.service.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;

/**
 * @author lee
 */
@Service(value = "userImpl")
@Transactional(rollbackFor = Exception.class)
public class UserImpl implements IUser {


    @Override
    public int addUser(User user) {
        user.setPassword(new BCryptPasswordEncoder(12, new SecureRandom("leetomlee123".getBytes())).encode(user.getPassword()));
        return userMapper.insertUser(user);
    }

    @Qualifier(value = "userMapper")
    @Autowired
    private UserMapper userMapper;


    @Override
    public List<User> login(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andLoginNameEqualTo(user.getLoginName()).andPasswordEqualTo(user.getPassword());
        return userMapper.selectByExample(userExample);
    }

    @Override
    public boolean registerCheck(User user) {
        boolean b = userMapper.checkEmail(user.getEmail()) == null ? true : false;
        boolean b1 = userMapper.checkLoginName(user.getLoginName()) == null ? true : false;
        return b & b1
                ;
    }

    @Override
    public int active(Integer id) {
        return userMapper.active(id);
    }
}
