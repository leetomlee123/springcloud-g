package com.example.gateway.filter;

import com.leetomlee.cloud.common.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Date;

/**
 * @author lee
 */
@Configuration
@Slf4j
public class GlobalRouteFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
        URI uri = builder.build().getURI();
        log.info(DateUtil.getFormatDateTime(new Date())+
                "|"+uri.getHost()+
                "|"+exchange.getRequest().getRemoteAddress().getHostString()+"|"+uri.toString()+"|"+uri.getPath()+"|"+exchange.getRequest().getHeaders().get("User-Agent").get(0)
        );
        builder.header("GlobalFilter", "GlobalFilter success");
        chain.filter(exchange.mutate().request(builder.build()).build());
        return chain.filter(exchange.mutate().request(builder.build()).build());
    }
}
