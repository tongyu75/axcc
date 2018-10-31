package com.axcc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

// 将项目中对应的mapper类的路径加进来就可以了
@ServletComponentScan
public class AxccApplication {
	public static void main(String[] args) {
		SpringApplication.run(AxccApplication.class, args);
	}
}