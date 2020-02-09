import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Consumer {

    public static void main(String[] args) {

        ActiveMQConnectionFactory connectionFactory;
        Connection connection = null;
        Session session = null;
        MessageConsumer consumer = null;

        try {
            connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.43.184:61616");
            connection = connectionFactory.createConnection();
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue("queue1");

            consumer = session.createConsumer(destination);

            Message message = consumer.receive(1000);

            if(message instanceof TextMessage) {
                System.out.println("收到文本消息：" + ((TextMessage) message).getText());
            } else {
                System.out.println(message);
            }

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
