package tyt.record.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tyt.record.controller.response.RecordResponse;
import tyt.record.model.OrderEntity;
import tyt.record.model.PdfGenerator;
import tyt.record.repository.OrderRepository;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RecordServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PdfGenerator pdfGenerator;

    @InjectMocks
    private RecordServiceImpl recordService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        OrderEntity order = new OrderEntity();
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
    }

    @Test
    void createRecordForOrder_OrderExists_PdfCreated() throws IOException {
        Long orderId = 1L;
        OrderEntity order = new OrderEntity();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        RecordResponse result = recordService.createRecordForOrder(orderId);

        verify(pdfGenerator, times(1)).generatePdf(anyString(), any());
        String expectedFilePath = String.format("/usr/share/app/slips/%s.pdf", order.getOrderNumber());
        String expectedMessage = "PDF created successfully at " + expectedFilePath;
        assertEquals(expectedMessage, result.getMessage());
    }

    @Test
    void createRecordForOrder_OrderDoesNotExist_ReturnsErrorMessage() {
        Long orderId = 10000L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        RecordResponse result = recordService.createRecordForOrder(orderId);

        assertEquals("Order not found", result.getMessage());
    }

    @Test
    void createRecordForOrder_PdfCreationFails_ReturnsErrorMessage() throws IOException {
        Long orderId = 1L;
        OrderEntity order = new OrderEntity();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        doThrow(IOException.class).when(pdfGenerator).generatePdf(anyString(), any());

        RecordResponse result = recordService.createRecordForOrder(orderId);

        assertEquals("Error creating PDF", result.getMessage());
    }
}