package com.example.messagingjms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import com.example.messagingjms.model.Email;

import jakarta.jms.ConnectionFactory;

@SpringBootApplication
@EnableJms
public class MessagingJmsApplication {

	
	@Bean
	public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,DefaultJmsListenerContainerFactoryConfigurer configurer) {
	
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		
		// This provides all auto-configured defaults to this factory, including the message converter
		configurer.configure(factory, connectionFactory);
		
		// You could still override some settings if necessary.
		return factory;
	}

	@Bean // Serialize message content to json using TextMessage
 public MessageConverter jacksonJmsMessageConverter() {
	MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
	converter.setTargetType(MessageType.TEXT);
	converter.setTypeIdPropertyName("_type");
	return converter;
 }

 public static void main(String[] args) {
		System.out.println(" *** Demarrage : MessagingJmsApplication 1");
		ConfigurableApplicationContext context = SpringApplication.run(MessagingJmsApplication.class, args);
		
		System.out.println(" *** MessagingJmsApplication 2 : apres appel ConfigurableApplicationContext ");
		
		JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
		System.out.println(" *** MessagingJmsApplication 3 : apres appel context.getBean(JmsTemplate.class) ");
		// Send a message with a POJO - the template reuse the message converter
		System.out.println(" *** MessagingJmsApplication 3 : Sending an email message.");
		jmsTemplate.convertAndSend("mailbox", new Email("info@example.com", "Hello from Spring JMS"));
		
		System.out.println(" *** MessagingJmsApplication 4 : jmsTemplate.convertAndSend.");
	}

}
