package com.example.user.serviceimpl;

import com.example.user.dao.UserMapper;
import com.example.user.dao.UserPermissionMapper;
import com.example.user.dao.UserRoleMapper;
import com.example.user.model.User;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lee
 */
@Service(value = "jwtUserService")
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    @Qualifier("userMapper")
    private
    UserMapper userMapper;
    @Autowired
    @Qualifier("userPermissionMapper")
    private UserPermissionMapper userPermissionMapper;
    @Autowired
    @Qualifier("userRoleMapper")
    private UserRoleMapper userRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByUser(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            ArrayList<GrantedAuthority> authorities = Lists.newArrayList();
            List<String> ids = userRoleMapper.getRoleByUserId(String.valueOf(user.getId()));
            if (ids != null && ids.size() > 0) {
                List<String> permissonByRoleId = userPermissionMapper.getPermissonByRoleId(ids);
                if (permissonByRoleId != null && permissonByRoleId.size() > 0) {
                    permissonByRoleId.forEach(f-> authorities.add(new SimpleGrantedAuthority(f)));
                }
            }
            return new User(user.getUsername(), user.getPassword(), authorities);
        }
    }
}