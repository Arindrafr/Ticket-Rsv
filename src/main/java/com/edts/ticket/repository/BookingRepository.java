package com.edts.ticket.repository;

import com.edts.ticket.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT COALESCE(SUM(b.quantity), 0) FROM Booking b WHERE b.user.id = :userId AND b.event.id = :eventId")
    Integer countUserBookingsForEvent(@Param("userId") Long userId, @Param("eventId") Long eventId);

    List<Booking> findByUserIdAndEventId(Long userId, Long eventId);

    List<Booking> findByTicketBatchId(Long batchId);

    List<Booking> findByStatus(Booking.Status status);
}
