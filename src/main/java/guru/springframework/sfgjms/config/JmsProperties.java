package guru.springframework.sfgjms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

import static guru.springframework.sfgjms.config.JmsConfig.MY_QUEUE;
import static guru.springframework.sfgjms.config.JmsConfig.MY_SEND_RCV_QUEUE;

@ConfigurationProperties("jms.queue")
public record JmsProperties(
        @DefaultValue(MY_QUEUE) String sendTo,
        @DefaultValue(MY_SEND_RCV_QUEUE) String replyTo
) {
}
