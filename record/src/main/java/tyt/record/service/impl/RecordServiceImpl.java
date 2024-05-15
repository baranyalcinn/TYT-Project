package tyt.record.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tyt.record.controller.response.RecordResponse;
import tyt.record.model.OrderEntity;
import tyt.record.model.PdfGenerator;
import tyt.record.model.dto.OrderDTO;
import tyt.record.model.mapper.OrderMapper;
import tyt.record.repository.OrderRepository;
import tyt.record.service.RecordService;

import java.io.IOException;
import java.util.Optional;

/**
 * Service class for handling records related to orders and products.
 * This class is marked as primary, meaning it has precedence when Spring looks for beans of type RecordService.
 */
@Service
@Primary
@Log4j2
@AllArgsConstructor
public class RecordServiceImpl implements RecordService {

    private final OrderRepository orderRepository;
    private final PdfGenerator pdfGenerator;
    private final OrderMapper orderMapper = OrderMapper.INSTANCE;

    /**
     * Creates a record for a given order.
     *
     * @param orderId The ID of the order.
     * @return The file path of the generated PDF, or an error message if the order was not found or the PDF could not be created.
     */
    public RecordResponse createRecordForOrder(Long orderId) {
        Optional<OrderEntity> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            OrderEntity order = orderOptional.get();
            OrderDTO orderDto = orderMapper.toDto(order);
            try {
                pdfGenerator.generatePdf("C:/Users/Baran/Desktop/slips/slip-" + orderDto.getOrderNumber() + ".pdf", orderDto);
                return new RecordResponse("PDF created successfully at C:/Users/Baran/Desktop/slips/slip-" + orderDto.getOrderNumber() + ".pdf", HttpStatus.OK);
            } catch (IOException e) {
                return new RecordResponse("Error creating PDF", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new RecordResponse("Order not found", HttpStatus.NOT_FOUND);
        }
    }
}