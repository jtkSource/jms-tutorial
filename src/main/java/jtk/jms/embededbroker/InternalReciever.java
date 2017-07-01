package jtk.jms.embededbroker;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by jubin on 7/1/2017.
 */
public class InternalReciever implements MessageListener {
    private final int id;

    public InternalReciever(int id) {
        this.id = id;
        try {
            ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("vm://embedded2");
            Connection connection = cf.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE); // if transaction is true the acknowledgement mode is ignored
            Queue queue = session.createQueue("EM_EMBEDDED_TRADE.Q");// if the  queue isn't there, it will be created dynamically
            MessageConsumer consumer = session.createConsumer(queue);
            consumer.setMessageListener(this);
            System.out.println("Receiver (" + this.id + "): waiting for messages...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(Message message) {
        try {
            TextMessage msg = (TextMessage) message;
            Thread.sleep(1000);
            System.out.println("Trade received at (" + this.id + "):" + msg.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
