package jtk.jms.embededbroker;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Random;

/**
 * Created by jubin on 6/29/2017.
 */
public class JMSSenderForEmbeddedBroker {

    public static void main(String[] args) throws Exception {


        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61888");
        Connection connection = cf.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE); // if transaction is true the acknowledgement mode is ignored
        Queue queue = session.createQueue("EM_EMBEDDED_TRADE.Q");// if the  queue isn't there, it will be created dynamically
        MessageProducer sender = session.createProducer(queue);
        TextMessage msg = session.createTextMessage();
        Random r = new Random(10);
        Random priority = new Random();
        for (int i = 0; i < 10; i++) {
            msg.setText("BUY APPL SHARES " + r.nextInt(1000));
            msg.setStringProperty("TraderName", "jubin");
            sender.send(msg);
            System.out.println("--Message send--");
            displayHeaders(msg);
            System.out.println("--------------------");
        }


        connection.close(); // closing the connection close the session
    }

    public static void displayHeaders(Message msg) throws JMSException {
        System.out.println("JMS Message Id: " + msg.getJMSMessageID());
        System.out.println("JMS DeliveryMode: " + msg.getJMSDeliveryMode());
        System.out.println("JMS Expiration: " + msg.getJMSExpiration());
        System.out.println("JMSPriority: " + msg.getJMSPriority());
    }
}
