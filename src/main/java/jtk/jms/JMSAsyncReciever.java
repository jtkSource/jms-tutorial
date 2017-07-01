package jtk.jms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by jubin on 6/30/2017.
 */
public class JMSAsyncReciever implements MessageListener{

    public JMSAsyncReciever(){
        try{
            ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("tcp://192.168.56.102:61616");
            Connection connection = cf.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("EM_TRADE.Q");
            MessageConsumer reciever = session.createConsumer(queue);
            reciever.setMessageListener(this);
            System.out.println("Waiting for messages...");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(Message message) {
        TextMessage msg = (TextMessage) message;
        try {
            System.out.println(msg.getText());
            System.out.println("Trader name: " + msg.getStringProperty("TraderName"));
            displayHeaders(msg);
            System.out.println("-----------------------");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


    public static void displayHeaders(Message msg) throws JMSException {
        System.out.println("JMS Message Id: " + msg.getJMSMessageID());
        System.out.println("JMS DeliveryMode: " + msg.getJMSDeliveryMode());
        System.out.println("JMS Expiration: " + msg.getJMSExpiration());
        System.out.println("JMSPriority: " + msg.getJMSPriority());
    }

    public static void main(String[] args) {
        new Thread(JMSAsyncReciever::new).start();
    }
}
