package com.wow.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.time.Instant;

/**
 * author: zhq
 * time: 2020-05-16
 * 自定义网关过滤器 实现方式一 [自定义类实现]
 * 备注: 定义完过滤器后，需要把过滤器注册到 router 中
 */
public class CustomerGatewayFilter implements GatewayFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger( CustomerGatewayFilter.class );
    private static final String COUNT_START_TIME = "countStartTime";
    /**
     * 自定义网关过滤器中的业务逻辑代码
     * @return
     */
    private Mono<Void> business(ServerWebExchange exchange, GatewayFilterChain chain){
        exchange.getAttributes().put(COUNT_START_TIME, Instant.now().toEpochMilli() );
        return chain.filter(exchange).then(
                Mono.fromRunnable(() -> {
                    /* 获取路由业务的总耗时 */
                    long startTime = exchange.getAttribute(COUNT_START_TIME);
                    long gobalTime =(Instant.now().toEpochMilli() - startTime);
                    log.info("--------> GateWayFilter:" + exchange.getRequest().getURI().getRawPath() + ": " + gobalTime + "ms");
                })
        );
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return business(exchange, chain);
    }

    /**
     * 功能: 给过滤器设定优先级别的，值越大则优先级越低
     * @return
     */
    @Override
    public int getOrder() {
        return 3;
    }
}
