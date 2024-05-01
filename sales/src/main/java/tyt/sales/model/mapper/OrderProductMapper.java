package tyt.sales.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import tyt.sales.model.dto.OrderProductDTO;
import tyt.sales.model.OrderProductEntity;

import java.util.List;

@Mapper
public interface OrderProductMapper {

    OrderProductMapper INSTANCE = Mappers.getMapper(OrderProductMapper.class);

    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "product.id", target = "productId")
    OrderProductDTO fromEntity(OrderProductEntity orderProductEntity);

    @Mapping(source = "orderId", target = "order.id")
    @Mapping(source = "productId", target = "product.id")
    List<OrderProductDTO> fromEntities(List<OrderProductEntity> orderProductEntities);
}