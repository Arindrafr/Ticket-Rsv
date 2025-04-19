# Event Ticket Reservation

Event Ticket Reservation System (Early Design)

A scalable, high-performance ticket booking API built with Java Spring Boot, supporting time-limited and batched ticket sales for concerts and other live events.

##  Features

-  Search for upcoming events by keyword, artist, or location
-  Support for time-limited ticket purchases (race booking)
-  Batch-based ticket configurations (e.g., Early Bird, Regular)
-  Multiple ticket purchases with user restrictions
-  Prevent overbooking with validation & optimistic locking
-  Swagger UI for API documentation and testing


##  Tech Stack

- Java 17
- Spring Boot 3.x
- Hibernate JPA
- MySQL / PostgreSQL
- Swagger / OpenAPI
- Maven

Documentation in Doc folder or using Swagger UI

Future Enhancement
- Redis locking for race condition safety
- Using Message Queue for race condition
- Payment integration

