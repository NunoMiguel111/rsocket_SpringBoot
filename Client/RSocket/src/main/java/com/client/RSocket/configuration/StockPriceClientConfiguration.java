package com.client.RSocket.configuration;

import com.client.RSocket.data.StockQuote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;

@Slf4j
@Configuration
public class StockPriceClientConfiguration {

    @Value("${rsocket.server.host}") // Add a configuration property for the server host
    private String serverHost;

    @Bean
    public ApplicationRunner Stocksender(RSocketRequester.Builder requesterBuilder){
        return args -> {

            RSocketRequester tcp = requesterBuilder.tcp(serverHost, 7000);

            String quote = "AMZ";

            tcp.route("stock/{symbol}", quote)
                    .retrieveFlux(StockQuote.class)
                    .doOnNext(stockQuote -> {
                        log.info("Price of {} : {} at {}",
                                stockQuote.getSymbol(),
                                stockQuote.getPrice(),
                                stockQuote.getTimestamp());
                    }).blockLast();
        };
    }

}
