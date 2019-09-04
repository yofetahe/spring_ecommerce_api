package com.yhabtu.ecommerce.controller;

import java.util.Date;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping("/getTestValue")
	public int getTestValue() {
		System.out.println(new Date());
		return 10;
	}
}
