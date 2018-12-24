package com.example.webflux.dao;

import com.example.webflux.model.Miui;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;


@Repository(value = "miuiRepository")
public interface MiuiRepository extends ReactiveCrudRepository<Miui, String> {
    @Tailable
    Flux<Miui> findBy();
}

