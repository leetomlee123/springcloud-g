package com.example.webflux.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

/**
 * @author lee
 */
@Component
public class TimeHandler {
    public Mono<ServerResponse> getTime(ServerRequest serverRequest) {
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(Flux.interval(Duration.ofSeconds(1)).map(l -> new SimpleDateFormat("HH:mm:ss").format(new Date())),
                String.class);
    }
}
