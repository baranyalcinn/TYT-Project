package tyt.record.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import tyt.record.model.OrderProductEntity;
import tyt.record.model.dto.OrderProductDTO;

/**
 * This is a MapStruct mapper interface for OrderProduct.
 * MapStruct is a code generator that simplifies the implementation of mappings between Java bean types.
 * This interface is used to map between OrderProductEntity and OrderProductDTO.
 */
@Mapper
public interface OrderProductMapper {

    /**
     * This is a singleton instance of the OrderProductMapper.
     * Mappers.getMapper(Class) is a MapStruct method used to get an instance of the mapper.
     */
    OrderProductMapper INSTANCE = Mappers.getMapper(OrderProductMapper.class);

    /**
     * This method is used to map from OrderProductEntity to OrderProductDTO.
     * @param orderProductEntity The OrderProductEntity that needs to be converted to OrderProductDTO.
     * @return OrderProductDTO The mapped OrderProductDTO.
     */
   OrderProductDTO toDTO(OrderProductEntity orderProductEntity);

}