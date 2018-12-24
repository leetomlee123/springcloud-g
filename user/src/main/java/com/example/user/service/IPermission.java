package com.example.user.service;

import com.example.user.model.UserPermission;
import com.example.user.model.UserRole;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional(rollbackFor = Exception.class)
public interface IPermission {
    List<UserPermission> getPermissionByRoleId(UserRole userRole);
}
