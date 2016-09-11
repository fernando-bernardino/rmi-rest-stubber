package com.github.tbernardino.rmi.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"tbernardino.rmi.test"})
@EnableAutoConfiguration
public class MainClass {
	
    public static void main(String[] args) throws Exception {
        SpringApplication.run(MainClass.class, args);
    }
}
