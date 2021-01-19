package org.affinity.com;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

public class EquifaxHost extends RouteBuilder {
    private static final String GET_MESSAGE =  "{"
                                                + "\"success\": true,"
                                                + "\"message\": \"Get Message\"" + 
                                               "}";
    private static final String POST_MESSAGE = "{"
                                                + "\"success\": true,"
                                                + "\"message\": \"Post Message\"" + 
                                               "}";
                                               
    @Override
    public void configure() throws Exception {
        rest("/api/messages")
        .post()
            .bindingMode(RestBindingMode.json)
            .description("Create New Message Details")
            .consumes("application/json")
            .produces("application/json")
            .route()
                .transform()
                    .constant(POST_MESSAGE).endRest();
        
        rest("/api/messages")
        .get()
            .bindingMode(RestBindingMode.json)
            .description("Get Message Details")
            .consumes("application/json")
            .produces("application/json")
                .route()
                    .setBody().constant(GET_MESSAGE).endRest();
    }
}
