package com.edts.ticket.service;

import com.edts.ticket.entity.Event;
import com.edts.ticket.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    public Page<Event> getUpcomingEvents(Pageable pageable) {
        return eventRepository.findUpcomingEvents(LocalDateTime.now(), pageable);
    }

    public Page<Event> searchEvents(String keyword, Pageable pageable) {
        return eventRepository.searchByKeyword(keyword, pageable);
    }


    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }
}
