package com.edts.ticket.service;

import com.edts.ticket.entity.*;
import com.edts.ticket.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final TicketBatchRepository ticketBatchRepository;
    private final TicketRuleRepository ticketRuleRepository;

    @Transactional
    public Booking createBooking(User user, Event event, TicketBatch batch, int quantity) {
        TicketRule rule = ticketRuleRepository.findByEventId(event.getId())
                .orElseThrow(() -> new IllegalArgumentException("Ticket rule not found"));

        int alreadyBooked = bookingRepository.countUserBookingsForEvent(user.getId(), event.getId());
        if (alreadyBooked + quantity > rule.getMaxTicketsPerUser()) {
            throw new IllegalArgumentException("Exceeds user max ticket quota");
        }

        if (batch.getSoldCount() + quantity > batch.getReservedQuota()) {
            throw new IllegalArgumentException("Batch quota exceeded");
        }

        batch.setSoldCount(batch.getSoldCount() + quantity);
        ticketBatchRepository.save(batch);

        Booking booking = Booking.builder()
                .user(user)
                .event(event)
                .ticketBatch(batch)
                .quantity(quantity)
                .status(Booking.Status.CONFIRMED)
                .reservedAt(LocalDateTime.now())
                .build();

        return bookingRepository.save(booking);
    }

    public List<Booking> getUserBookingsForEvent(Long userId, Long eventId) {
        return bookingRepository.findByUserIdAndEventId(userId, eventId);
    }
}
