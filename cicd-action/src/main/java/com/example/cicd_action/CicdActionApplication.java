package com.example.cicd_action;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CicdActionApplication {
	@GetMapping("/welcome")
	public String welcome() {
		return "welcome";
	}
	public static void main(String[] args) {
		SpringApplication.run(CicdActionApplication.class, args);
	}

}
