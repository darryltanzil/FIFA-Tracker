package com.darryltanzil.FIFASTAT;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ComparisonController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/comparison")
    public Comparison comparison(@RequestParam(value = "player1", defaultValue = "None") String player1,
                                 @RequestParam(value = "player2", defaultValue = "None") String player2) {
        return new Comparison(counter.incrementAndGet(), String.format(template, player1), "No winner yet");
    }
}
