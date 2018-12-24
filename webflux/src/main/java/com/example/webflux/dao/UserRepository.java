package com.example.webflux.dao;

import com.example.webflux.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
@Repository(value = "userRepository")
public interface UserRepository extends ReactiveCrudRepository<User, String> {
    Mono<User> findByUsername(String username);

    Mono<Long> deleteByUsername(String username);
}
