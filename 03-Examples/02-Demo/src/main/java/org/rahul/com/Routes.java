package org.rahul.com;

import org.apache.camel.builder.RouteBuilder;

public class Routes extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("servlet://https:////apex.oracle.com//pls//apex//oraclejet//emp").transform().simple("{$in.body}")
                .to("stream:out");
    }
}