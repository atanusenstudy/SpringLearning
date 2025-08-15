package org.globaltechadvisor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App
{
    public static void main( String[] args )
    {

        // We need to create bean and everything when using Spring framework without Spring Boot
        // Here we are creating IOC Container
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

        Dev devObj = context.getBean(Dev.class);

        //Dev devObj = (Dev)context.getBean("dev");
        //devObj.build();
    }
}
