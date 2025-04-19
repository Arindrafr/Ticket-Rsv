package com.edts.ticket.controller;

import com.edts.ticket.entity.*;
import com.edts.ticket.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@Tag(name = "Booking Controller", description = "Operations related to ticket booking")
public class BookingController {

    private final BookingService bookingService;
    private final EventService eventService;
    private final TicketBatchService ticketBatchService;
    private final UserService userService;

    @PostMapping("/create")
    @Operation(summary = "Create a ticket booking")
    public ResponseEntity<?> bookTicket(@RequestParam(name = "userId") Long userId,
                                        @RequestParam(name = "eventId") Long eventId,
                                        @RequestParam(name = "batchId") Long batchId,
                                        @RequestParam(name = "quantity") int quantity) {
        Optional<User> userOpt = userService.getUserById(userId);
        Optional<Event> eventOpt = eventService.getEventById(eventId);
        Optional<TicketBatch> batchOpt = ticketBatchService.getBatchById(batchId);

        if (userOpt.isEmpty() || eventOpt.isEmpty() || batchOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid input data");
        }

        Booking booking = bookingService.createBooking(userOpt.get(), eventOpt.get(), batchOpt.get(), quantity);
        return ResponseEntity.ok(booking);
    }
}
