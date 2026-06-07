package com.example.flightbooking;

import com.example.flightbooking.exception.FlightAlreadyExistsException;
import com.example.flightbooking.exception.FlightFullyBookedException;
import com.example.flightbooking.exception.FlightNotFoundException;
import com.example.flightbooking.model.BookingConfirmation;
import com.example.flightbooking.model.Flight;
import com.example.flightbooking.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class FlightServiceTest {

    private FlightService flightService;

    @BeforeEach
    void setUp() {
        flightService = new FlightService();
    }

    @Test
    void createFlight_success() {
        Flight flight = flightService.createFlight("AI101", 2);
        assertThat(flight.getFlightNumber()).isEqualTo("AI101");
        assertThat(flight.getCapacity()).isEqualTo(2);
        assertThat(flight.getAvailableSeats()).isEqualTo(2);
    }

    @Test
    void createFlight_duplicate_throwsConflict() {
        flightService.createFlight("AI101", 2);
        assertThatThrownBy(() -> flightService.createFlight("AI101", 5))
                .isInstanceOf(FlightAlreadyExistsException.class);
    }

    @Test
    void bookSeat_happyPath() {
        flightService.createFlight("AI101", 2);
        BookingConfirmation confirmation = flightService.bookSeat("AI101");
        assertThat(confirmation.getBookingId()).isNotBlank();
        assertThat(confirmation.getFlightNumber()).isEqualTo("AI101");
    }

    @Test
    void bookSeat_lastSeat_succeeds() {
        flightService.createFlight("AI101", 1);
        BookingConfirmation confirmation = flightService.bookSeat("AI101");
        assertThat(confirmation).isNotNull();
    }

    @Test
    void bookSeat_overbooking_throwsConflict() {
        flightService.createFlight("AI101", 1);
        flightService.bookSeat("AI101");
        assertThatThrownBy(() -> flightService.bookSeat("AI101"))
                .isInstanceOf(FlightFullyBookedException.class);
    }

    @Test
    void bookSeat_unknownFlight_throwsNotFound() {
        assertThatThrownBy(() -> flightService.bookSeat("UNKNOWN"))
                .isInstanceOf(FlightNotFoundException.class);
    }
}
