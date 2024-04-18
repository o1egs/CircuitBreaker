package ru.shtyrev.ClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import ru.shtyrev.ClientService.services.CircuitBreaker;

import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.SECONDS;

@SpringBootApplication
public class ClientServiceApplication implements CommandLineRunner {
    @Autowired
    CircuitBreaker circuitBreaker;

    public static void main(String[] args) {
        SpringApplication.run(ClientServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        int request = circuitBreaker.handleRequest("http://localhost:8082/calculator/div?x1=1&x2=0").get();
        System.out.println(request);
    }
}
