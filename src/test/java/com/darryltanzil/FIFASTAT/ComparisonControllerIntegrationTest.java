package com.darryltanzil.FIFASTAT;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Map;

import static java.lang.String.valueOf;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ComparisonController.class)
class ComparisonControllerIntegrationTest {

    // Model-view-controller which lets us test GET endpoints
    @Autowired
    private MockMvc mvc;

    @Test
    void shouldCreateMockMVC() {
        assertNotNull(mvc);
    }

    /**
     * Checking the "comparison" endpoint, making sure we are able to make api calls successfully, and making sure
     * that we are able to make successful comparisons.
     */
    @Test
    void comparison() throws Exception {

        // CASE 1: No comparison operator

        RequestBuilder badRequest = MockMvcRequestBuilders.get("/comparison").header("X-AUTH-TOKEN",
                System.getenv("APIKEY"));
        MvcResult badResult = mvc.perform(badRequest).andReturn();
        String badResultString = badResult.getResponse().getContentAsString();

        // Map to the comparison class from string
        ObjectMapper mapper = new ObjectMapper();
        Comparison badComparison = mapper.readValue(badResultString, Comparison.class);

        Comparison expected = new Comparison(1, "Neither", "Neither", "Error: Invalid comparison operator");

        assertEquals(expected, badComparison);

        // CASE 2: Checking for height request
        RequestBuilder heightRequest = MockMvcRequestBuilders.get("/comparison?player1=1&player2=2&compare=height").header("X-AUTH-TOKEN",
                System.getenv("APIKEY"));

        MvcResult heightResult = mvc.perform(heightRequest).andReturn();
        String heightResultString = heightResult.getResponse().getContentAsString();

        ObjectMapper heightMapper = new ObjectMapper();
        Comparison heightComparison = mapper.readValue(heightResultString, Comparison.class);
        Comparison expectedHeight = new Comparison(2, "Thomas Partey", "Martin Ã\u0098degaard", "Thomas Partey is 7 cm taller then Martin Ã\u0098degaard.");

        assertEquals(expectedHeight, heightComparison);

        // CASE 3: Checking for weight request
        RequestBuilder weightRequest = MockMvcRequestBuilders.get("/comparison?player1=12&player2=6&compare=weight").header("X-AUTH-TOKEN",
                System.getenv("APIKEY"));

        MvcResult weightResult = mvc.perform(weightRequest).andReturn();
        String weightResultString = weightResult.getResponse().getContentAsString();

        ObjectMapper weightMapper = new ObjectMapper();
        Comparison weightComparison = mapper.readValue(weightResultString, Comparison.class);
        Comparison expectedWeight = new Comparison(3, "Neither", "Neither", "Ben White is the same weight as Aaron Ramsdale.");

        assertEquals(expectedWeight, weightComparison);

    }

    @Test
    void getPlayerData() {
    }
}