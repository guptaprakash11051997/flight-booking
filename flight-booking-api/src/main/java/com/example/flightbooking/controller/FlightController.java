package com.example.flightbooking.controller;

import com.example.flightbooking.model.BookingConfirmation;
import com.example.flightbooking.model.Flight;
import com.example.flightbooking.service.FlightService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flights")
@Validated
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    /**
     * Create a flight.
     * POST /flights?flightNumber=AI101&capacity=150
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Flight createFlight(
            @RequestParam @NotBlank(message = "Flight number must not be blank") String flightNumber,
            @RequestParam @Min(value = 1, message = "Capacity must be at least 1") int capacity) {
        return flightService.createFlight(flightNumber.toUpperCase(), capacity);
    }

    /**
     * Book a seat on a flight.
     * POST /flights/{flightNumber}/bookings
     */
    @PostMapping("/{flightNumber}/bookings")
    @ResponseStatus(HttpStatus.CREATED)
    public BookingConfirmation bookSeat(@PathVariable String flightNumber) {
        return flightService.bookSeat(flightNumber.toUpperCase());
    }
}
