/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package replicator;

import domain.ShoppingList;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

/**
 *
 * @author keiranpatel
 */
public class ReplicatingBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("jms:queue:json-list")
                .unmarshal().json(JsonLibrary.Gson, ShoppingList.class)
                .to("jms:queue:list");

        from("jms:queue:list")
                .split().simple("${body.items}")
                .multicast()
                .to("jms:queue:graphql", "jms:queue:rest");

        from("jms:queue:rest")
                .removeHeaders("*") // remove headers to stop them being sent to the service
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .marshal()
                .json(JsonLibrary.Gson)
                .to("http://localhost:8081/api/shopping");

        from("jms:queue:graphql")
                .toD("graphql://http://localhost:8083/graphql?query=mutation{addItem(name:\"${body.name}\", description:\"${body.description}\"){items{name,description}}}")
   
                .log("GraphQL service called"); //muataion needs changing-takes removeItem, addItem=String!

    }
}
