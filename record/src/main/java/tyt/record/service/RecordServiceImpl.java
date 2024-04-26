package tyt.record.service;


import org.springframework.stereotype.Service;
import tyt.record.database.OrderRepository;
import tyt.record.database.ProductRepository;
import tyt.record.model.OrderEntity;
import tyt.record.model.PdfGenerator;

import java.io.IOException;
import java.util.Optional;

@Service
public class RecordServiceImpl implements RecordService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final PdfGenerator pdfGenerator;

    public RecordServiceImpl(ProductRepository productRepository, OrderRepository orderRepository, PdfGenerator pdfGenerator) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.pdfGenerator = pdfGenerator;
    }

    @Override
    public String createRecordForOrder(Long orderId) {
        try {
            Optional<OrderEntity> orderOptional = orderRepository.findById(orderId);
            if (orderOptional.isPresent()) {
                String filePath = "C:/Users/Baran/Desktop/OrderReceipt" + orderId + ".pdf";
                pdfGenerator.generatePdf(filePath);
                return filePath;
            } else {
                return "Order not found";
            }
        } catch (IOException e) {
            return "Error creating PDF";
        }
    }
}