package com.axcc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
// 将项目中对应的mapper类的路径加进来就可以了
@MapperScan("com.axcc.dao")
@ServletComponentScan
@EnableScheduling
public class AxccApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(AxccApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AxccApplication.class);
    }
}
