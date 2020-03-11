package org.jp.spring.p2p.synchronous.jms;

import javax.jms.JMSException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;

public class AnnotationExecutor {

	public static void main(String[] args) throws JmsException, JMSException, InterruptedException {

		try (AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				SpringConfig.class);) {
			JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
			jmsTemplate.send(s -> s.createTextMessage("hello sir ji"));
			System.out.println("message sent");
		}
	}
}
