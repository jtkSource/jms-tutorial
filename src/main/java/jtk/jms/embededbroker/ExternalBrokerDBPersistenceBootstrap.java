package jtk.jms.embededbroker;

import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

import java.net.URI;

/**
 * Created by jubin on 7/1/2017.
 */
public class ExternalBrokerDBPersistenceBootstrap {
    public ExternalBrokerDBPersistenceBootstrap(){
        try{
            BrokerService brokerService = BrokerFactory.createBroker(new URI("xbean:broker.xml"));
            brokerService.start();

            System.out.println("Embedded non-persistent broker");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Thread(ExternalBrokerDBPersistenceBootstrap::new).start();
    }
}
