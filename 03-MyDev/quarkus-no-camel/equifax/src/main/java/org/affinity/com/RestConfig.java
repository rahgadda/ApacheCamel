package org.affinity.com;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

public class RestConfig extends RouteBuilder {

    @Override
    public void configure() {
        // from("timer:timerName?fixedRate=true&period=60000")
        // .log("HelloWorld");

        restConfiguration()
            .component("undertow")
            .host("localhost")
            .port(8080)
            .bindingMode(RestBindingMode.json)
            .dataFormatProperty("prettyPrint", "true")
            .apiContextPath("/api/swagger.json")
                .apiProperty("base.path", "/api")
                .apiProperty("api.title", "Affinity Dynamic Rest Api")
                .apiProperty("api.version", "1.0.0")
                .apiProperty("cors", "true");
    }
}
