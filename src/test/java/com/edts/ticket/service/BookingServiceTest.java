package com.edts.ticket.service;

import com.edts.ticket.entity.*;
import com.edts.ticket.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private TicketBatchRepository ticketBatchRepository;

    @Mock
    private TicketRuleRepository ticketRuleRepository;

    @InjectMocks
    private BookingService bookingService;

    private User user;
    private Event event;
    private TicketBatch batch;
    private TicketRule rule;

    @BeforeEach
    void setUp() {
        user = User.builder().id(1L).username("user1").email("user1@example.com").build();
        event = Event.builder().id(1L).name("Concert").totalTickets(5000)
                .eventDateTime(LocalDateTime.now().plusDays(1)).build();
        batch = TicketBatch.builder()
                .id(1L)
                .event(event)
                .reservedQuota(3000)
                .soldCount(1000)
                .price(100.0)
                .startTime(LocalDateTime.now().minusMinutes(10))
                .endTime(LocalDateTime.now().plusMinutes(10))
                .build();
        rule = TicketRule.builder().id(1L).event(event).maxTicketsPerUser(5000).build();
    }

    @Test
    void testCreateBooking_Success() {
        when(ticketRuleRepository.findByEventId(event.getId())).thenReturn(Optional.of(rule));
        when(bookingRepository.countUserBookingsForEvent(user.getId(), event.getId())).thenReturn(1);
        when(ticketBatchRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        when(bookingRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Booking booking = bookingService.createBooking(user, event, batch, 2);

        assertNotNull(booking);
        assertEquals(Booking.Status.CONFIRMED, booking.getStatus());
        assertEquals(2, booking.getQuantity());
        verify(ticketBatchRepository, times(1)).save(any());
        verify(bookingRepository, times(1)).save(any());
    }

    @Test
    void testCreateBooking_ExceedsUserLimit() {
        when(ticketRuleRepository.findByEventId(event.getId())).thenReturn(Optional.of(rule));
        when(bookingRepository.countUserBookingsForEvent(user.getId(), event.getId())).thenReturn(5000);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            bookingService.createBooking(user, event, batch, 2);
        });

        assertEquals("Exceeds user max ticket quota", ex.getMessage());
    }

    @Test
    void testCreateBooking_ExceedsBatchQuota() {
        batch.setSoldCount(2999);
        when(ticketRuleRepository.findByEventId(event.getId())).thenReturn(Optional.of(rule));
        when(bookingRepository.countUserBookingsForEvent(user.getId(), event.getId())).thenReturn(1);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            bookingService.createBooking(user, event, batch, 5);
        });

        assertEquals("Batch quota exceeded", ex.getMessage());
    }
}
