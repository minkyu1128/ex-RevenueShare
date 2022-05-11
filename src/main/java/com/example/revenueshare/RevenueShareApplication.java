package com.example.revenueshare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.example")
@SpringBootApplication
public class RevenueShareApplication {

    public static void main(String[] args) {

        Long begin = System.currentTimeMillis();

//        SpringApplication.run(RevenueShareApplication.class, args);
        SpringApplication application = new SpringApplication(RevenueShareApplication.class);
        application.addListeners(new ApplicationPidFileWriter());	//PID(Process ID 작성)
        application.run(args);

        Long end = System.currentTimeMillis();
        System.out.println("====================================================");
        System.out.println("기동 소요시간: "+ (end-begin) +"ms");
        System.out.println("====================================================");
    }

}
