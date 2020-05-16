package com.wow.customer.interfacepage;


import com.wow.customer.hystrix.custormerHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/* 注解中关键字: name 对应的值是服务提供者在nacos 中注册的名称 */
@FeignClient(value = "nacos-provider", fallback = custormerHystrix.class)
public interface customerService {
    /* 方法一： 路径变量。定义写法要跟服务提供方一致 */
    @GetMapping ("/provide/hello/{message}")
    public String hello(@PathVariable String message);

    /* 方法二： 参数变量。定义写法要跟服务提供方一致 */
    /*@RequestMapping(value = "/provide/hello")
    public String hello(@RequestParam(value = "message") String message);*/
}
