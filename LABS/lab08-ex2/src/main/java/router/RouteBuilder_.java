/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package router;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

/**
 *
 * @author keiranpatel
 */
public class RouteBuilder_ extends RouteBuilder {

    @Override
    public void configure() {

        // routes go here
        //from("jms:queue:in").to("jms:queue:out");
//        from("jms:queue:in")
//                .multicast()
//                .to("jms:queue:out1", "jms:queue:out2");
        from("jms:queue:items") //qs 10 
                .log("Product Name: ${body}")
                .filter().simple("${body.urgent} == 'true'")
                .to("jms:queue:sort");

        from("jms:queue:sort") //qs12
                .choice()
                .when().simple("${body.urgent} == 'true'")
                .to("jms:queue:urgent")
                .otherwise()
                .to("jms:queue:not-urgent");

        // queue contains objects
// convert them to JSON
        from("jms:queue:urgent") //qs14
                // convert to JSON using marshal method
                .marshal().json(JsonLibrary.Gson)
                // ensure the message body is a string (sometimes it ends up as a byte-stream)
                .convertBodyTo(String.class)
                // send to a queue that expects JSON
                .to("jms:queue:urgent-json");

        // convert them to JSON
        from("jms:queue:not-urgent") //qs15
                // convert to JSON using marshal method
                .marshal().json(JsonLibrary.Gson)
                // ensure the message body is a string (sometimes it ends up as a byte-stream)
                .convertBodyTo(String.class)
                // send to a queue that expects JSON
                .to("jms:queue:not-urgent-json");

    }

}
