package com.client.RSocket.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
public class RSocketClientConfiguration {

    @Value("${rsocket.server.host}") // Add a configuration property for the server host
    private String serverHost;

    @Bean
    public ApplicationRunner sender(RSocketRequester.Builder requesterBuilder) {
        log.info("Starting");
        return args -> {
            log.info("Sending message to server");
            log.info(serverHost);
            RSocketRequester tcp = requesterBuilder.tcp(serverHost, 7000);

            String name = "Leo";
            // Send Messages with RSocketRequester ...

            tcp
                    .route("greeting/{name}", name)
                    .data("Hello RSocket!")
                    .retrieveMono(String.class)
                    .block();

        };
    }
}