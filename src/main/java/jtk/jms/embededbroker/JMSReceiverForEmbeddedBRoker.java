package jtk.jms.embededbroker;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by jubin on 7/1/2017.
 */
public class JMSReceiverForEmbeddedBRoker {
    public static void main(String[] args) throws Exception
    {
        Connection connection=null;
        try {
            ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61888");
            connection = cf.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("EM_EMBEDDED_TRADE.Q");
            System.out.println("listening...");
            MessageConsumer reciever = session.createConsumer(queue);
            for (int i = 0; i < 10; i++) {
                TextMessage msg = (TextMessage) reciever.receive();
                System.out.println(msg.getText());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connection.close();
        }

    }
}
