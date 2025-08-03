package guru.springframework.sfgjms.sender;

import guru.springframework.sfgjms.config.JmsProperties;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsClient;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SyncReceiver {
    private final JmsClient jmsClient;
    private final JmsProperties jmsProperties;

    public Optional<HelloWorldMessage> pullMessage() {
        return jmsClient.destination(jmsProperties.sendTo()).receive(HelloWorldMessage.class);
    }
}
