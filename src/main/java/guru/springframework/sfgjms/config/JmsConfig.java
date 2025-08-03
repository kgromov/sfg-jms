package guru.springframework.sfgjms.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@EnableConfigurationProperties(JmsProperties.class)
@Configuration
public class JmsConfig {

    public static final String MY_QUEUE = "hello-world-queue";
    public static final String MY_SEND_RCV_QUEUE = "reply-back-to-me";
}
