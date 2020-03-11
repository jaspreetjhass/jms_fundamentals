package com.example.demo;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class CustomMessageListener {

	@JmsListener(destination = "${queue.queue/request}")
	public void onMessage(Message message) {
		try {
			System.out.println("message received : "+message.getBody(String.class));
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
