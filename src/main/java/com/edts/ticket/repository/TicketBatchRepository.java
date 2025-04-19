package com.edts.ticket.repository;

import com.edts.ticket.entity.TicketBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketBatchRepository extends JpaRepository<TicketBatch, Long> {
    @Query("SELECT b FROM TicketBatch b WHERE b.event.id = :eventId AND b.startTime <= :now AND b.endTime >= :now")
    List<TicketBatch> findAvailableBatchesForNow(@Param("eventId") Long eventId, @Param("now") LocalDateTime now);

    List<TicketBatch> findByEventId(Long eventId);

    @Query("SELECT b FROM TicketBatch b WHERE b.batchType = :type AND b.event.id = :eventId")
    List<TicketBatch> findByBatchTypeAndEventId(@Param("type") TicketBatch.BatchType type, @Param("eventId") Long eventId);
}
