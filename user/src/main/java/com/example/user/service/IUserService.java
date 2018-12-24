package com.example.user.service;

import com.example.user.model.User;

public interface IUserService {

    /**
     * 用户基本信息获取
     *
     * @param userName
     * @param password
     * @return
     */
    User getUserInfo(String userName, String password);

    /**
     * 系统登入
     *
     * @param userName
     * @param password
     * @return
     */
    User loginWeb(String userName, String password);

    /**
     * 系统登出
     *
     * @param userName
     * @return
     */
    User logoutWeb(String userName);
}
