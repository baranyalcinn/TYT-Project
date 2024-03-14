package tyt.product.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tyt.product.controller.request.CreateCategoryRequest;
import tyt.product.controller.request.UpdateCategoryRequest;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private  Long id;
    private String name;
    private boolean isActive;

    public static CategoryDTO of(CreateCategoryRequest createCategoryRequest){
        return CategoryDTO.builder()
                .name(createCategoryRequest.getName())
                .isActive(true)
                .build();
    }

    public static CategoryDTO of(UpdateCategoryRequest updateCategoryRequest){
        return CategoryDTO.builder()
                .id(updateCategoryRequest.getId())
                .name(updateCategoryRequest.getName())
                .isActive(updateCategoryRequest.isActive())
                .build();
    }

    public boolean isActive() {
        return isActive;
    }


}
