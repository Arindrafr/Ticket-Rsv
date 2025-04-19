package com.edts.ticket.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_batch_id")
    private TicketBatch ticketBatch;

    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    @Column(name = "reserved_at")
    private LocalDateTime reservedAt = LocalDateTime.now();

    public enum Status {
        PENDING,
        CONFIRMED,
        EXPIRED
    }
}
