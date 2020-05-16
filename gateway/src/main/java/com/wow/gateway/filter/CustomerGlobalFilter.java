package com.wow.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关自定义全局过滤器。
 * 个人理解：
 * 全局过滤器和网关过滤器只是在业务意义上的区分而已。
 * 全局过滤器 处理所有 的请求。先处理。例如: token,权限 等校验
 * 网关过滤器 根据业务需求不同，出现的差异化处理。后处理。例如: 统计登录服务调用数量。
 * 两种过滤器 实现功能是相通的。
 *
 * 网关过滤器可以做很多事情。例如:
 * 1. 限流。(每个IP可以访问一段时间内的总量)
 * 2. 熔断。(访问微服务出现等待)
 * 3. ...
 *
 */
public class CustomerGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
