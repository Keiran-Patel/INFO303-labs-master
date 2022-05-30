/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package replicator;

import com.google.gson.Gson;
import domain.ShoppingItem;
import domain.ShoppingList;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.activemq.ActiveMQComponent;
import org.apache.camel.impl.DefaultCamelContext;

/**
 *
 * @author keiranpatel
 */
public class ReplicatingClient {

    public static void main(String[] args) throws Exception {

        // create default context
        CamelContext camel = new DefaultCamelContext();

// register ActiveMQ as the JMS handler
        ActiveMQComponent activemq = ActiveMQComponent.activeMQComponent();
        camel.addComponent("jms", activemq);

// transfer the entire exchange, or just the body and headers?
        activemq.setTransferExchange(false);

// trust all classes being used to send serialised domain objects
        activemq.setTrustAllPackages(true);

// turn exchange tracing on or off (false is off)
        camel.setTracing(false);

// enable stream caching so that things like loggers don't consume the messages
        camel.setStreamCaching(true);

// create message producer
        ProducerTemplate producer = camel.createProducerTemplate();

// context must be started before we can send messages
        camel.start();

        Collection<ShoppingItem> items = new ArrayList<>();
        items.add(new ShoppingItem("test", "a test item"));
        items.add(new ShoppingItem("test1", "a testing item"));
        ShoppingList shoppingList = new ShoppingList(items);

        String json = new Gson().toJson(shoppingList);

// send a message
        producer.sendBody("jms:queue:json-list", json);
        camel.stop();
    }
}
