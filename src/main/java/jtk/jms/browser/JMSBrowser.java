package jtk.jms.browser;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Enumeration;

/**
 * Created by jubin on 7/1/2017.
 */
public class JMSBrowser {

    public static void main(String[] args) throws Exception {
        Context ctx = new InitialContext();// picks the naming properties from the jndi.properties file
        Connection connection = ((ConnectionFactory) ctx.lookup("ConnectionFactory")).createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = (Queue) ctx.lookup("EM_TRADE.Q");
        QueueBrowser browser = session.createBrowser(queue);
        Enumeration<?> e = browser.getEnumeration();
        int msgCount = 0;
        while (e.hasMoreElements()){
            e.nextElement();
            msgCount++;
        }
        System.out.println("There are "+ msgCount + " messages in the queue");
        browser.close(); // need to close a browser before creating another one
        connection.close();
    }

}
