package com.example.gateway;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author lee
 */
@SpringBootApplication
@RestController
@Slf4j
@EnableDiscoveryClient
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class);
    }


    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {

        return builder.routes()
                .route(p -> p
                        .path("/invoke/**")
                        .filters(f -> f
                                .addRequestHeader("Hello", "World")
                                .stripPrefix(1))
                        .uri("lb://service-invoke"))
                .route(p -> p
                        .path("/user/**")
                        .filters(f -> f
                                .addRequestHeader("Hello", "World")
                                .stripPrefix(1)
                                .requestRateLimiter(l -> l
                                        .setKeyResolver(keyResolver())
                                        .setRateLimiter(redisRateLimiter())))
                        .uri("lb://service-user"))
                .build();
    }

    @Bean
    RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(10, 100);
    }

    class UriKeyResolver implements KeyResolver {

        @Override
        public Mono<String> resolve(ServerWebExchange exchange) {

            //用户认证接口限流
            return Mono.just("/users/auth");
        }

    }

    @Bean
    public UriKeyResolver keyResolver() {
        return new UriKeyResolver();
    }

    @Test
    public void c() throws UnknownHostException {
        System.out.println(InetAddress.getLocalHost().getHostAddress());
    }
}


