package com.axcc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
// 将项目中对应的mapper类的路径加进来就可以了
@MapperScan("com.axcc.dao")
public class AxccApplication {
	public static void main(String[] args) {
		SpringApplication.run(AxccApplication.class, args);
	}
}
