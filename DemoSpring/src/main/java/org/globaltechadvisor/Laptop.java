package org.globaltechadvisor;

public class Laptop implements Computer{

    private Laptop(){
        System.out.println("Laptop constructor working.");
    }
    public void compile(){
        System.out.println("Laptop is compiling.");
    }
}
