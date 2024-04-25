package tyt.sales.service;

import tyt.sales.model.dto.OrderDto;

import java.util.List;

public interface OrderService {

    List<OrderDto> getAllOrders();

    //get order by id
    OrderDto getOrderById(Long id);
}
