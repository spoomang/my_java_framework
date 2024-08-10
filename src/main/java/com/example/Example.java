package com.example;

import com.annotations.Controller;
import com.annotations.Function;

@Controller("controllerPath")
public class Example {

    @Function("methodPath")
    public String example() {
        return "hello";
    }

    @Function("testMethod")
    public String testMethod(String body) {
        System.out.println("body inside method "+body);
        return "processed";
    }
}
