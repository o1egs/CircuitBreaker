package ru.shtyrev.ClientService.cb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CircuitBreakerService {
    private final Map<RequestType, CircuitBreaker> circuitBreakerMap;

    @Autowired
    public CircuitBreakerService(List<CircuitBreaker> circuitBreakerList) {
        circuitBreakerMap = circuitBreakerList.stream()
                .collect(Collectors.toMap(CircuitBreaker::type, Function.identity()));
    }

    public int request(RequestType type, int x1, int x2) throws RuntimeException {
        CircuitBreaker circuitBreaker = circuitBreakerMap.get(type);
        return circuitBreaker.handleRequest(x1, x2);
    }
}
