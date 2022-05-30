/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aggregator;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

/**
 *
 * @author keiranpatel
 */
public class routebuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer://timer?period=5000")
                .multicast()
                .to("jms:queue:get-graphql-list");

        from("jms:queue:get-graphql-list")
                .to("graphql://http://localhost:8083/graphql?query={shoppingList{items{name,description}}}") // convert to JSON using marshal method
                .marshal().json(JsonLibrary.Gson)
                .to("jms:queue:shopping-lists");

        from("jms:queue:shopping-lists")
                .split().simple("${body.getItems}")
                .to("jms:queue:shopping-items");

        from("jms:queue:shopping-items")
                .marshal().json(JsonLibrary.Gson)
                .to("jms:queue:items-json");

        from("jms:queue:items-json")
                .removeHeader("*")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .to("http://localhost:8081/api/shopping");

    }
    
}
