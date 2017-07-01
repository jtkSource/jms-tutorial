package jtk.jms.tx;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * Created by jubin on 6/29/2017.
 */
public class JMSTxSender {

    public static void main(String[] args) throws Exception {
        Connection connection = null;
        try {


            Context ctx = new InitialContext();// picksthe naming properties from the jndi.properties file
            connection = ((ConnectionFactory) ctx.lookup("ConnectionFactory")).createConnection();
            connection.start();
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE); //ack mode is ignored
            Queue queue = (Queue) ctx.lookup("EM_TRADE.Q");
            MessageProducer sender = session.createProducer(queue);

            TextMessage msg1 = session.createTextMessage("BUY APPL 1000 SHARES");
            sender.send(msg1);
            System.out.println("Message 1 send");

            //Thread.sleep(3000);
            if (args.length == 1) throw new Exception("Dont commit transaction;");// will not send messages in this transaction

            TextMessage msg2 = session.createTextMessage("BUY IBM 2000 SHARES");
            sender.send(msg2);
            System.out.println("Message 2 send");
            session.commit();

        } finally {

            connection.close(); // closing the connection close the session

        }
    }

}
