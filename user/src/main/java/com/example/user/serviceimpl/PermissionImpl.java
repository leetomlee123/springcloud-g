package com.example.user.serviceimpl;

import com.example.user.dao.UserPermissionMapper;
import com.example.user.model.UserPermission;
import com.example.user.model.UserPermissionExample;
import com.example.user.model.UserRole;
import com.example.user.service.IPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lee
 */
@Service(value = "permissionImpl")
public class PermissionImpl implements IPermission {
    @Qualifier(value = "userPermissionMapper")
    @Autowired
    private UserPermissionMapper userPermissionMapper;

    @Override
    public List<UserPermission> getPermissionByRoleId(UserRole userRole) {
        UserPermissionExample userPermissionExample = new UserPermissionExample();
        userPermissionExample.createCriteria().andRoleEqualTo(userRole.getRoleId());
        return userPermissionMapper.selectByExample(userPermissionExample);
    }
}
