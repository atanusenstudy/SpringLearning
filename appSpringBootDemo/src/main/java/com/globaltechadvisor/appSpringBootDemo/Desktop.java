package com.globaltechadvisor.appSpringBootDemo;

public class Desktop implements  Computer{

    @Override
    public void compile(String type){
        System.out.println("Desktop is compiling with :" + type);
    }
}