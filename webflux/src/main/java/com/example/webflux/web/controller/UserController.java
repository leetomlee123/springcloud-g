package com.example.webflux.web.controller;

import com.example.webflux.api.IUser;
import com.example.webflux.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @author lee
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    @Qualifier(value = "userService")
    private IUser userService;

    @PostMapping("")
    public Mono<User> save(User user) {
        return this.userService.save(user);
    }

    @DeleteMapping("/{username}")
    public Mono<Long> deleteByUsername(@PathVariable String username) {
        return this.userService.deleteByUsername(username);
    }

    @GetMapping("/{username}")
    public Mono<User> findByUsername(@PathVariable String username) {
        return this.userService.findByUsername(username);
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<User> findAll() {
        return this.userService.findAll().delayElements(Duration.ofSeconds(2));
    }
}
