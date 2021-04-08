package com.atguigu.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu"})  //扫描配置类common等
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class,args);

    }
}
