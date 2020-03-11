package org.jp.spring.p2p.synchronous.jms;

import javax.jms.JMSException;

import org.jp.spring.p2p.synchronous.jms.models.Patient;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

public class ObjectMessageProducer {

	public static void main(String[] args) throws JMSException {
		try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"application-context-jndi.xml");) {

			JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
			// case-1 string message
			// MessageCreator messageCreator = (session) -> session.createTextMessage("I am
			// the owner of this app");
			// jmsTemplate.send(messageCreator);

			// case-2 object message
			// Patient patient =
			// Patient.builder().id(122).name("jaspreet").insuranceProvider("healthnet").copay(89.0).amountPayable(78.90).build();
			// MessageCreator messageCreator = (session) ->
			// session.createObjectMessage(patient);
			// jmsTemplate.send(messageCreator);

			// case -3 map message
			/*
			 * MessageCreator messageCreator = (session) -> { MapMessage mapMessage =
			 * session.createMapMessage(); mapMessage.setString("name", "jaspreet");
			 * mapMessage.setDouble("copay", 89.0); return mapMessage; };
			 * jmsTemplate.send(messageCreator);
			 */

			// case - 4 java map
			/*
			 * Map<String, Object> map = new HashMap<>(); map.put("name", "jaspreet");
			 * map.put("copay", 10.89);
			 * 
			 * jmsTemplate.convertAndSend(map);
			 */

			// case - 5 object without serialization, in this case message converter is
			// required
			Patient patient = Patient.builder().id(122).name("jaspreet").insuranceProvider("healthnet").copay(89.0)
					.amountPayable(78.90).build();
			jmsTemplate.convertAndSend(patient);
			System.out.println("message sent");
			System.out.println("message received : " + jmsTemplate.receiveAndConvert());
		}
	}
}
