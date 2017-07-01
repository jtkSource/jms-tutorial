package jtk.jms.pubsub;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.UUID;

/**
 * Created by jubin on 7/1/2017.
 */
public class JMSDurableSubscriber implements MessageListener {

    public JMSDurableSubscriber() {
        try {
            Context ctx = new InitialContext();// picksthe naming properties from the jndi.properties file
            TopicConnection connection = ((TopicConnectionFactory) ctx.lookup("TopicConnectionFactory")).createTopicConnection();
            connection.setClientID("client:123"); // have to set the clientId on the connection

            connection.start();
            TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic("EM_TRADE.T");
            TopicSubscriber subscriber = session.createDurableSubscriber(topic, "sub:122");// creates durable subscriber
            subscriber.setMessageListener(this);
            System.out.println("Waiting for messages...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onMessage(Message message) {
        try {
            TextMessage msg = (TextMessage) message;
            System.out.println("received: " + msg.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new Thread(JMSDurableSubscriber::new).start();
    }
}
