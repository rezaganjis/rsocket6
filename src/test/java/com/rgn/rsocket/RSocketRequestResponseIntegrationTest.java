package com.rgn.rsocket;

import com.rgn.rsocket.requester.MessageClient;
import com.rgn.rsocket.responder.RSocketApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.service.RSocketServiceProxyFactory;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = RSocketApplication.class)
public class RSocketRequestResponseIntegrationTest {

    MessageClient client;

    public RSocketRequestResponseIntegrationTest(){
        RSocketRequester.Builder requesterBuilder= RSocketRequester.builder();
        RSocketRequester requester = requesterBuilder.tcp("localhost", 7000);
        RSocketServiceProxyFactory factory = RSocketServiceProxyFactory.builder(requester).build();
        client=factory.createClient(MessageClient.class);
    }

    @Test
    public void whenSendingStream_thenReceiveTheSameStream() {
        String message="test message";
        assertEquals(message,client.sendMessage(Mono.just(message)).block());
    }

}
