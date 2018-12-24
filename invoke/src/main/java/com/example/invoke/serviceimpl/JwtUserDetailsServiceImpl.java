package com.example.invoke.serviceimpl;

import com.example.invoke.annotation.TargetDataSource;
import com.example.invoke.common.DataSourceKey;
import com.example.invoke.dao.UserMapper;
import com.example.invoke.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service(value = "jwtUserDetailsServiceImpl")
@TargetDataSource(dataSourceKey = DataSourceKey.DB_SLAVE1)
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    @Qualifier("userMapper")
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByUser(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return user;
        }
    }
}