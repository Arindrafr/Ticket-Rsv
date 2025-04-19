package com.edts.ticket.service;

import com.edts.ticket.entity.TicketBatch;
import com.edts.ticket.repository.TicketBatchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketBatchServiceTest {

    @Mock
    private TicketBatchRepository ticketBatchRepository;

    @InjectMocks
    private TicketBatchService ticketBatchService;

    private TicketBatch batch1;
    private TicketBatch batch2;

    @BeforeEach
    void setup() {
        batch1 = TicketBatch.builder().id(1L).batchName("Early Bird").build();
        batch2 = TicketBatch.builder().id(2L).batchName("Regular").build();
    }

    @Test
    void testGetAvailableBatches() {
        when(ticketBatchRepository.findAvailableBatchesForNow(eq(1L), any()))
                .thenReturn(List.of(batch1));

        List<TicketBatch> result = ticketBatchService.getAvailableBatches(1L);

        assertEquals(1, result.size());
        assertEquals("Early Bird", result.get(0).getBatchName());
        verify(ticketBatchRepository, times(1)).findAvailableBatchesForNow(eq(1L), any());
    }

    @Test
    void testGetBatchesByEvent() {
        when(ticketBatchRepository.findByEventId(1L)).thenReturn(List.of(batch1, batch2));

        List<TicketBatch> result = ticketBatchService.getBatchesByEvent(1L);

        assertEquals(2, result.size());
        verify(ticketBatchRepository, times(1)).findByEventId(1L);
    }

    @Test
    void testGetBatchById() {
        when(ticketBatchRepository.findById(1L)).thenReturn(Optional.of(batch1));

        Optional<TicketBatch> result = ticketBatchService.getBatchById(1L);

        assertTrue(result.isPresent());
        assertEquals("Early Bird", result.get().getBatchName());
        verify(ticketBatchRepository, times(1)).findById(1L);
    }
}
