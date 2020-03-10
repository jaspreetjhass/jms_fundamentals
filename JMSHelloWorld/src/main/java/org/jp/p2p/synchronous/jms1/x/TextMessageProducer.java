package org.jp.p2p.synchronous.jms1.x;

import java.util.Objects;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TextMessageProducer {

	public static void main(String[] args) {

		InitialContext context = null;
		Connection connection = null;
		try {
			context = new InitialContext();
			ConnectionFactory cf = (ConnectionFactory) context.lookup("ConnectionFactory");
			connection = cf.createConnection();
			Session session = connection.createSession();
			TextMessage message = session.createTextMessage("I am the owner of Text message app");

			Queue queue = (Queue) context.lookup("queue/request");

			MessageProducer producer = session.createProducer(queue);
			producer.send(message);
			System.out.println("message sent");

			MessageConsumer createConsumer = session.createConsumer(queue);
			connection.start();
			TextMessage output = (TextMessage) createConsumer.receive(3000);

			System.out.println("message received : " + output.getText());

		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
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
