package com.wow.gateway;

import com.wow.gateway.filter.CustomerGatewayFilter;
import com.wow.gateway.filter.CustomerGatewayFilterFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    /**
     * 注册自定义网关过滤器
     * @param builder
     * @return
     * 备注：这种模式不需要在配置文件中配置 routes 相关信息了
     */
    /*@Bean
    public RouteLocator customerRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/client/**")    //替代配置文件中的path
                        .filters(f -> f.filter(new CustomerGatewayFilter()))
                        .uri("lb://nacos-customer") //替代配置文件中的uri
                        .id("hello_route")  //替代配置文件中的id
                )
                .build();
    }*/

    /**
     * 这种模式需要在 配置文件中配置 开启功能
     * filters:
     *   - Customer=true #开启自定义网关过滤器工厂模式
     * @return
     */
    @Bean
    //@Order(0) //过滤器等级注解
    public CustomerGatewayFilterFactory elapsedGatewayFilterFactory() {
        return new CustomerGatewayFilterFactory();
    }
}
