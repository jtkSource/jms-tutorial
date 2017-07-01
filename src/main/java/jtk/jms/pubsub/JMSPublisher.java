package jtk.jms.pubsub;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.text.DecimalFormat;

/**
 * Created by jubin on 7/1/2017.
 */
public class JMSPublisher {
    public static void main(String[] args) throws NamingException, JMSException {
        Context ctx = new InitialContext();// picksthe naming properties from the jndi.properties file
        Connection connection = ((ConnectionFactory) ctx.lookup("ConnectionFactory")).createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("EM_TRADE.T");
        MessageProducer publisher = session.createProducer(topic);

        DecimalFormat df= new DecimalFormat("##.00");
        String price = df.format(95.0 + Math.random());
        TextMessage msg = session.createTextMessage("APPL " + price);
        publisher.send(msg);
        System.out.println("Message sent: APPL " + price);
        connection.close();
    }
}
