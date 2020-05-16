package com.wow.provider.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/provide")
public class HelloController {

    /* 写法一: 参数变量 */
    /*@RequestMapping("/hello")
    public String hello(@RequestParam String message) {*/

    /* 写法二: 路径变量 */
    @GetMapping("/hello/{message}")
    public String hello(@PathVariable String message) {
        /*try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        System.out.println("-------------call my small service:" + message);
        return String.format("hello %s", message);
    }
}