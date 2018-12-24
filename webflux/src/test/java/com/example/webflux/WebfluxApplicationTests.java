package com.example.webflux;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebfluxApplicationTests {

    @Test
    public void webClientTest1() throws InterruptedException {
        WebClient webClient = WebClient.create("http://localhost:8080");
        Mono<String> resp = webClient
                .get().uri("/user")
                .retrieve()
                .bodyToMono(String.class);
        resp.subscribe(System.out::println);
        TimeUnit.SECONDS.sleep(1);
    }
}
