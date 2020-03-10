package org.jp.p2p.synchronous.jms1.x;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jp.p2p.synchronous.jms1.x.listeners.TaskCompletionListener;

public class StreamMessageProducer {

	public static void main(String[] args) throws NamingException, JMSException {
		InitialContext context = new InitialContext();
		ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Queue queue = (Queue) context.lookup("queue/request");

		Connection connection = connectionFactory.createConnection();
		Session session = connection.createSession();

		StreamMessage streamMessage1 = session.createStreamMessage();
		streamMessage1.writeString("stream1 -x- message1");
		streamMessage1.writeString("stream1 -x- message2");
		streamMessage1.writeString("stream1 -x- message3");
		
		StreamMessage streamMessage2 = session.createStreamMessage();
		streamMessage2.writeString("stream2 -xx- message1");
		streamMessage2.writeString("stream2 -xx- message2");
		streamMessage2.writeString("stream2 -xx- message3");
		
		MessageProducer messageProducer = session.createProducer(queue);
	
		messageProducer.send(streamMessage1, DeliveryMode.PERSISTENT, 3, 1000, new TaskCompletionListener());
		messageProducer.send(streamMessage2, new TaskCompletionListener());

		MessageConsumer messageConsumer1 = session.createConsumer(queue);
		MessageConsumer messageConsumer2 = session.createConsumer(queue);
		connection.start();

		StreamMessage output1 = (StreamMessage) messageConsumer1.receive(2000);
		StreamMessage output2 = (StreamMessage) messageConsumer2.receive(2000);

		System.out.println("message received " + output1.readString());
		System.out.println("message received " + output1.readString());
		
		System.out.println("message received " + output2.readString());
	}
}
