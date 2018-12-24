package com.example.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * @author lee
 */
public class PreGatewayFilterFactory extends AbstractGatewayFilterFactory {

    public PreGatewayFilterFactory() {
        super(Config.class);
    }

    public GatewayFilter apply() {

        return apply(o -> {

        });
    }


    @Override
    public GatewayFilter apply(Object config) {
        // grab configuration from Config object
        return (exchange, chain) -> {
            //If you want to build a "pre" filter you need to manipulate the
            //request before calling change.filter
            ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
            builder.header("GatewayFilter", "PreGatewayFilterFactory success");
            //use builder to manipulate the request
            return chain.filter(exchange.mutate().request(builder.build()).build());
        };

    }

    public static class Config {
        //Put the configuration properties for your filter here
    }
}
