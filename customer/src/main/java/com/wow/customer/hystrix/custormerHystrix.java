package com.wow.customer.hystrix;

import com.wow.customer.interfacepage.customerService;
import org.springframework.stereotype.Component;

@Component
public class custormerHystrix implements customerService {
    @Override
    public String hello(String message) {
        return "请求超时";
    }
}
