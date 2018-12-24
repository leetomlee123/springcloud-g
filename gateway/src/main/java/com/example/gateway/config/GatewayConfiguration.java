package com.example.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.support.DefaultServerCodecConfigurer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


@Configuration
public class GatewayConfiguration {
    private static final String ALLOWED_HEADERS = "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN";
    private static final String ALLOWED_METHODS = "GET, PUT, POST, DELETE, OPTIONS";
    private static final String ALLOWED_ORIGIN = "*";
    private static final String MAX_AGE = "3600";
    @Autowired
    private KeyResolver addressKeyResolver;
    @Bean
    public ServerCodecConfigurer serverCodecConfigurer() {
        return new DefaultServerCodecConfigurer();
    }
    @Bean
    public RouteDefinitionLocator discoveryClientRouteDefinitionLocator(DiscoveryClient discoveryClient,
                                                                        DiscoveryLocatorProperties properties) {
        return new DiscoveryClientRouteDefinitionLocator(discoveryClient, properties);
    }
    @Bean
    public WebFilter corsFilter() {
        return (ServerWebExchange ctx, WebFilterChain chain) -> {
            ServerHttpRequest request = ctx.getRequest();
            if (CorsUtils.isCorsRequest(request)) {
                ServerHttpResponse response = ctx.getResponse();
                HttpHeaders headers = response.getHeaders();
                headers.add("Access-Control-Allow-Origin", ALLOWED_ORIGIN);
                headers.add("Access-Control-Allow-Methods", ALLOWED_METHODS);
                headers.add("Access-Control-Max-Age", MAX_AGE);
                headers.add("Access-Control-Allow-Headers", ALLOWED_HEADERS);
                if (request.getMethod() == HttpMethod.OPTIONS) {
                    response.setStatusCode(HttpStatus.OK);
                    return Mono.empty();
                }
            }
            return chain.filter(ctx);
        };
    }



    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                //权重路由
//      weight  在开发或者测试的时候，或者线上发布，线上服务多版本控制的时候，需要对服务提供权重路由，最常见的使用就是，一个服务有两个版本，旧版本V1，新版本v2。在线上灰度的时候，需要通过网关动态实时推送，路由权重信息。比如95%的流量走服务v1版本，5%的流量走服务v2版本。
//       retry         当转发到代理服务时，遇到指定的 服务端 Error，如 http Status为500时，我们可以设定重试几次。除了对指定的异常重试之外，还可以指定请求的方法，GET或POST。
//实验场景涉及到：网关服务和用户服务。客户端请求经过网关，请求用户服务的API接口，遇到指定的异常时，进行重试。
//                .route(r -> r.weight("provide", 95).and().path("/test/weight").filters(f -> f.retry(3).filter(new PreGatewayFilterFactory().apply()).filter(new PostGatewayFilterFactory().apply()).requestRateLimiter().rateLimiter(RedisRateLimiter.class, config -> config.setBurstCapacity(1).setReplenishRate(1)).configure(config -> config.setKeyResolver(addressKeyResolver)).stripPrefix(1).hystrix(config -> config.setFallbackUri("forward:/fallback").setName("fallbackcmd"))).uri("http://localhost:3001/test/weight"))
//                .route(r -> r.weight("provide", 5).and().path("/test/weight").filters(f -> f.retry(3).filter(new PreGatewayFilterFactory().apply()).filter(new PostGatewayFilterFactory().apply()).requestRateLimiter().rateLimiter(RedisRateLimiter.class, config -> config.setBurstCapacity(1).setReplenishRate(1)).configure(config -> config.setKeyResolver(addressKeyResolver)).stripPrefix(1).hystrix(config -> config.setFallbackUri("forward:/fallback").setName("fallbackcmd"))).uri("http://localhost:3002/test/weight"))
                //集成了可能会用到的各种filter
                .route(r -> r.path("/user/**").filters(f -> f.retry(3).requestRateLimiter().rateLimiter(RedisRateLimiter.class, config -> config.setBurstCapacity(0).setReplenishRate(100)).configure(config -> config.setKeyResolver(addressKeyResolver)).stripPrefix(1)).uri("lb://service-user"))
                .route(r -> r.path("/invoke/**").filters(f -> f.retry(3).requestRateLimiter().rateLimiter(RedisRateLimiter.class, config -> config.setBurstCapacity(1).setReplenishRate(1)).configure(config -> config.setKeyResolver(addressKeyResolver)).stripPrefix(1)).uri("lb://service-invoke"))
                .build();
    }

}
