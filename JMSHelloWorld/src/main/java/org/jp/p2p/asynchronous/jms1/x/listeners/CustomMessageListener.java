package org.jp.p2p.asynchronous.jms1.x.listeners;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.jp.p2p.synchronous.jms1.x.models.Patient;

public class CustomMessageListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		try {
			System.out.println("message received : "+message.getBody(Patient.class));
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
