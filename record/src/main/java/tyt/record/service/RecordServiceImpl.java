package tyt.record.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tyt.record.database.OrderRepository;
import tyt.record.model.OrderEntity;
import tyt.record.model.PdfGenerator;
import tyt.record.model.dto.OrderDTO;
import tyt.record.model.mapper.OrderMapper;

import java.io.IOException;
import java.util.Optional;

/**
 * Service class for handling records related to orders and products.
 * This class is marked as primary, meaning it has precedence when Spring looks for beans of type RecordService.
 */
@Service
@Primary
@Log4j2
public class RecordServiceImpl implements RecordService {

    private final OrderRepository orderRepository;
    private final PdfGenerator pdfGenerator;

    /**
     * Constructor for RecordServiceImpl.
     *
     * @param orderRepository   Repository for accessing order data.
     * @param pdfGenerator      Utility for generating PDFs.
     */
    public RecordServiceImpl(OrderRepository orderRepository, PdfGenerator pdfGenerator) {
        this.orderRepository = orderRepository;
        this.pdfGenerator = pdfGenerator;
    }

    /**
     * Creates a record for a given order.
     *
     * @param orderId The ID of the order.
     * @return The file path of the generated PDF, or an error message if the order was not found or the PDF could not be created.
     */

@Override
public String createRecordForOrder(Long orderId) {
    try {
        Optional<OrderEntity> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            return "Order not found";
        } else {
            OrderEntity order = orderOptional.get();
            OrderDTO orderDTO = OrderMapper.INSTANCE.toDto(order);
            String filePath = "C:/Users/Baran/Desktop/slip" + "-" + orderId + ".pdf";
            pdfGenerator.generatePdf(filePath, orderDTO);
            return "PDF created successfully at " + filePath;
        }
    } catch (IOException e) {
        return "Error creating PDF";
    }
}

}