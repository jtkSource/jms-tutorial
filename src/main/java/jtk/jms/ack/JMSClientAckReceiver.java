package jtk.jms.ack;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by jubin on 6/30/2017.
 */
public class JMSClientAckReceiver {

    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("tcp://192.168.56.102:61616");
        Connection connection = cf.createConnection();
        if(args.length==1)
            connection.setClientID("ExceptionClient");
        else connection.setClientID("FreeClient");
        connection.start();
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE); //requires explicit acking
        Queue queue = session.createQueue("EM_TRADE.Q");
        MessageConsumer receiver = session.createConsumer(queue);
        while(true){ // is a blocking wait
            TextMessage msg = (TextMessage) receiver.receive(500);
            if (msg==null)continue;
            System.out.println(msg.getText());
            Thread.sleep(4000);
            if (args.length == 1) {// will not ack messages// messages will remain in broker until connection is closed
                // if connection is closed then next client will pick it up.
                continue;
            }
            msg.acknowledge();
        }
    }
}
