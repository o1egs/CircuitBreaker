package ru.shtyrev.ClientService.cb;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
public abstract class CircuitBreaker {
    private final RestTemplate restTemplate;
    private boolean isOpen;
    private LocalDateTime lastFailureTime;

    public int handleRequest(int x1, int x2) {
        try {
            if (isOpen) {
                int intervalSeconds = 10;
                if (lastFailureTime.plusSeconds(intervalSeconds).isBefore(LocalDateTime.now())) {
                    close();
                } else {
                    throw new RuntimeException("Circuit will be closed in "
                            + ChronoUnit.SECONDS.between(LocalDateTime.now(), lastFailureTime.plusSeconds(intervalSeconds)) + " seconds");
                }
            }
            return request(x1, x2);
        } catch (HttpServerErrorException ex) {
            open();
            throw new RuntimeException("Opening circuit breaker in " + lastFailureTime);
        }
    }

    private int request(int x1, int x2) {
        String operation = type().toString().toLowerCase();
        String url = "http://localhost:8089/calculator";
        String requestUrl = url + "/" + operation + "?x1=" + x1 + "&" + "x2=" + x2;
        Integer i = restTemplate.getForObject(requestUrl, Integer.class);
        assert i != null;
        return i;
    }

    public abstract RequestType type();

    private void open() {
        lastFailureTime = LocalDateTime.now();
        isOpen = true;
    }

    private void close() {
        lastFailureTime = null;
        isOpen = false;
    }
}
