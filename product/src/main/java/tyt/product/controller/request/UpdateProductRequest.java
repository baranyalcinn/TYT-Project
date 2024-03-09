package tyt.product.controller.request;

import lombok.Value;

@Value
public class UpdateProductRequest {
    long id;
    String name;
    String description;
    double price;
    int stock;
    boolean isActive;
    long categoryId;
}
