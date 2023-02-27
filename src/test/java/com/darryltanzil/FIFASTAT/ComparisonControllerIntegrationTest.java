package com.darryltanzil.FIFASTAT;

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

import static java.lang.String.valueOf;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ComparisonController.class)
class ComparisonControllerIntegrationTest {

    // Model-view-controller which lets us test GET endpoints
    @Autowired
    private MockMvc mvc;

    @Test
    void comparison() throws Exception {

//        RequestBuilder request = MockMvcRequestBuilders.get("/comparison").header("X-AUTH-TOKEN",
//                System.getenv("APIKEY"));
//
//        MvcResult result = mvc.perform(request).andReturn();
//        assertEquals("{\"id\":1,\"winner\":\"Neither\",\"loser\":\"Neither\",\"description\":\"Error: Invalid comparison operator\"}", result.getResponse().getContentAsString());
    }

    @Test
    void getPlayerData() {
    }
}