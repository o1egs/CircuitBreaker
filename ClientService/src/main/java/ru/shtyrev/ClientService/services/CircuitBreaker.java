package ru.shtyrev.ClientService.services;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.SECONDS;
import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;

@Service
@RequiredArgsConstructor
public class CircuitBreaker {
    private LocalDateTime lastFailureTime;
    @Value("${circuitbreaker.interval}")
    private Long intervalSeconds;
    private final RestTemplate restTemplate;
    private final StringRedisTemplate redisTemplate;

    public CompletableFuture<Integer> handleRequest(String url) throws ExecutionException, InterruptedException {
        if (getState().equals("OPEN")) {
            if (lastFailureTime.until(now(), SECONDS) >= intervalSeconds) {
                close();
            } else {
                throw new RuntimeException("Circuit breaker opened");
            }
        }

        CompletableFuture<Integer> result = new CompletableFuture<>();

        try {
            Integer forObject = restTemplate.getForObject(url, Integer.class);
            result.complete(forObject);
            return result;
        } catch (Exception e) {
            newSingleThreadScheduledExecutor().schedule(() -> {
                try {
                    Integer forObject = restTemplate.getForObject(url, Integer.class);
                    result.complete(forObject);
                } catch (Exception e2) {
                    open();
                    throw new RuntimeException("Circuit breaker opened");
                }
                return result;
            }, intervalSeconds, TimeUnit.SECONDS).get();
        }
        return result;
    }

    @PostConstruct
    public void init() {
        redisTemplate.opsForValue().setIfAbsent("STATE", "CLOSED");
    }

    private void open() {
        redisTemplate.opsForValue().set("STATE", "OPEN");
        lastFailureTime = now();
    }

    private void close() {
        redisTemplate.opsForValue().set("STATE", "CLOSE");
        lastFailureTime = null;
    }

    private String getState() {
        return redisTemplate.opsForValue().get("STATE");
    }
}
