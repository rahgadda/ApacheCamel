package org.mylearning.com;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.mylearning.com.dto.Message;

public class Routes extends RouteBuilder {
    private final  Set<Message> messages = Collections.synchronizedSet(new LinkedHashSet<>());

    public Routes(){
        this.messages.add(new Message());
        this.messages.add(new Message("Hello Camel"));
    }    

    @Override
    public void configure() throws Exception {
        //Messages Example
        from("platform-http:/hello?httpMethodRestrict=GET,POST")
        .choice()
            .when(simple("${header.CamelHttpMethod} == 'GET'"))
                .setBody()
                    .constant(this.messages)
            .endChoice()
            .when(simple("${header.CamelHttpMethod} == 'POST'"))
                .unmarshal()
                    .json(JsonLibrary.Jackson, Message.class)
                    .process()
                    .body(Message.class, messages::add)
                .setBody()
                    .constant(this.messages)
            .endChoice()
        .end()
        .marshal().json();

        //AtlasMap Transformation Example
        from("platform-http:/transform?httpMethodRestrict=POST")
        .choice()
            .when(simple("${header.CamelHttpMethod} == 'POST'"))
                .unmarshal()
                    .json(JsonLibrary.Jackson, Message.class)
                    .process()
                    .body(Message.class, messages::add)
                .setBody()
                    .constant(this.messages)
            .endChoice()
        .end()
        .marshal().json()
        .log("Received ==> ${body}")
        .to("atlasmap:hell-world.adm");
    }
}
