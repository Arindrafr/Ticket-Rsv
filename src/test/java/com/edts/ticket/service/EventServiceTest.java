package com.edts.ticket.service;

import com.edts.ticket.entity.Event;
import com.edts.ticket.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    private Event event1;
    private Event event2;

    @BeforeEach
    void setup() {
        event1 = Event.builder().id(1L).name("Rock Night").eventDateTime(LocalDateTime.now().plusDays(1)).build();
        event2 = Event.builder().id(2L).name("Jazz Morning").eventDateTime(LocalDateTime.now().plusDays(2)).build();
    }

    @Test
    void testGetUpcomingEvents() {
        Pageable pageable = PageRequest.of(0, 10);
        when(eventRepository.findUpcomingEvents(any(), eq(pageable)))
                .thenReturn(new PageImpl<>(List.of(event1, event2)));

        Page<Event> result = eventService.getUpcomingEvents(pageable);

        assertEquals(2, result.getContent().size());
        verify(eventRepository, times(1)).findUpcomingEvents(any(), eq(pageable));
    }

    @Test
    void testSearchEvents() {
        Pageable pageable = PageRequest.of(0, 10);
        when(eventRepository.searchByKeyword(eq("Rock"), eq(pageable)))
                .thenReturn(new PageImpl<>(List.of(event1)));

        Page<Event> result = eventService.searchEvents("Rock", pageable);

        assertEquals(1, result.getContent().size());
        assertEquals("Rock Night", result.getContent().get(0).getName());
        verify(eventRepository, times(1)).searchByKeyword(eq("Rock"), eq(pageable));
    }

    @Test
    void testGetEventById() {
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event1));

        Optional<Event> result = eventService.getEventById(1L);

        assertTrue(result.isPresent());
        assertEquals("Rock Night", result.get().getName());
        verify(eventRepository, times(1)).findById(1L);
    }
}
