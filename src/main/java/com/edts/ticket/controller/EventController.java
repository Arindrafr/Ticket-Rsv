package com.edts.ticket.controller;

import com.edts.ticket.entity.*;
import com.edts.ticket.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
@Tag(name = "Event Controller", description = "Operations related to events")
public class EventController {

    private final EventService eventService;
    private final TicketBatchService ticketBatchService;

    @GetMapping("/upcoming")
    @Operation(summary = "Get all upcoming events with pagination")
    public ResponseEntity<Page<Event>> getUpcomingEvents(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("eventDateTime").ascending());
        return ResponseEntity.ok(eventService.getUpcomingEvents(pageable));
    }

    @GetMapping("/search")
    @Operation(summary = "Search events by keyword with pagination")
    public ResponseEntity<Page<Event>> searchEvents(
            @RequestParam("keyword") String keyword,
            @RequestParam("page") int page,
            @RequestParam("size") int size)  {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(eventService.searchEvents(keyword, pageable));
    }


    @GetMapping("/{eventId}/batches")
    @Operation(summary = "Get available batches for event")
    public ResponseEntity<List<TicketBatch>> getEventBatches(@PathVariable Long eventId) {
        return ResponseEntity.ok(ticketBatchService.getAvailableBatches(eventId));
    }
}
