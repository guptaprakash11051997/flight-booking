package com.example.flightbooking.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Flight {

    private final String flightNumber;
    private final int capacity;
    private final AtomicInteger bookedSeats;

    public Flight(String flightNumber, int capacity) {
        this.flightNumber = flightNumber;
        this.capacity = capacity;
        this.bookedSeats = new AtomicInteger(0);
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getBookedSeats() {
        return bookedSeats.get();
    }

    public int getAvailableSeats() {
        return capacity - bookedSeats.get();
    }

    /**
     * Thread-safe booking using compareAndSet loop.
     * Returns true if booking succeeded, false if flight is full.
     */
    public boolean bookSeat() {
        while (true) {
            int current = bookedSeats.get();
            if (current >= capacity) {
                return false;
            }
            if (bookedSeats.compareAndSet(current, current + 1)) {
                return true;
            }
        }
    }
}
