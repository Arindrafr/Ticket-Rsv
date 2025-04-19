package com.edts.ticket.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ticket_batches")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "batch_name")
    private String batchName;

    @Column(name = "reserved_quota")
    private Integer reservedQuota;

    @Column(name = "sold_count")
    private Integer soldCount = 0;

    private Double price;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "batch_type")
    private BatchType batchType;

    public enum BatchType {
        LIMITED,
        REGULAR
    }
}
