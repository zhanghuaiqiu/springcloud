package com.wow.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

/**
 * 自定义网关过滤器 实现方式二 [用过滤器工厂实现]
 */
@Component
public class CustomerGatewayFilterFactory extends AbstractGatewayFilterFactory<CustomerGatewayFilterFactory.Config> {

    private static final Logger log = LoggerFactory.getLogger(CustomerGatewayFilterFactory.class);
    private static final String COUNT_START_TIME = "countStartTime";

    @Value("${customerGatewayFilter}")
    private String key;

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(key);
    }

    /**
     * 重载构造函数
     */
    public CustomerGatewayFilterFactory() {
        super(Config.class);
    }

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
                    log.info(exchange.getRequest().getURI().getRawPath() + ": " + gobalTime + "ms");
                })
        );
    }

    @Override
    public GatewayFilter apply(Config conf) {
        return (exchange, chain) -> {
            /* 判断是否走自定义网关过滤器业务逻辑 */
            if (!conf.isEnabled()) {
                return chain.filter(exchange);
            }
            return business(exchange, chain);
        };
    }

    /**
     * 控制是否开启统计
     */
    public static class Config {
        private boolean enabled;

        public Config() {
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }
}
