package com.globaltechadvisor.appSpringBootDemo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class MultipleAmbiguitySolved {

    public static void run(ApplicationContext context) {
        MultipleAmbiguitySolved ambObj = context.getBean(MultipleAmbiguitySolved.class);// Getting Dependency
        ambObj.build();
    }

    @Autowired
    @Qualifier("laptop")
    private Computer computer;

    public void build(){
        System.out.println("Hello baby, Its my Ambiguity Object");
        computer.compile("field");
    }
}
