package com.example.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.function.Consumer;

@Slf4j
public class PostGatewayFilterFactory extends AbstractGatewayFilterFactory {


    public PostGatewayFilterFactory() {
        super(Config.class);
    }

    public GatewayFilter apply() {
        return this.apply(o -> {
        });
    }

    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                ServerHttpResponse response = exchange.getResponse();
                //Manipulate the response in some way
            }));
        };
    }

    @Override
    public GatewayFilter apply(Object config) {
        return null;
    }

    public static class Config {
        //Put the configuration properties for your filter here
    }

}
