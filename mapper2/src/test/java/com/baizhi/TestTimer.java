package com.baizhi;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TestTimer {
    public static void main(String[] args) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                String s = "定时器测试"+new Date();
                System.out.println(s);
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask,3000,3000);
    }

}
