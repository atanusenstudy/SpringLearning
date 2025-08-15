package com.globaltechadvisor.appSpringBootDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class AppSpringBootDemoApplication {

	public static void main(String[] args) {

		// This context will help us to give object from IOCContainer
		ApplicationContext context =  SpringApplication.run(AppSpringBootDemoApplication.class, args);
		Running.run(context);
	}
}
