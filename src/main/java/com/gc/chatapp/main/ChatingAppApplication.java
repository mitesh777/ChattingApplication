package com.gc.chatapp.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
//chatManagement
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages={"com.gc.*"})
public class ChatingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatingAppApplication.class, args);
	}
}
