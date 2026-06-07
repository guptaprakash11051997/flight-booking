package com.example.flightbooking.service;

import com.example.flightbooking.exception.FlightAlreadyExistsException;
import com.example.flightbooking.exception.FlightFullyBookedException;
import com.example.flightbooking.exception.FlightNotFoundException;
import com.example.flightbooking.model.BookingConfirmation;
import com.example.flightbooking.model.Flight;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class FlightService {

    private final ConcurrentHashMap<String, Flight> flights = new ConcurrentHashMap<>();

    public Flight createFlight(String flightNumber, int capacity) {
        Flight flight = new Flight(flightNumber, capacity);
        Flight existing = flights.putIfAbsent(flightNumber, flight);
        if (existing != null) {
            throw new FlightAlreadyExistsException(flightNumber);
        }
        return flight;
    }

    public BookingConfirmation bookSeat(String flightNumber) {
        Flight flight = flights.get(flightNumber);
        if (flight == null) {
            throw new FlightNotFoundException(flightNumber);
        }
        boolean booked = flight.bookSeat();
        if (!booked) {
            throw new FlightFullyBookedException(flightNumber);
        }
        return new BookingConfirmation(flightNumber);
    }
}
