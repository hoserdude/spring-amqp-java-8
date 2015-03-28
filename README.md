# spring-amqp-java-8
Example project to work through running on Java 8

# Requirements
* RabbitMQ 3.4.2
* Java 8
* Java 7
* Maven 3

# Running
Ensure you are running Java 8 (`java -version`)
* `mvn install`
* `java -jar target/spring-amqp-java-8-0.1.0.jar`

You should see among other things, the following:

```
Caused by: org.springframework.beans.BeanInstantiationException: Failed to instantiate [org.springframework.amqp.core.Queue]: Factory method 'clientQueue' threw exception; nested exception is org.springframework.amqp.AmqpConnectException: java.net.ConnectException: Connection refused
	at org.springframework.beans.factory.support.SimpleInstantiationStrategy.instantiate(SimpleInstantiationStrategy.java:189)
	at org.springframework.beans.factory.support.ConstructorResolver.instantiateUsingFactoryMethod(ConstructorResolver.java:588)
	... 42 more
Caused by: org.springframework.amqp.AmqpConnectException: java.net.ConnectException: Connection refused
```

# Making it work
* Ensure you are running Java 7 (either switch your symlink or use the full path to the java executable)
* `java -jar target/spring-amqp-java-8-0.1.0.jar`

Now you snould see logs with the following:

```
2015-03-27 23:56:37,093; thread=[main]; LogLevel=INFO; category=com.hoserdude.messaging.service.Application; msg=Starting work request loop; 
2015-03-27 23:56:39,098; thread=[main]; LogLevel=INFO; category=com.hoserdude.messaging.service.Application; msg=Sending message to for requestId=1; 
2015-03-27 23:56:39,119; thread=[SimpleAsyncTaskExecutor-1]; LogLevel=INFO; category=com.hoserdude.messaging.service.Receiver; msg=Received Response for requestId=1; 
2015-03-27 23:56:41,118; thread=[main]; LogLevel=INFO; category=com.hoserdude.messaging.service.Application; msg=Sending message to for requestId=2; 
```



