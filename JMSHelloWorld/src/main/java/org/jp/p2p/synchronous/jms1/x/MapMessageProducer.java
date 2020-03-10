package org.jp.p2p.synchronous.jms1.x;

import java.util.Objects;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MapMessageProducer {

	public static void main(String[] args) {

		InitialContext context = null;
		Connection connection = null;
		try {
			context = new InitialContext();
			ConnectionFactory cf = (ConnectionFactory) context.lookup("ConnectionFactory");
			connection = cf.createConnection();
			Session session = connection.createSession();
			Queue queue = (Queue) context.lookup("queue/request");
			MapMessage message = session.createMapMessage();
			message.setString("message", "I am the owner of Map message app");
			MessageProducer producer = session.createProducer(queue);
			producer.send(message);
			System.out.println("message sent");

			MessageConsumer consumer = session.createConsumer(queue);
			connection.start();
			MapMessage output = (MapMessage) consumer.receive(2000);
			System.out.println("message received : " + output.getString("message"));
		} catch (NamingException | JMSException e) {
			e.printStackTrace();
		} finally {
			if (Objects.nonNull(context)) {
				try {
					context.close();
				} catch (NamingException e) {
					e.printStackTrace();
				}
			}
			if (Objects.nonNull(connection)) {
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}

		}

	}
}
