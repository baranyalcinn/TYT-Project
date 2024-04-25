package tyt.sales.service;

import tyt.sales.model.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    List<OrderDTO> getAllOrders();

    //get order by id
    OrderDTO getOrderById(Long id);
}
