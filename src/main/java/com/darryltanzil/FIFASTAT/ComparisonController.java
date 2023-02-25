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

import javax.swing.*;

@RestController
public class ComparisonController {
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/comparison")
    public Comparison comparison(@RequestParam(value = "player1", defaultValue = "None") String player1,
                                 @RequestParam(value = "player2", defaultValue = "None") String player2) {
        Player player1Obj = getPlayerData(player1);
        return new Comparison(counter.incrementAndGet(), "test", "No winner yet");
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
        headers.set("X-AUTH-TOKEN", "apiKey");
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


}
