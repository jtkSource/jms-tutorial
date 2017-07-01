package jtk.jms.reqreply.tmpqueue;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Created by jubin on 6/30/2017.
 */
public class JMSReply {
    public static void main(String[] args) throws JMSException, NamingException, InterruptedException {
        Context ctx = new InitialContext();// picksthe naming properties from the jndi.properties file
        Connection connection = ((ConnectionFactory) ctx.lookup("ConnectionFactory")).createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue req = (Queue) ctx.lookup("EM_TRADE.Q");
        MessageConsumer receiver =session.createConsumer(req);
        TextMessage msg = (TextMessage)receiver.receive();
        System.out.println("processing trade: " + msg.getText());
        Thread.sleep(4000);
        String confirmation = "EQ-12345";
        System.out.println("trade confirmation: " + confirmation);

        TextMessage responseMessage = session.createTextMessage(confirmation);
        //responseMessage.setJMSCorrelationID(msg.getJMSMessageID()); // no need to set the correlation id
        System.out.println("temporary queue -> " + msg.getJMSReplyTo());
        MessageProducer producer = session.createProducer(msg.getJMSReplyTo());// sending the response to the replyto set on the
        producer.send(responseMessage);
        connection.close();

    }
}
