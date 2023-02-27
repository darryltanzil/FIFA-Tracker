package com.darryltanzil.FIFASTAT;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Singleton object which handles the service comparison
 */
@Service
public class ComparisonService {
    private final AtomicLong counter = new AtomicLong();

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
     * Assumes that both player objects are already valid
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

    public AtomicLong getCounter() {
        return counter;
    }
}
