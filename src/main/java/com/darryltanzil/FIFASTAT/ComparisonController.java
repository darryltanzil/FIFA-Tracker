package com.darryltanzil.FIFASTAT;

import java.util.concurrent.atomic.AtomicLong;
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
    private final AtomicLong counter = new AtomicLong();

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

        Player player1Obj = getPlayerData(player1);
        Player player2Obj = getPlayerData(player2);

        // guard for same character
        if (player1Obj.id() == player2Obj.id()) {
            return new Comparison(counter.incrementAndGet(),
                    "N/A", "N/A", "Error: duplicate player");
        }

        switch (compareType) {
            case "height":
                return compareHeight(player1Obj, player2Obj);
            case "weight":
                return compareWeight(player1Obj, player2Obj);
            default:
                return new Comparison(counter.incrementAndGet(), "Neither", "Neither",
                        "Error: Invalid comparison operator");
        }
    }

    /**
     *  GETs player in the form of the Player object
     * @param player the id of player
     * @return the player Object
     */
    public Player getPlayerData(String player) {
        RestTemplate restTemplate = new RestTemplate();
        String apiKey = System.getenv("APIKEY");

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", apiKey);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<PlayerJSON> player1Response = restTemplate.exchange(
                "https://futdb.app/api/players/" + player,
                HttpMethod.GET,
                entity,
                PlayerJSON.class
        );

        try {
            return player1Response.getBody().player();
        } catch (NullPointerException e) {
            throw new RuntimeException("Error: No player with id: " + player, e);
        }
    }

    /**
     * Given two player objects, compares the height between the two
     * @param player1 Player one object
     * @param player2 Player two object
     * @return Comparison between the two
     */
    public Comparison compareHeight(Player player1, Player player2) {
        int heightDiff = Math.abs(player1.height() - player2.height());

        if (player1.height() > player2.height()) {
            return new Comparison(counter.incrementAndGet(),
                    player1.name(), player2.name(),
                    player1.name() + " is " + heightDiff + " cm taller then " + player2.name() + ".");
        } else if (player1.height() < player2.height()) {
            return new Comparison(counter.incrementAndGet(),
                    player2.name(), player1.name(),
                    player2.name() + " is " + heightDiff + " cm taller then " + player1.name() + ".");
        } else {
            return new Comparison(counter.incrementAndGet(),
                    "Neither", "Neither",
                    player1.name() + " is the same height as " + player2.name() + ".");
        }
    }

    /**
     * Given two player objects, compares the weight between the two
     * @param player1 Player one object
     * @param player2 Player two object
     * @return Weight between the two
     */
    public Comparison compareWeight(Player player1, Player player2) {
        int weightDiff = Math.abs(player1.weight() - player2.weight());

        if (player1.weight() > player2.weight()) {
            return new Comparison(counter.incrementAndGet(),
                    player1.name(), player2.name(),
                    player1.name() + " is " + weightDiff + " kg heavier then " + player2.name() + ".");
        } else if (player1.weight() < player2.weight()) {
            return new Comparison(counter.incrementAndGet(),
                    player2.name(), player1.name(),
                    player2.name() + " is " + weightDiff + " kg heavier then " + player1.name() + ".");
        } else {
            return new Comparison(counter.incrementAndGet(),
                    "Neither", "Neither",
                    player1.name() + " is the same weight as " + player2.name() + ".");
        }
    }
}
