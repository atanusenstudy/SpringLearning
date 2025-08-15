package com.globaltechadvisor.appSpringBootDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


// This will tell Spring boot to create the objet inside container
//Dependency Injection
@Component
public class Dev {

    public static void run(ApplicationContext context){
        Dev devObj = context.getBean(Dev.class);// Getting Dependency
		/* We have to create object and maintain it.
		It's going to create in JVM not inside JVM THEN CONTAINER
		Dev obj = new Dev();
		*/
        devObj.build();
    }

    /*
    @Autowired
        will be used to connect it and get the object from spring boot
        we do not need to use ApplicationContext and long code to get the object
     */
    // 1. Field injection
    @Autowired
    private Laptop laptopObjectFieldInjection; //Instance variable
    private Laptop laptopConstructorInjection;
    private Laptop laptopSetterInjection;


    // 2. Constructor Injection
    public Dev(Laptop laptopConstructorInjection){
        this.laptopConstructorInjection = laptopConstructorInjection;
    }

    // 3. Setter Injection
    @Autowired
    public void SetterInjection(Laptop laptopSetterInjection){
        this.laptopSetterInjection = laptopSetterInjection;
    }
    public void build(){
        System.out.println("Hello baby, Its my Dev Object");
        laptopObjectFieldInjection.compile("field");
        laptopConstructorInjection.compile("constructor");
        laptopSetterInjection.compile("Setter");
    }
}
