package com.example.webflux.api;

import com.example.webflux.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IUser {
    Mono<User> save(User user);

    Mono<Long> deleteByUsername(String name);

    Mono<User> findByUsername(String name);

    Flux<User> findAll();
}
