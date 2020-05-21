package com.wow.customer.controller;

import com.wow.customer.interfacepage.customerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class customerController {
    @Autowired
    private customerService customer;

    @GetMapping("/client_hello/{msg}")
    public String client_hello(@PathVariable String msg){
        try {
            Thread.sleep(3*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return customer.hello(msg);
    }
}
