package jtk.jms.tx;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by jubin on 6/30/2017.
 */
public class JMSTxReceiver {

    public static void main(String[] args) throws JMSException, InterruptedException {
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("tcp://192.168.56.102:61616");
        Connection connection = cf.createConnection();
        connection.start();
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("EM_TRADE.Q");
        MessageConsumer receiver = session.createConsumer(queue);

        TextMessage msg1 = (TextMessage) receiver.receive(); // is a blocking wait
        System.out.println(msg1.getText());
        Thread.sleep(6000);

        TextMessage msg2 = (TextMessage) receiver.receive(); // is a blocking wait
        System.out.println(msg2.getText());
        Thread.sleep(6000);

        session.commit();// if not commited the message will be persistent on the broker
        connection.close();
    }
}
