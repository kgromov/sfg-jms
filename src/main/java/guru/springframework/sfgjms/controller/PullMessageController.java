package guru.springframework.sfgjms.controller;

import guru.springframework.sfgjms.model.HelloWorldMessage;
import guru.springframework.sfgjms.sender.SyncReceiver;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PullMessageController {
    private final SyncReceiver syncReceiver;

    @GetMapping("pull")
    public HelloWorldMessage getMessage() {
        return syncReceiver.pullMessage();
    }
}
