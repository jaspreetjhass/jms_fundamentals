package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.core.JmsTemplate;

@SpringBootApplication
//@EnableJms
@PropertySource(value = {"jndi.properties"})
public class SpringBootJmsHelloWorldApplication {

	public static void main(String[] args){
		ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringBootJmsHelloWorldApplication.class, args);
		JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
		jmsTemplate.send(s-> s.createTextMessage("hello sir ji"));
		System.out.println("message sent");
	}

}
