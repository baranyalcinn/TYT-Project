package tyt.sales.controller;

import org.springframework.web.bind.annotation.*;
import tyt.sales.model.dto.OrderDTO;
import tyt.sales.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/all")
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public OrderDTO getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }
}
