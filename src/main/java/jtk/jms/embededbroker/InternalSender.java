package jtk.jms.embededbroker;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by jubin on 7/1/2017.
 */
public class InternalSender {

    private Session session;
    private MessageProducer sender;

    public InternalSender(){
        try{
            ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("vm://embedded2");
            Connection connection = cf.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE); // if transaction is true the acknowledgement mode is ignored
            Queue queue = session.createQueue("EM_EMBEDDED_TRADE.Q");// if the  queue isn't there, it will be created dynamically
            sender = session.createProducer(queue);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void sendMessage(String trade) {
        try{
            TextMessage msg = session.createTextMessage(trade);
            sender.send(msg);
            System.out.println("Trade Sent...");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
