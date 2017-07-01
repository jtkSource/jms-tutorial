package jtk.jms.ack;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Random;

/**
 * Created by jubin on 6/29/2017.
 */
public class JMSSender {

    public static void main(String[] args) throws Exception {
        Context ctx = new InitialContext();// picksthe naming properties from the jndi.properties file
        Connection connection = ((ConnectionFactory) ctx.lookup("ConnectionFactory")).createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = (Queue) ctx.lookup("EM_TRADE.Q");
        MessageProducer sender = session.createProducer(queue);
        TextMessage msg = session.createTextMessage();
        Random r = new Random(10);
        msg.setText("BUY APPL SHARES " + r.nextInt(1000));
        msg.setStringProperty("TraderName", "jubin");
        sender.send(msg);
        System.out.println("--Message send--");
        displayHeaders(msg);
        System.out.println("--------------------");
        connection.close(); // closing the connection close the session
    }

    public static void displayHeaders(Message msg) throws JMSException {
        System.out.println("JMS Message Id: " + msg.getJMSMessageID());
        System.out.println("JMS DeliveryMode: " + msg.getJMSDeliveryMode());
        System.out.println("JMS Expiration: " + msg.getJMSExpiration());
        System.out.println("JMSPriority: " + msg.getJMSPriority());
    }
}
