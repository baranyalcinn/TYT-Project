package tyt.record.service;

import tyt.record.model.dto.OrderDTO;
import tyt.record.model.dto.ProductDTO;

public interface RecordService {

    String createRecordForOrder(Long id);

    OrderDTO getOrderById(Long id);

    ProductDTO getProduct(Long id);
}
