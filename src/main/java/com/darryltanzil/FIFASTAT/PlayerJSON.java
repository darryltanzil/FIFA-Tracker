package com.darryltanzil.FIFASTAT;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The response from the GET request returns a JSON body, containing another JSON object which can be mapped to
 * a domain class
 * @param player
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record PlayerJSON(Player player) {
}