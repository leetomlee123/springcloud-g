package com.example.user.serviceimpl;

import com.example.user.dao.UserMapper;
import com.example.user.model.User;
import com.example.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Objects;

@Service("userService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl  implements IUserService {

    @Autowired
    @Qualifier("userMapper")
    private UserMapper userMapper;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public User getUserInfo(String userName, String password) {
        //用户数据查询
        User result = userMapper.findByUser(userName);

        if (null == result) {
            return null;
        }
        if (!Objects.equals(result.getPassword(),new BCryptPasswordEncoder(12, new SecureRandom("leetomlee123".getBytes())).encode(password))) {
            return null;
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public User loginWeb(String userName, String password) {

       User result = getUserInfo(userName, password);

        if (null != result) {
            return result;
        }
        return null;
    }

    @Override
    public User logoutWeb(String userName) {
        return null;
    }
}
