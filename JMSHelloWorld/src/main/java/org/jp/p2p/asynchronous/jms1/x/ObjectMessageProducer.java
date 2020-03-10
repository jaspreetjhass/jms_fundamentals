package org.jp.p2p.asynchronous.jms1.x;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jp.p2p.asynchronous.jms1.x.listeners.CustomMessageListener;
import org.jp.p2p.synchronous.jms1.x.models.Patient;

public class ObjectMessageProducer {

	public static void main(String[] args) throws NamingException, JMSException, InterruptedException {
		
		InitialContext context = new InitialContext();
		ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Queue queue = (Queue) context.lookup("queue/request");

		Connection connection = connectionFactory.createConnection();
		Session session = connection.createSession();

		Patient patient1 = Patient.builder().id(123).name("jaspreet").insuranceProvider("healthnet").copay(20.0)
				.amountPayable(330.0).build();
		ObjectMessage objectMessage1 = session.createObjectMessage(patient1);

		Patient patient2 = Patient.builder().id(123).name("harry").insuranceProvider("wellington").copay(40.0)
				.amountPayable(140.0).build();
		ObjectMessage objectMessage2 = session.createObjectMessage(patient2);

		MessageProducer messageProducer = session.createProducer(queue);

		messageProducer.send(objectMessage1, DeliveryMode.PERSISTENT, 3, 1000);
		System.out.println("message sent ");
		messageProducer.send(objectMessage2);
		System.out.println("message sent ");
		
		MessageConsumer messageConsumer = session.createConsumer(queue);
		connection.start();

		messageConsumer.setMessageListener(new CustomMessageListener());

		Thread.sleep(5000);
	}
}
