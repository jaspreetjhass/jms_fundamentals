package org.jp.spring.p2p.synchronous.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.jms.annotation.JmsListener;

public class CustomMessageListener {

	@JmsListener(destination = "${queue.queue/request}",containerFactory = "myFactory")
	public void onMessage(Message message) {
		try {
			System.out.println("message received : "+message.getBody(String.class));
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
