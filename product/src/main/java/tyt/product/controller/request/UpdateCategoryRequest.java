package tyt.product.controller.request;

import lombok.Value;

@Value
public class UpdateCategoryRequest {
    long id;
    String name;
    boolean isActive;
}
