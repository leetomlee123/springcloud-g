package com.example.user.serviceimpl;

import com.example.user.dao.UserRoleMapper;
import com.example.user.model.User;
import com.example.user.model.UserRole;
import com.example.user.model.UserRoleExample;
import com.example.user.service.IRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "roleImpl")
public class RoleImpl implements IRole {
    @Qualifier(value = "userRoleMapper")
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public List<UserRole> getRoleByUserName(User user) {
        UserRoleExample userRoleExample = new UserRoleExample();
        userRoleExample.createCriteria().andUserIdEqualTo(String.valueOf(user.getId()));
        return userRoleMapper.selectByExample(userRoleExample);
    }
}
