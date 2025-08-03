package guru.springframework.sfgjms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.config.JmsProperties;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.artemis.utils.RandomUtil;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

/**
 * Created by jt on 2019-07-17.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class HelloSender {

    private final JmsTemplate jmsTemplate;
    private final JmsProperties jmsProperties;

//    @Async("taskExecutor")
//    @Scheduled(fixedRate = 2000)
    public void sendMessage() {

        HelloWorldMessage message = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello World "+ RandomUtil.randomInterval(0, 100) + "!")
                .build();

        jmsTemplate.convertAndSend(message);
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

        Message receivedMsg = jmsTemplate.sendAndReceive(jmsProperties.replyTo(), session -> {
            try {
                Message helloMessage = session.createObjectMessage(message);
                log.info("Sending Hello");
                return helloMessage;
            } catch (JMSException e) {
                throw new JMSException(e.getMessage());
            }
        });

        HelloWorldMessage receivedMessage = receivedMsg.getBody(HelloWorldMessage.class);
        log.info("Received message: {}", receivedMessage);
        log.info("send.id == received.id {}", Objects.equals(receivedMessage.getId(), message.getId()));
        log.info("send.body == received.body {}", Objects.equals(receivedMessage.getMessage(), message.getMessage()));
    }
}
