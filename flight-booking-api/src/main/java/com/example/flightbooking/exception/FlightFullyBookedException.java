package com.example.flightbooking.exception;

public class FlightFullyBookedException extends RuntimeException {
    public FlightFullyBookedException(String flightNumber) {
        super("Flight is fully booked: " + flightNumber);
    }
}
