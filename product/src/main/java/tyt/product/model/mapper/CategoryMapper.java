package tyt.product.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import tyt.product.controller.request.CreateCategoryRequest;
import tyt.product.controller.request.UpdateCategoryRequest;
import tyt.product.model.CategoryEntity;
import tyt.product.model.dto.CategoryDTO;

/**
 * This interface is used to map between different category-related objects.
 * It uses the MapStruct library to generate the implementation at compile time.
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper {
    /**
     * An instance of the mapper that can be used anywhere in the application.
     */
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    /**
     * Converts a CategoryEntity object to a CategoryDTO object.
     *
     * @param categoryEntity The CategoryEntity object to convert.
     * @return The converted CategoryDTO object.
     */
    CategoryDTO toDTO(CategoryEntity categoryEntity);

    /**
     * Converts a CategoryDTO object to a CategoryEntity object.
     *
     * @param categoryDTO The CategoryDTO object to convert.
     * @return The converted CategoryEntity object.
     */
    CategoryEntity toEntity(CategoryDTO categoryDTO);

    /**
     * Converts a CreateCategoryRequest object to a CategoryDTO object.
     *
     * @param createCategoryRequest The CreateCategoryRequest object to convert.
     * @return The converted CategoryDTO object.
     */
    CategoryDTO createRequestToDto(CreateCategoryRequest createCategoryRequest);

    /**
     * Converts an UpdateCategoryRequest object to a CategoryDTO object.
     *
     * @param updateCategoryRequest The UpdateCategoryRequest object to convert.
     * @return The converted CategoryDTO object.
     */

    CategoryDTO updateRequestToDto(UpdateCategoryRequest updateCategoryRequest);
}