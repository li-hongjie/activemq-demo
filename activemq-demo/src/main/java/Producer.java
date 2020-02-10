import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Producer {

    public static void main(String[] args) {
        ActiveMQConnectionFactory connectionFactory;
        Connection connection = null;
        Session session = null;

        try {

            connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://192.168.43.184:61616");

            connection = connectionFactory.createConnection();
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue("queue1");

            MessageProducer producer = session.createProducer(destination);

            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            String text = "hello world";
            TextMessage message = session.createTextMessage(text);

            producer.send(message);

        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
