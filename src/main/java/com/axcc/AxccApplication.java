package com.axcc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class AxccApplication {
	public static void main(String[] args) {
		SpringApplication.run(AxccApplication.class, args);
	}
}
