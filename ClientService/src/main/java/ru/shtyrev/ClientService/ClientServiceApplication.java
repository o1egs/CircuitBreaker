package ru.shtyrev.ClientService;

import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.shtyrev.ClientService.cb.CircuitBreakerService;

import java.util.List;
import java.util.Random;

import static ru.shtyrev.ClientService.cb.RequestType.*;

@SpringBootApplication
public class ClientServiceApplication implements CommandLineRunner {
    @Autowired
    private CircuitBreakerService service;

    public static void main(String[] args) {
        SpringApplication.run(ClientServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Random random = new Random();
        for (long i = 0; i < 10000; i++) {
            Thread.sleep(4000);
            int x1 = random.nextInt();
            int x2 = random.nextInt();
            x2 = x2 % 2 == 0 ? x2 : 0;
            System.out.println(x1 + " and " + x2 + " operations:");
            try {
                System.out.println("DIV: " + service.request(DIV, x1, x2));
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("SUM: " + service.request(SUM, x1, x2));
            System.out.println("MINUS: " + service.request(MINUS, x1, x2));
            System.out.println("MUL: " + service.request(MUL, x1, x2));
            System.out.println();
        }
    }
}
