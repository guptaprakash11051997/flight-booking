package com.example.flightbooking.model;

import java.time.Instant;
import java.util.UUID;

public class BookingConfirmation {

    private final String bookingId;
    private final String flightNumber;
    private final Instant bookedAt;

    public BookingConfirmation(String flightNumber) {
        this.bookingId = UUID.randomUUID().toString();
        this.flightNumber = flightNumber;
        this.bookedAt = Instant.now();
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public Instant getBookedAt() {
        return bookedAt;
    }
}
