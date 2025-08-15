package com.globaltechadvisor.appSpringBootDemo;


import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary //Prefer this one's. This will solve the problem of ambiguity when there are 2 object implementing same Interface
public class Laptop implements  Computer{

    @Override
    public void compile(String type){
        System.out.println("Laptop is compiling with :" + type);
    }
}
