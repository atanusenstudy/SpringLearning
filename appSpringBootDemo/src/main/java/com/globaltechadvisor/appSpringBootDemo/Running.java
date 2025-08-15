package com.globaltechadvisor.appSpringBootDemo;

import org.springframework.context.ApplicationContext;

public class Running {
    public static void run(ApplicationContext context){
        Dev.run(context);
        MultipleAmbiguitySolved.run(context);
    }
}
