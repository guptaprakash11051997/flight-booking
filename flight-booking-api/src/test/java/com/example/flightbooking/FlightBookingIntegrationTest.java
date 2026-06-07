package com.example.flightbooking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FlightBookingIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createAndBookFlight_fullFlow() throws Exception {
        mockMvc.perform(post("/flights")
                        .param("flightNumber", "INT101")
                        .param("capacity", "1"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.flightNumber").value("INT101"));

        mockMvc.perform(post("/flights/INT101/bookings"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookingId").isNotEmpty());
    }

    @Test
    void overbooking_returns409() throws Exception {
        mockMvc.perform(post("/flights")
                        .param("flightNumber", "INT102")
                        .param("capacity", "1"))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/flights/INT102/bookings"))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/flights/INT102/bookings"))
                .andExpect(status().isConflict());
    }

    @Test
    void bookUnknownFlight_returns404() throws Exception {
        mockMvc.perform(post("/flights/GHOST99/bookings"))
                .andExpect(status().isNotFound());
    }
}
