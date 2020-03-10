package org.jp.p2p.synchronous.jms1.x;

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

import org.jp.p2p.synchronous.jms1.x.listeners.TaskCompletionListener;
import org.jp.p2p.synchronous.jms1.x.models.Patient;

public class ObjectMessageProducer {

	public static void main(String[] args) throws NamingException, JMSException {

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
	
		messageProducer.send(objectMessage1, DeliveryMode.PERSISTENT, 6, 1000, new TaskCompletionListener());
		messageProducer.send(objectMessage2, new TaskCompletionListener());

		MessageConsumer messageConsumer = session.createConsumer(queue);
		connection.start();

		ObjectMessage output1 = (ObjectMessage) messageConsumer.receive(2000);
		ObjectMessage output2 = (ObjectMessage) messageConsumer.receive(2000);

		System.out.println("message received " + output1.getObject());
		System.out.println("message received " + output2.getObject());

	}
}
