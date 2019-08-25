package org.rahul.com;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class App {
    public static void main(String args[]) throws Exception {
        System.out.println("Starting Apache Camel Rest Consume Demo!");
        CamelContext camel = new DefaultCamelContext();
        camel.addRoutes(new Routes());
        camel.start();
        System.out.println("Ending Apache Camel Rest Consume Demo!");
    }
}