package org.globaltechadvisor;

public class Dev {

    private Laptop laptop;
    private int age;

    public int getAge() {return age;}

    public void setAge(int age) {this.age = age;}

    public Laptop getLaptop() {return laptop;}

    public void setLaptop(Laptop laptop) {this.laptop = laptop;}

    public Dev(){
        System.out.println("Dev Default Constructor object created");
    }

    public Dev(int age){
        System.out.println("Dev Parameterized Constructor object created");
        this.age = age;
    }
    public void build() {
        System.out.println("Hellooooooo");
        System.out.println("Developers Age is : " + age);
    }
}
