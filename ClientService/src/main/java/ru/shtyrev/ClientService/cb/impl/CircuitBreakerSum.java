package ru.shtyrev.ClientService.cb.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.shtyrev.ClientService.cb.CircuitBreaker;
import ru.shtyrev.ClientService.cb.RequestType;

@Component
public class CircuitBreakerSum extends CircuitBreaker {
    @Autowired
    public CircuitBreakerSum(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    public RequestType type() {
        return RequestType.SUM;
    }
}
