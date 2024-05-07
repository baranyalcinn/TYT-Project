package tyt.sales.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import tyt.sales.model.OrderProductEntity;
import tyt.sales.model.dto.OrderProductDTO;

/**
 * This is a MapStruct mapper interface for OrderProduct.
 * MapStruct is a code generator that simplifies the implementation of mappings between Java bean types.
 * This mapper provides methods to convert between OrderProductEntity and OrderProductDTO.
 */
@Mapper(componentModel = "spring")
public interface OrderProductMapper {

    // An instance of the mapper that can be used anywhere in the code.
    OrderProductMapper INSTANCE = Mappers.getMapper(OrderProductMapper.class);

    /**
     * This method maps from OrderProductEntity to OrderProductDTO.
     * It maps the id of the order and product from the entity to the DTO.
     *
     * @param orderProductEntity The OrderProductEntity to be converted.
     * @return The converted OrderProductDTO.
     */
    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    OrderProductDTO fromEntity(OrderProductEntity orderProductEntity);

    /**
     * This method maps from OrderProductDTO to OrderProductEntity.
     * It maps the id of the order and product from the DTO to the entity.
     *
     * @param orderProductDTO The OrderProductDTO to be converted.
     * @return The converted OrderProductEntity.
     */
    @Mapping(source = "orderId", target = "order.id")
    @Mapping(source = "productId", target = "product.id")
    OrderProductEntity toEntity(OrderProductDTO orderProductDTO);

}