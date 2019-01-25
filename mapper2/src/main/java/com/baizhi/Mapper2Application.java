package com.baizhi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.baizhi.dao")
@EnableScheduling
public class Mapper2Application {

    public static void main(String[] args) {
        SpringApplication.run(Mapper2Application.class, args);
    }

}

