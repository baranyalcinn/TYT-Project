package tyt.record.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tyt.record.database.OrderRepository;
import tyt.record.database.ProductRepository;
import tyt.record.model.OrderEntity;
import tyt.record.model.PdfGenerator;
import tyt.record.model.ProductEntity;
import tyt.record.model.dto.OrderDTO;
import tyt.record.model.dto.ProductDTO;
import tyt.record.model.mapper.OrderMapper;
import tyt.record.model.mapper.ProductMapper;

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

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final PdfGenerator pdfGenerator;

    /**
     * Constructor for RecordServiceImpl.
     *
     * @param productRepository Repository for accessing product data.
     * @param orderRepository   Repository for accessing order data.
     * @param pdfGenerator      Utility for generating PDFs.
     */
    public RecordServiceImpl(ProductRepository productRepository, OrderRepository orderRepository, PdfGenerator pdfGenerator) {
        this.productRepository = productRepository;
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
        if (orderOptional.isPresent()) {
            OrderEntity order = orderOptional.get();
            OrderDTO orderDTO = OrderMapper.INSTANCE.toDto(order);
            String filePath = "C:/Users/Baran/Desktop/slip" + "-" + orderId + ".pdf";
            pdfGenerator.generatePdf(filePath, orderDTO);
            return filePath;
        } else {
            return "Order not found";
        }
    } catch (IOException e) {
        return "Error creating PDF";
    }
}
    /**
     * Retrieves an order by its ID.
     *
     * @param id The ID of the order.
     * @return The order, or throws a RuntimeException if the order was not found.
     */
   @Override
public OrderDTO getOrderById(Long id) {
    Optional<OrderEntity> orderOptional = orderRepository.findById(id);
    if (orderOptional.isPresent()) {
        return OrderMapper.INSTANCE.toDto(orderOptional.get());
    } else {
        throw new RuntimeException("Order not found with id: " + id);
    }
}

    /**
     * Retrieves a product by its ID.
     *
     * @param id The ID of the product.
     * @return The product, or throws a RuntimeException if the product was not found.
     */
    @Override
    public ProductDTO getProduct(Long id) {
        Optional<ProductEntity> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            return ProductMapper.INSTANCE.toDto(productOptional.get());
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }
}