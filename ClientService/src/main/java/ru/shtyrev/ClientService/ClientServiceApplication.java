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
    @Autowired
    StringRedisTemplate redisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(ClientServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        redisTemplate.opsForValue().set("STATE", "CLOSE");
        for (int i = 0; i < 10000; i++) {
            Thread.sleep(100);
            try {
                int request = circuitBreaker.handleRequest("http://localhost:8082/calculator/div?x1=1&x2=1").get();
                System.out.println(request);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
