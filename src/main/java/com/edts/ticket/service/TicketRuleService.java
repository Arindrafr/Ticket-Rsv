package com.edts.ticket.service;

import com.edts.ticket.entity.TicketRule;
import com.edts.ticket.repository.TicketRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketRuleService {
    private final TicketRuleRepository ticketRuleRepository;

    public Optional<TicketRule> getRuleByEventId(Long eventId) {
        return ticketRuleRepository.findByEventId(eventId);
    }
}