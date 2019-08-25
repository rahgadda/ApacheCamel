package org.rahul.com;

import org.apache.camel.builder.RouteBuilder;

public class Routes extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("jetty://http://localhost:8889/clock").log("Received a request")
                .setBody(simple("${date:now:yyyy-MM-dd'T'HH:mm:ssZ}"));
    }
}