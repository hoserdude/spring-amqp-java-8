
package com.hoserdude.messaging.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Sends a message to a very slow service every 2 seconds.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

	final static Logger logger = LoggerFactory.getLogger(Application.class);

	@Autowired
	private RabbitTemplate rabbitTemplate;

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args);
    }

    private final AtomicInteger counter = new AtomicInteger();

    @Override
    public void run(String... args) throws Exception {
		logger.info("Starting work request loop");
		while (true) {
			String requestId = String.valueOf(counter.incrementAndGet());
			Thread.sleep(2000);
			logger.info("Sending message to for requestId={}", requestId);
			Message<String> message = MessageBuilder
					.withPayload((requestId))
					.setHeader("user", "user1")
					.build();
			rabbitTemplate.convertAndSend(MessageConfig.ROUTING_KEY_WORK, message);
		}
    }
}
