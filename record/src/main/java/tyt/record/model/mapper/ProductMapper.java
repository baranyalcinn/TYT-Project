package tyt.record.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import tyt.record.model.ProductEntity;
import tyt.record.model.dto.ProductDTO;

/**
 * This is a MapStruct mapper interface for converting between ProductEntity and ProductDTO.
 * MapStruct is a code generator that simplifies the implementation of mappings between Java bean types.
 *
 * @Mapper is a MapStruct annotation that indicates this is a mapper interface.
 */
@Mapper
public interface ProductMapper {

    /**
     * INSTANCE is a singleton instance of the ProductMapper.
     * Mappers.getMapper(ProductMapper.class) is a MapStruct method that provides the implementation of the mapper.
     */
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    /**
     * Method to convert a ProductEntity to a ProductDTO.
     *
     * @param productEntity the ProductEntity to be converted.
     * @return the converted ProductDTO.
     */
    ProductDTO toDto(ProductEntity productEntity);
}