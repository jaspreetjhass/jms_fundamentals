package org.jp.spring.p2p.synchronous.jms;

import javax.jms.ConnectionFactory;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;

@Configuration
@PropertySource(value = { "jndi.properties" })
public class SpringConfig {

	@Bean
	public ActiveMQConnectionFactory connectionFactory(
			@Value("${connectionFactory.ConnectionFactory}") String brokerURL) {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
		return connectionFactory;
	}

	@Bean
	public ActiveMQQueue mqQueue(@Value("${queue.queue/request}") String queueName) {
		ActiveMQQueue mqQueue = new ActiveMQQueue();
		mqQueue.setAddress(queueName);
		return mqQueue;
	}

	@Bean
	public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory, ActiveMQQueue destination) {
		JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
		jmsTemplate.setDefaultDestination(destination);
		return jmsTemplate;
	}

	@Bean
	public CustomMessageListener customMessageListener() {
		return new CustomMessageListener();
	}

	@Bean
	public MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory,
			CustomMessageListener customMessageListener, ActiveMQQueue destination) {
		DefaultMessageListenerContainer defaultMessageListenerContainer = new DefaultMessageListenerContainer();
		defaultMessageListenerContainer.setConnectionFactory(connectionFactory);
		defaultMessageListenerContainer.setMessageListener(customMessageListener);
		defaultMessageListenerContainer.setDestination(destination);
		return defaultMessageListenerContainer;
	}
}
