package org.globaltechadvisor;

import org.springframework.context.ApplicationContext;

public class MultipleAmbiguitySolved {
    private Computer comp;
    public Computer getComp() {return comp;}
    public void setComp(Computer comp) {this.comp = comp;}

    /*
    public static void run(ApplicationContext context) {
        MultipleAmbiguitySolved ambObj = context.getBean(MultipleAmbiguitySolved.class);// Getting Dependency
        ambObj.build();
    }
 */

    public void build(){
        System.out.println("Hello baby, Its my Ambiguity Object");
        comp.compile();
    }


}
