package com.example;

import com.san.annotations.Controller;
import com.san.annotations.Function;;

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
