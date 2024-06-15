package tyt.record.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tyt.record.controller.response.RecordResponse;
import tyt.record.model.PdfGenerator;
import tyt.record.model.dto.OrderDTO;
import tyt.record.model.mapper.OrderMapper;
import tyt.record.repository.OrderRepository;
import tyt.record.service.RecordService;

import java.io.IOException;

/**
 * Service class for handling records related to orders and products.
 * This class is marked as primary, meaning it has precedence when Spring looks for beans of type RecordService.
 */
@Service
@Primary
@Log4j2
@AllArgsConstructor
public class RecordServiceImpl implements RecordService {

    private static final OrderMapper ORDER_MAPPER = OrderMapper.INSTANCE;

    private final OrderRepository orderRepository;
    private final PdfGenerator pdfGenerator;

    /**
     * Creates a record for a given order.
     *
     * @param orderId The ID of the order.
     * @return A RecordResponse containing the file path of the generated PDF and HTTP status,
     * or an error message if the order was not found or the PDF could not be created.
     */
    @Override
    public RecordResponse createRecordForOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(order -> {
                    try {
                        OrderDTO orderDto = ORDER_MAPPER.toDto(order);
                        String filePath = String.format("/usr/share/app/slips/%s.pdf", orderDto.getOrderNumber());
                        pdfGenerator.generatePdf(filePath, orderDto);
                        return new RecordResponse("PDF created successfully at " + filePath, HttpStatus.OK);
                    } catch (IOException e) {
                        log.error("Error creating PDF for order {}", orderId, e);
                        return new RecordResponse("Error creating PDF", HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                })
                .orElseGet(() -> {
                    log.error("Order with id {} not found", orderId);
                    return new RecordResponse("Order not found", HttpStatus.NOT_FOUND);
                });
    }

}