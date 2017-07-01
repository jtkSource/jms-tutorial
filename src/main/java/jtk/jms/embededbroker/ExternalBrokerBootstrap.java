package jtk.jms.embededbroker;

import org.apache.activemq.broker.BrokerService;

/**
 * Created by jubin on 7/1/2017.
 */
public class ExternalBrokerBootstrap {
    public ExternalBrokerBootstrap(){
        try{
            BrokerService brokerService = new BrokerService();
            brokerService.addConnector("tcp://localhost:61888");
            brokerService.setBrokerName("embedded1");
            brokerService.setPersistent(false);//no guaranteed messages
            brokerService.start();
            System.out.println("Embedded non-persistent broker");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Thread(ExternalBrokerBootstrap::new).start();
    }
}
