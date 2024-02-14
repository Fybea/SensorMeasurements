package com.rest.project.SpringRestProject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestServiceTest {

    @Test
    public void testRestServiceWithWebClient() {
        Random random = new Random();

        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(codecs -> codecs
                                .defaultCodecs()
                                .maxInMemorySize(500 * 1024))
                        .build())
                .build();

        Map<String, Object> sensors = new HashMap<>();
        sensors.put("name", "sensor");


        Flux.range(1, 10)
                .flatMap(i -> {
                    Map<String, Object> requestBody = new HashMap<>();
                    requestBody.put("temperature", random.nextInt(50));
                    requestBody.put("raining", random.nextBoolean());
                    requestBody.put("sensor", sensors);

                    return webClient.post()
                            .uri("/measurements/add")
                            .body(BodyInserters.fromValue(requestBody))
                            .retrieve()
                            .bodyToMono(String.class);
                })
                .subscribe(response -> System.out.println("Response: " + response));

        webClient.get()
                .uri("/measurements/rainyDaysCount")
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(response -> System.out.println("Response: " + response));

        webClient.get()
                .uri("/measurements")
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(response -> System.out.println("Response: " + response));

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
