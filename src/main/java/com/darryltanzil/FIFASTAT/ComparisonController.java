package com.darryltanzil.FIFASTAT;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ComparisonController {

    @Autowired
    private ComparisonService comparisonService;

    /**
     * API endpoint that takes in two players, and compares them via an arbitrary criteria
     * @param player1 the id of the first player
     * @param player2 the id of the second player
     * @param compareType what you want to compare them by (height, weight, age)
     * @return Comparison object containing the winner, and description about why they won
     */
    @GetMapping("/comparison")
    public Comparison comparison(@RequestParam(value = "player1", defaultValue = "1") String player1,
                                 @RequestParam(value = "player2", defaultValue = "2") String player2,
                                 @RequestParam(value = "compare", defaultValue = "None") String compareType) {

        Player player1Obj = comparisonService.getPlayerData(player1);
        Player player2Obj = comparisonService.getPlayerData(player2);

        // guard for same character
        if (player1Obj.id() == player2Obj.id()) {
            return new Comparison(comparisonService.getCounter().incrementAndGet(),
                    "N/A", "N/A", "Error: duplicate player");
        }

        switch (compareType) {
            case "height":
                return comparisonService.compareHeight(player1Obj, player2Obj);
            case "weight":
                return comparisonService.compareWeight(player1Obj, player2Obj);
            default:
                return new Comparison(comparisonService.getCounter().incrementAndGet(), "Neither", "Neither",
                        "Error: Invalid comparison operator");
        }
    }

}
