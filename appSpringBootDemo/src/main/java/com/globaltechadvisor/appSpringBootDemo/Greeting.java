package com.globaltechadvisor.appSpringBootDemo;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Greeting {

    @RequestMapping("/Hello")
    public String Greet(){
        return "Hey baby It's my first project wanna go on date";
    }
}
