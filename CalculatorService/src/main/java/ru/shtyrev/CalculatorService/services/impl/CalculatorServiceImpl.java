package ru.shtyrev.CalculatorService.services.impl;

import org.springframework.stereotype.Service;
import ru.shtyrev.CalculatorService.services.CalculatorService;

@Service
public class CalculatorServiceImpl implements CalculatorService {
    @Override
    public int sum(int x1, int x2) {
        return x1 + x2;
    }

    @Override
    public int minus(int x1, int x2) {
        return x1 - x2;
    }

    @Override
    public int mul(int x1, int x2) {
        return x1 * x2;
    }

    @Override
    public int div(int x1, int x2) {
        return x1 / x2;
    }
}
