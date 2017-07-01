package jtk.jms;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Random;

/**
 * Created by jubin on 6/29/2017.
 */
public class JMSSender {

    public static void main(String[] args) throws Exception {

        /*
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("tcp://192.168.56.102:61616");
        Connection connection = cf.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE); // if transaction is true the acknowledgement mode is ignored
        Queue queue = session.createQueue("EM_TRADE.Q");// if the  queue isn't there, it will be created dynamically
        */
        // using JNDI - its used to avoid using vendor specific libraries like ActiveMQConnectionFactory
        Context ctx = new InitialContext();// picksthe naming properties from the jndi.properties file
        Connection connection = ((ConnectionFactory) ctx.lookup("ConnectionFactory")).createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = (Queue) ctx.lookup("EM_TRADE.Q");
        MessageProducer sender = session.createProducer(queue);
        sender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        sender.setTimeToLive(1000); // all messages send will expire in 10s
        TextMessage msg = session.createTextMessage();
        Random r = new Random(10);
        Random priority = new Random();
        for (int i = 0; i < 10; i++) {
            //msg.setJMSDeliveryMode(DeliveryMode.NON_PERSISTENT); //1 - cannot be set at the message - will be reset once send; this can be set on the MessageProducer
           // msg.setJMSExpiration(1000);// ignored by the broker - the default timeout is 0 indicating forever. //
            msg.setText("BUY APPL SHARES " + r.nextInt(1000));
            msg.setStringProperty("TraderName", "jubin");
            displayHeaders(msg);
            sender.send(msg,DeliveryMode.PERSISTENT,priority.nextInt(9),100000);
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
