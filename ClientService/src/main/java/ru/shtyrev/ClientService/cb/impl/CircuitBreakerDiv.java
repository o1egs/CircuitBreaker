package ru.shtyrev.ClientService.cb.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.shtyrev.ClientService.cb.CircuitBreaker;
import ru.shtyrev.ClientService.cb.RequestType;

@Component
public class CircuitBreakerDiv extends CircuitBreaker {
    @Autowired
    public CircuitBreakerDiv(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    public RequestType type() {
        return RequestType.DIV;
    }
}
