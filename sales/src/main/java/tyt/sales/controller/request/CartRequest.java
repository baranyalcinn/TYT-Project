package tyt.sales.controller.request;

import lombok.Value;

@Value
public class CartRequest {

    Long productId;
    int quantity;
}
