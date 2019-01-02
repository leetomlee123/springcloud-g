package com.example.gateway;


import com.leetomlee.cloud.common.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.Optional;

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
            log.info("请求ip:" + exchange.getRequest().getRemoteAddress() + "时间:" + DateUtil.getFormatDateTime(new Date()));
            log.info(exchange.getRequest().getURI().getPath());
            //用户认证接口限流
            return Mono.just("/users/auth");
        }

    }

    @Bean
    public UriKeyResolver keyResolver() {
        return new UriKeyResolver();
    }


}


