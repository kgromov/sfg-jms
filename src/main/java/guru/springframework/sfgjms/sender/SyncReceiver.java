package guru.springframework.sfgjms.sender;

import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SyncReceiver {
    private final JmsTemplate jmsTemplate;

    public HelloWorldMessage pullMessage() {
        return (HelloWorldMessage) jmsTemplate.receiveAndConvert();
    }
}
