package jtk.jms.reqreply.tmpqueue;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Created by jubin on 6/30/2017.
 */
public class JMSSenderUsingQueueRequestor {
    public static void main(String[] args) throws NamingException, JMSException {
        Context ctx = new InitialContext();// picksthe naming properties from the jndi.properties file
        QueueConnection connection = ((QueueConnectionFactory) ctx.lookup("QueueConnectionFactory")).createQueueConnection();
        connection.start();
        QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue req = (Queue) ctx.lookup("EM_TRADE.Q");
        TextMessage msg = session.createTextMessage("BUY APPL 1000 SHARES");

        // combines Messageproducer and replyTo to a Single encapsulation
        QueueRequestor requestor = new QueueRequestor(session,req);
        TextMessage msgResp = (TextMessage)requestor.request(msg); // the request method will create a temporary queue
        // it also sets the consumer to the temporary queue to return the response message
        System.out.println("conf = " + msgResp.getText());

        connection.close();

    }
}
