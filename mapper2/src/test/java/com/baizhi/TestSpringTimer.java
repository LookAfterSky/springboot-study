package com.baizhi;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TestSpringTimer {

    @Scheduled(fixedDelay = 3000)
    public void testO1(){
        System.out.println("定时器任务1");
    }
    @Scheduled(fixedRate = 5000,initialDelay = 3000)
    public void test02(){System.out.println("task2 :"+new Date());}
}
