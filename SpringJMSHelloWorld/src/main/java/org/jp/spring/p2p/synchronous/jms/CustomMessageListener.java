package org.jp.spring.p2p.synchronous.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

public class CustomMessageListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		try {
			System.out.println("message received : "+message.getBody(String.class));
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
