package com.example.demo;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@PropertySource(value = { "jndi.properties" })
public class SpringConfig2 {


	@Bean
	public ActiveMQQueue mqQueue(@Value("${queue.queue/request}") String queueName) {
		ActiveMQQueue mqQueue = new ActiveMQQueue();
		mqQueue.setAddress(queueName);
		return mqQueue;
	}

	@Bean
	public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory, Queue destination) {
		JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
		jmsTemplate.setDefaultDestination(destination);
		return jmsTemplate;
	}

	/*
	 * @Bean public JmsListenerContainerFactory<?> myFactory(ConnectionFactory
	 * connectionFactory) { DefaultJmsListenerContainerFactory factory = new
	 * DefaultJmsListenerContainerFactory();
	 * factory.setConnectionFactory(connectionFactory); return factory; }
	 */
}
