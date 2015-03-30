package com.hoserdude.messaging.service;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class MessageConfig {
	public static final String MY_EXCHANGE = "my-exchange";
	public static final String ROUTING_KEY_WORK = "do.work";

	/**
	 * Used below for declaring an anonymous queue (one that is auto-generated and deleted when the client dies)
	 */
	@Autowired
	private AmqpAdmin amqpAdmin;

	/**
	 * Template for sending messages to any Queue
	 * @param connectionFactory
	 * @return
	 */
	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setExchange(MY_EXCHANGE);
		return template;
	}

	/**
	 * Anonymous queue for listening to the Exchange broadcasts.  Blows up in Java 8 with connection error.
	 * @return
	 */
	@Bean()
	public Queue clientQueue() {
		return new AnonymousQueue();
	}

	/**
	 * Reference (or creation if needed) of the Fanout Exchange used to listen to completed work events.
	 * @return
	 */
	@Bean
	public FanoutExchange exchange() {
		return new FanoutExchange(MY_EXCHANGE);
	}

	/**
	 * Bind anon queue to fanout exchange.
	 * @param exchange
	 * @return
	 */
	@Bean()
	public Binding binding(FanoutExchange exchange) {
		return BindingBuilder.bind(clientQueue()).to(exchange);
	}

	/**
	 * Config for listeners
	 * @param connectionFactory
	 * @return
	 */
	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setConcurrentConsumers(3);
		factory.setMaxConcurrentConsumers(10);
		return factory;
	}
}
