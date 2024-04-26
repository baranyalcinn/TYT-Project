package tyt.record.service;


import org.springframework.stereotype.Service;
import tyt.record.database.OrderRepository;
import tyt.record.database.ProductRepository;
import tyt.record.model.OrderEntity;
import tyt.record.model.PdfGenerator;
import tyt.record.model.ProductEntity;
import tyt.record.model.dto.OrderDTO;
import tyt.record.model.dto.ProductDTO;

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
                String filePath = "C:/Users/Baran/Desktop/OrderReceipt" + "-" + orderId + ".pdf";
                pdfGenerator.generatePdf(filePath, orderOptional.get());
                return filePath;
            } else {
                return "Order not found";
            }
        } catch (IOException e) {
            return "Error creating PDF";
        }
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        Optional<OrderEntity> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            return OrderDTO.fromEntity(orderOptional.get());
        } else {
            throw new RuntimeException("Order not found with id: " + id);
        }
    }

    @Override
    public ProductDTO getProduct(Long id) {
        Optional<ProductEntity> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            return ProductDTO.fromEntity(productOptional.get());
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }
}