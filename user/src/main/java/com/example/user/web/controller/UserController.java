package com.example.user.web.controller;

import com.example.user.dto.UserTokenState;
import com.example.user.model.User;
import com.example.user.service.IUserService;
import com.example.user.service.UserTokenService;
import com.example.user.serviceimpl.UserImpl;
import com.example.user.util.EmailTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Date;

/**
 * @author lee
 */
@Slf4j
@RestController
@RequestMapping(value = "/users")
public class UserController {
    @Qualifier(value = "userImpl")
    @Autowired
    private UserImpl userImpl;
    @Autowired
    private EmailTool emailTool;

    @Value("${battcn.security.token.expiration-time}")
    private String expireTime;

    @Autowired
    @Qualifier("userTokenService")
    UserTokenService userTokenService;

    @Autowired
    @Qualifier("userService")
    private IUserService panoramicUserService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(
            @RequestParam("username") String username,
            @RequestParam("password") String password

    ) {

        if (panoramicUserService.getUserInfo(username, new BCryptPasswordEncoder(12, new SecureRandom("leetomlee123".getBytes())).encode(password)) == null) {
            return ResponseEntity.badRequest().body("用户名或密码错误或邮箱未激活！");

        }
        try {
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, new BCryptPasswordEncoder(12, new SecureRandom("leetomlee123".getBytes())).encode(password))
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = (User) authentication.getPrincipal();
            String jws = userTokenService.generateToken(user.getLoginName(), user.getAuthoritie(), expireTime);
            return ResponseEntity.ok(new UserTokenState(jws, Integer.valueOf(expireTime)));
        } catch (Exception e) {
            return ResponseEntity.ok(new UserTokenState());
        }
    }

    @PostMapping("/web/register")
    public ResponseEntity addUser(User user) {

        if (StringUtils.isEmpty(user)) {
            return ResponseEntity.ok("参数不能为空");
        } else {
            if (!userImpl.registerCheck(user)) {
                return ResponseEntity.ok("用户名or邮箱已存在");
            }
            user.setPassword(new BCryptPasswordEncoder(12, new SecureRandom("leetomlee123".getBytes())).encode(user.getPassword()));
            user.setCtime(new Date());
            user.setOperator("web");
            userImpl.addUser(user);
            new Thread(() -> emailTool.sendSimpleMail(user.getEmail(), "http://192.168.81.129:17080/user/users/active/" + user.getId()));

            return new ResponseEntity(HttpStatus.OK);
        }

    }


    @GetMapping(value = "/active/{id}")
    public ResponseEntity active(@PathVariable(value = "id") Integer id) {
        int active = userImpl.active(id);
        if (active == 1) {
            return ResponseEntity.ok().contentType(new MediaType(MediaType.TEXT_HTML, Charset.forName("utf-8"))).body("激活成功,<a href='http://192.168.81.129'>登陆</a>");
        } else {
            return ResponseEntity.badRequest().contentType(new MediaType(MediaType.TEXT_HTML, Charset.forName("utf-8"))).body("激活失败,<a href='mailto:lx_stu@qq.com'>联系管理员</a>");
        }
    }

    @GetMapping(value = "/valid/{token}")
    public boolean validToken(@PathVariable(value = "token") String token) {
        return userTokenService.validTokenExpire(token);
    }

    @RolesAllowed(value = ("admin"))
    @GetMapping(value = "/fresh/{token}")
    public String freshToken(@PathVariable(value = "token") String token) {
        return userTokenService.refreshToken(token, Duration.ofMillis(15).toString());
    }

    @GetMapping(value = "test")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity res() {
        log.info("user has admin");
        return new ResponseEntity("xxx", HttpStatus.OK);
    }

}

