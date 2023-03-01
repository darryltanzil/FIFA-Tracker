package com.darryltanzil.FIFASTAT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * Class to initially boot up the Spring Application.
 * Defines a Rest template with HTTP headers to the API, and tests getting a character, logging information about it
 * Used to determine whether connection to API is successful
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.darryltanzil.FIFASTAT")

public class FifaStatApplication {
    private static final Logger log = LoggerFactory.getLogger(FifaStatApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(FifaStatApplication.class, args);
    }

    /**
     * Returns RestTemplate with Spring Framework (used for RESTfuls application)
     */
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(myInterceptor()));
        return restTemplate;
    }

    /**
     * Interceptor which is passed in as parameter to Template, containing header with API env key
     */
    @Bean
    public ClientHttpRequestInterceptor myInterceptor() {
        String apiKey = System.getenv("APIKEY");
        return (request, body, execution) -> {
            request.getHeaders().add("X-AUTH-TOKEN", apiKey);
            return execution.execute(request, body);
        };
    }

    /**
     * Fetches data in "PlayerJSON" object format, to be parsed
     * @param restTemplate
     * @throws Exception
     */
    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {
            // GET request using spring, returns as PlayerJSON object
            PlayerJSON playerJSON = restTemplate.getForObject(
                    "https://futdb.app/api/players/1", PlayerJSON.class);
            log.info(playerJSON.toString());
            System.out.println("Resulting PlayerJSON is: " + playerJSON.player().id());
            System.out.println("Player Full Name is: " + playerJSON.player().name());
        };
    }
}
