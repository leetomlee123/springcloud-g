package com.example.user.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

public interface UserTokenService {
    /**
     * 根据Token获取用户信息
     *
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token);

    /**
     * 根据用户名来生成Token
     *
     * @param username
     * @param expireTime
     * @return
     */
    public String generateToken(String username, List<GrantedAuthority> permission, String expireTime);

    /**
     * 验证Token基本信息
     *
     * @param token
     * @param userDetails
     * @return
     */
    public Boolean validateToken(String token, UserDetails userDetails);

    /**
     * 获取Token
     *
     * @param request
     * @return
     */
    public String getToken(HttpServletRequest request);

    /**
     * Token信息的刷新
     *
     * @param token
     * @param expireTime
     * @return
     */
    public String refreshToken(String token, String expireTime);


    /**
     * 用户对应的Token信息的删除
     *
     * @param token
     */
    public void deleteToken(String token);

    /**
     * 验证Token的实效性
     *
     * @param token
     * @return
     */
    public Boolean validTokenExpire(String token);

    public Date getExpireTimeFromToken(String token);
}
