package guru.springframework.sfgjms.sender;

import guru.springframework.sfgjms.config.JmsProperties;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import jakarta.jms.JMSException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.artemis.utils.RandomUtil;
import org.springframework.jms.core.JmsClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class HelloSender {

    private final JmsClient jmsClient;
    private final JmsProperties jmsProperties;

//    @Async("taskExecutor")
//    @Scheduled(fixedRate = 2000)
    public void sendMessage() {

        HelloWorldMessage message = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello World "+ RandomUtil.randomInterval(0, 100) + "!")
                .build();

        jmsClient.destination(jmsProperties.sendTo()).send(message);
        log.info("Send message: {}", message);
    }

    @Scheduled(fixedRate = 2000)
    @SneakyThrows
    public void sendAndReceiveMessage() {

        HelloWorldMessage message = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello")
                .build();

        log.info("Sending Hello");
//        var receivedMsg = jmsClient.destination(jmsProperties.replyTo()).sendAndReceive(MessageBuilder.withPayload(message).build());
        var receivedMsg = jmsClient.destination(jmsProperties.replyTo())
                .sendAndReceive(message, HelloWorldMessage.class);

        HelloWorldMessage receivedMessage = receivedMsg.orElseThrow(() -> new JMSException("No message received"));
        log.info("Received message: {}", receivedMessage);
        log.info("send.id == received.id {}", Objects.equals(receivedMessage.getId(), message.getId()));
        log.info("send.body == received.body {}", Objects.equals(receivedMessage.getMessage(), message.getMessage()));
    }
}
