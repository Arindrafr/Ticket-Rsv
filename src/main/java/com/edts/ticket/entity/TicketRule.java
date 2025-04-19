package com.edts.ticket.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ticket_rules")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "is_batched")
    private Boolean isBatched = false;

    @Column(name = "is_time_limited")
    private Boolean isTimeLimited = false;

    @Column(name = "max_tickets_per_user")
    private Integer maxTicketsPerUser = 1;
}
