package ru.shtyrev.CalculatorService.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.shtyrev.CalculatorService.services.CalculatorService;

@RestController
@RequestMapping("/calculator")
@RequiredArgsConstructor
public class CalculatorController {
    private final CalculatorService calculatorService;

    @GetMapping("/sum")
    ResponseEntity<Integer> sum(@RequestParam int x1, @RequestParam int x2) {
        int sum = calculatorService.sum(x1, x2);
        return new ResponseEntity<>(sum, HttpStatus.OK);
    }

    @GetMapping("/minus")
    ResponseEntity<Integer> minus(@RequestParam int x1, @RequestParam int x2) {
        int minus = calculatorService.minus(x1, x2);
        return new ResponseEntity<>(minus, HttpStatus.OK);
    }

    @GetMapping("/mul")
    ResponseEntity<Integer> mul(@RequestParam int x1, @RequestParam int x2) {
        int mul = calculatorService.mul(x1, x2);
        return new ResponseEntity<>(mul, HttpStatus.OK);
    }

    @GetMapping("/div")
    ResponseEntity<Integer> div(@RequestParam int x1, @RequestParam int x2) {
        int div = calculatorService.div(x1, x2);
        return new ResponseEntity<>(div, HttpStatus.OK);
    }
}
