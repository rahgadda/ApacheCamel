package org.rahul.com;

import org.apache.camel.builder.RouteBuilder;

public class Routes extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("timer://simpleTimer?period=500").setBody(simple("Hello from timer at ${header.firedTime}"))
                .to("stream:out");
    }
}