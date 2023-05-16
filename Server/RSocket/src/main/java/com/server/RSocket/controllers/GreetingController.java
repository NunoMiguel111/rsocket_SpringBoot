package com.server.RSocket.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
@Slf4j
public class GreetingController {
    @MessageMapping("greeting/{name}")
    public Mono<String> handleGreeting (@DestinationVariable("name") String name,Mono<String> greetingMono) {

        log.info("Received greeting from client {}: {}", name, greetingMono);

        return greetingMono
                .doOnNext(greeting ->
                        log.info("Received a greeting from {}: {}",name, greeting))
                .map(greeting -> "Hello back to you, " + name);
    }
}