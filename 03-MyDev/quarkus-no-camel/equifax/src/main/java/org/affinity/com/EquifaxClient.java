package org.affinity.com;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http.HttpComponent;
import org.apache.camel.support.jsse.KeyStoreParameters;
import org.apache.camel.support.jsse.SSLContextParameters;
import org.apache.camel.support.jsse.TrustManagersParameters;

public class EquifaxClient extends RouteBuilder {

    private final String PAY_LOAD="{\"consumers\":{\"name\":[{\"identifier\":\"current\",\"firstName\":\"SHEMICA\",\"lastName\":\"XXSHJF\",\"middleName\":\"FAYE\",\"suffix\":\"X\"}],\"socialNum\":[{\"identifier\":\"current\",\"number\":\"666795307\"}],\"dateOfBirth\":\"04281987\",\"addresses\":[{\"identifier\":\"current\",\"houseNumber\":\"7249\",\"streetName\":\"PAJJ ZAFW\",\"streetType\":\"LN\",\"apartmentNumber\":\"12\",\"city\":\"HUMBLE\",\"state\":\"TX\",\"zip\":\"77396\"}],\"phoneNumbers\":[{\"identifier\":\"current\",\"number\":\"4585856325\"}],\"employments\":{\"occupation\":\"VELLA\",\"employerName\":\"XYZ\"}},\"customerReferenceIdentifier\":\"JSON\",\"customerConfiguration\":{\"equifaxUSConsumerCreditReport\":{\"memberNumber\":\"999XX00000\",\"securityCode\":\"XXX\",\"codeDescriptionRequired\":true,\"models\":[{\"identifier\":\"02583\"}],\"endUserInformation\":{\"endUsersName\":\"ALABASTER INC\",\"permissiblePurposeCode\":\"01\"},\"customerCode\":\"BQ81\",\"multipleReportIndicator\":\"F\",\"ECOAInquiryType\":\"Individual\",\"optionalFeatureCode\":[\"X\"],\"vendorIdentificationCode\":\"FI\"}}}";

    @Override
    public void configure() throws Exception {

        KeyStoreParameters ksp = new KeyStoreParameters();
        ksp.setResource("D:\\02-MyDev\\12-Camel\\02-MyDev\\10-Atlasmap-dev1\\equifax\\src\\main\\resources\\api.sandbox.equifax.com.jks");
        ksp.setPassword("changeit");

        TrustManagersParameters tmp = new TrustManagersParameters();
        tmp.setKeyStore(ksp);
        SSLContextParameters scp = new SSLContextParameters();
        scp.setTrustManagers(tmp);
        HttpComponent httpComponent = getContext().getComponent("https", HttpComponent.class);
        httpComponent.setSslContextParameters(scp);

        from("timer:timerName?fixedRate=true&period=60000")
            .setHeader(Exchange.HTTP_METHOD, simple("POST"))
            .setHeader(Exchange.CONTENT_TYPE, constant("application/x-www-form-urlencoded"))
            .setHeader("Authorization",constant("Basic SSS=="))
            .setBody().simple("grant_type=client_credentials&scope=https://api.equifax.com/business/consumer-credit/v1")
            .doTry()
                .to("https::https://api.sandbox.equifax.com/v2/oauth/token")
                    .transform()
                        .jsonpath("$.access_token")
                    .setHeader("token").simple("${body}")
                    .to("direct:equifaxcall")
            .doCatch(Exception.class)
                .log("OAuth Authentication Failed")
        .end();
        
        from("direct:equifaxcall")
            .setHeader("Authorization",simple("Bearer ${header.token}"))
            .setHeader(Exchange.HTTP_METHOD, simple("POST"))
            .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
            .setHeader("Accept", constant("application/json"))
            .setBody().constant(PAY_LOAD)
            .doTry()
                .to("https://api.sandbox.equifax.com/business/consumer-credit/v1/reports/credit-report")
                    .log("${body}")
            .doCatch(Exception.class)
                .log("Equifax Call Failed")
        .end();
    }
}
