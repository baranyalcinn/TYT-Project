package tyt.product.model.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import tyt.product.controller.request.CreateProductRequest;
import tyt.product.controller.request.UpdateProductRequest;
import tyt.product.model.ProductEntity;
import tyt.product.model.dto.ProductDTO;

/**
 * This interface is used to map between different object types related to the Product.
 * It uses the MapStruct library to generate the implementation at compile time.
 */
@Mapper
public interface ProductMapper {

    /**
     * Singleton instance of the ProductMapper.
     */
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    /**
     * Maps a ProductDTO to a ProductEntity.
     * The 'active' field of the ProductEntity is set to true by default.
     *
     * @param productDTO the ProductDTO to map from.
     * @return the mapped ProductEntity.
     *
     */
    @Mapping(target = "active", defaultValue = "true")
    @Mapping(source = "productDTO.categoryId", target = "category.id")
    @Mapping(source = "productDTO.categoryName", target = "category.name")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    ProductEntity toEntity(ProductDTO productDTO);

    /**
     * Maps a ProductEntity to a ProductDTO.
     * The 'isActive' field of the ProductDTO is set to true.
     *
     * @param productEntity the ProductEntity to map from.
     * @return the mapped ProductDTO.
     */
    @Mapping(source = "productEntity.id", target = "id")
    @Mapping(source = "productEntity.active", target = "isActive")
    @Mapping(source = "productEntity.category.id", target = "categoryId")
    @Mapping(source = "productEntity.category.name", target = "categoryName")
    ProductDTO toDTO(ProductEntity productEntity);

    /**
     * Maps a CreateProductRequest to a ProductDTO.
     *
     * @param createProductRequest the CreateProductRequest to map from.
     * @return the mapped ProductDTO.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    ProductDTO createRequestToDto(CreateProductRequest createProductRequest);

    /**
     * Maps an UpdateProductRequest to a ProductDTO.
     *
     * @param updateProductRequest the UpdateProductRequest to map from.
     * @return the mapped ProductDTO.
     */
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "categoryName", ignore = true)
    ProductDTO updateRequestToDto(UpdateProductRequest updateProductRequest);

    /**
     * Updates a ProductEntity from a ProductDTO.
     *
     * @param dto    the ProductDTO to map from.
     * @param entity the ProductEntity to map to.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "category", ignore = true)
    void updateProductFromDTO(ProductDTO dto, @MappingTarget ProductEntity entity);
}