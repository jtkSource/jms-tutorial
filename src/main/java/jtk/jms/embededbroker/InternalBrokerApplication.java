package jtk.jms.embededbroker;

import org.apache.activemq.broker.BrokerService;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jubin on 7/1/2017.
 */
public class InternalBrokerApplication {
    private List<String> trades = Arrays.asList("BUY APPL 2000", "BUY IBM 3000", "BUY ATT 2400",
            "SELL APPL 1000", "SELL IBM 2200", "SELL ATT 2200");

    public static void main(String[] args) {
        new Thread(() -> {
            InternalBrokerApplication app = new InternalBrokerApplication();
            app.startBroker();
            app.startTradeProcessors();
            app.processTrades();
        }).start();
    }

    private void startBroker() {
        try {
            //BrokerService brokerService = BrokerFactory.createBroker("tcp://localhost:61888");
            BrokerService brokerService = new BrokerService();
            brokerService.addConnector("tcp://localhost:61888");
            brokerService.setBrokerName("embedded2");
            brokerService.setPersistent(false);
            brokerService.start();
            System.out.println("Internal embedded broker service started...");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void startTradeProcessors() {
        new InternalReciever(1);
        new InternalReciever(2);
        new InternalReciever(3);
        new InternalReciever(4);
        new InternalReciever(5);
        new InternalReciever(6);
        new InternalReciever(7);

    }

    private void processTrades() {
        InternalSender sender = new InternalSender();
        for (String trade : trades) {
            sender.sendMessage(trade);
        }
    }

}
