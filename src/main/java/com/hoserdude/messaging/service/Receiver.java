
package com.hoserdude.messaging.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

	final static Logger logger = LoggerFactory.getLogger(Receiver.class);

	/**
	 * Note the reference to the containerFactory and the existing anonymous queue that is defined in config.
	 * @param message
	 * @throws Exception
	 */
	@RabbitListener(containerFactory = "rabbitListenerContainerFactory", queues = "#{@clientQueue}")
	public void onMessage(Message<String> message) throws Exception {
		logger.info("Received Response for requestId={}", message.getPayload());
	}
}
