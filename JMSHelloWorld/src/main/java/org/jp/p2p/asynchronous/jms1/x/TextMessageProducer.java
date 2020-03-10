package org.jp.p2p.asynchronous.jms1.x;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TextMessageProducer {

	public static void main(String[] args) throws NamingException, JMSException, InterruptedException {
		InitialContext context =  new InitialContext();
		
		ConnectionFactory connectionFactory = (ConnectionFactory)context.lookup("ConnectionFactory");
		Queue queue = (Queue)context.lookup("queue/request");
		
		Connection connection = connectionFactory.createConnection();
		Session session = connection.createSession();
		
		TextMessage textMessage1 = session.createTextMessage("first message");
		TextMessage textMessage2 = session.createTextMessage("second message");
		
		MessageProducer messageProducer = session.createProducer(queue);
		messageProducer.send(textMessage1);
		messageProducer.send(textMessage2);
		
		MessageConsumer messageConsumer = session.createConsumer(queue);
		connection.start();
		MessageListener lambdaMessageListener = (message) -> {
			try {
				System.out.println("message received : "+message.getBody(String.class));
			} catch (JMSException e) {
				e.printStackTrace();
			}
		};
		
		messageConsumer.setMessageListener(lambdaMessageListener);
		
		Thread.sleep(4000);
	}
}
