package org.rahul.com;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Starting Apache Camel Demo!");
        CamelContext camel = new DefaultCamelContext();
        camel.addRoutes(new Routes());
        System.out.println("Ending Apache Camel Demo!");
    }
}
