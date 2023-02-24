package com.darryltanzil.FIFASTAT;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Player(Integer id, String firstName, String lastName, String name, Integer height, Integer weight) {

}