package tyt.sales.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import tyt.sales.model.ProductEntity;
import tyt.sales.model.dto.ProductDTO;

/**
 * This interface is used to map between ProductEntity and ProductDTO.
 * It uses MapStruct library for the mapping.
 */
@Mapper
public interface ProductMapper {

    /**
     * An instance of the ProductMapper.
     */
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    /**
     * This method is used to convert a ProductEntity to a ProductDTO.
     * It ignores the 'categoryName' and 'isActive' fields during the mapping.
     *
     * @param productEntity The ProductEntity to be converted.
     * @return The converted ProductDTO.
     */
    @Mapping(target = "categoryName", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    ProductDTO fromEntity(ProductEntity productEntity);

    /**
     * This method is used to convert a ProductDTO to a ProductEntity.
     * It ignores the 'orderProducts' field during the mapping.
     *
     * @param productDTO The ProductDTO to be converted.
     * @return The converted ProductEntity.
     */
    @Mapping(target = "orderProducts", ignore = true)
    ProductEntity toEntity(ProductDTO productDTO);
}