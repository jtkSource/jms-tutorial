package jtk.jms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by jubin on 6/30/2017.
 */
public class JMSReceiver {

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("tcp://192.168.56.102:61616");
        Connection connection = cf.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("EM_TRADE.Q");
        MessageConsumer receiver = session.createConsumer(queue);
        TextMessage msg = (TextMessage) receiver.receive(); // is a blocking wait
        //TextMessage msg = (TextMessage) receiver.receive(1000);// waits for 10s

        System.out.println(msg.getText());
        connection.close();
    }
}
