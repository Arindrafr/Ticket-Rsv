package com.edts.ticket.service;

import com.edts.ticket.entity.TicketBatch;
import com.edts.ticket.repository.TicketBatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketBatchService {
    private final TicketBatchRepository ticketBatchRepository;

    public List<TicketBatch> getAvailableBatches(Long eventId) {
        return ticketBatchRepository.findAvailableBatchesForNow(eventId, LocalDateTime.now());
    }

    public List<TicketBatch> getBatchesByEvent(Long eventId) {
        return ticketBatchRepository.findByEventId(eventId);
    }

    public Optional<TicketBatch> getBatchById(Long id) {
        return ticketBatchRepository.findById(id);
    }
}
