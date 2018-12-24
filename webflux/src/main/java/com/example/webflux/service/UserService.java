package com.example.webflux.service;

import com.example.webflux.api.IUser;
import com.example.webflux.dao.UserRepository;
import com.example.webflux.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author lee
 */
@Service(value = "userService")
public class UserService implements IUser {
    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;
    @Override

    public Mono<Long> deleteByUsername(String name) {
        return userRepository.deleteByUsername(name);
    }
    @Override

    public Mono<User> findByUsername(String name) {
        return userRepository.findByUsername(name);
    }
    @Override

    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Mono<User> save(User user) {
        return userRepository.save(user)
                .onErrorResume(e ->
                        userRepository.findByUsername(user.getUsername())
                                .flatMap(originalUser -> {
                                    user.setId(originalUser.getId());
                                    return userRepository.save(user);
                                }));
    }

}
