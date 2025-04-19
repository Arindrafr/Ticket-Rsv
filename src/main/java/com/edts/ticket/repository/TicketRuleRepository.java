package com.edts.ticket.repository;

import com.edts.ticket.entity.TicketRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRuleRepository extends JpaRepository<TicketRule, Long> {
    Optional<TicketRule> findByEventId(Long eventId);
}
