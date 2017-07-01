package jtk.jms.reqreply;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.soap.Text;

/**
 * Created by jubin on 6/30/2017.
 */
public class JMSSender {
    public static void main(String[] args) throws NamingException, JMSException {
        Context ctx = new InitialContext();// picksthe naming properties from the jndi.properties file
        Connection connection = ((ConnectionFactory) ctx.lookup("ConnectionFactory")).createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue req = (Queue) ctx.lookup("EM_TRADE.Q");
        Queue  resp = session.createQueue("EM_TRADE_RESP.Q");
        TextMessage msg = session.createTextMessage("BUY APPL 1000 SHARES");
        msg.setJMSReplyTo(resp);//sets the reply queue on which the response is expected.
        MessageProducer sender = session.createProducer(req);
        sender.send(msg);

        System.out.println("Message send...");

        String filter = "JMSCorrelationID = '" + msg.getJMSMessageID() + "'";
        MessageConsumer receiver = session.createConsumer(resp,filter); // creates a consumer listening toa specific corelationId
        TextMessage respMsg = (TextMessage)receiver.receive();
        System.out.println("Confirmation = " + respMsg.getText());

        connection.close();

    }
}
