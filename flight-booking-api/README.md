# Flight Booking API

Simple Spring Boot REST API for booking flight seats with in-memory storage.

---

## How to Run

```bash
mvn spring-boot:run
```

The service starts on `http://localhost:8080`.

---

## API Endpoints

### Create a Flight

```bash
curl -X POST "http://localhost:8080/flights?flightNumber=AI101&capacity=2"
```

**Response (201 Created):**
```json
{
  "flightNumber": "AI101",
  "capacity": 2,
  "bookedSeats": 0,
  "availableSeats": 2
}
```

---

### Book a Seat

```bash
curl -X POST "http://localhost:8080/flights/AI101/bookings"
```

**Response (201 Created):**
```json
{
  "bookingId": "d3f1a2b4-...",
  "flightNumber": "AI101",
  "bookedAt": "2026-03-20T10:00:00Z"
}
```

---

### Error Responses (RFC 7807)

**Flight not found (404):**
```json
{
  "title": "Flight Not Found",
  "status": 404,
  "detail": "Flight not found: XY999"
}
```

**Flight fully booked (409):**
```json
{
  "title": "Flight Fully Booked",
  "status": 409,
  "detail": "Flight is fully booked: AI101"
}
```

**Duplicate flight (409):**
```json
{
  "title": "Flight Already Exists",
  "status": 409,
  "detail": "Flight already exists: AI101"
}
```

---

## What I Would Improve With More Time

- **Persistence** — Replace in-memory `ConcurrentHashMap` with PostgreSQL via Spring Data JPA
- **Booking entity** — Track who booked, allow cancellations, add passenger details
- **Idempotency** — Accept a client-supplied `Idempotency-Key` header to safely retry booking requests
- **Structured logging** — Add correlation IDs per request for traceability
- **Integration tests** — More edge cases: concurrent booking stress tests, invalid input variants
- **Swagger / OpenAPI** — Auto-generated API docs via `springdoc-openapi`
- **Observability** — Expose `/actuator/health` and metrics via Spring Boot Actuator
