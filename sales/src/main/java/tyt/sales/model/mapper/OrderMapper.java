package tyt.sales.model.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import tyt.sales.database.OrderRepository;
import tyt.sales.database.ProductRepository;
import tyt.sales.model.OrderEntity;
import tyt.sales.model.OrderProductEntity;
import tyt.sales.model.ProductEntity;
import tyt.sales.model.dto.OrderDTO;
import tyt.sales.model.dto.OrderProductDTO;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {OrderProductMapper.class})
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "orderNumber", target = "orderNumber", qualifiedByName = "uuidToString")
    @Mapping(source = "orderProducts", target = "orderProducts", qualifiedByName = "orderProductListToOrderProductDTOList")
    OrderDTO fromEntity(OrderEntity orderEntity);

    List<OrderDTO> fromEntities(List<OrderEntity> orderEntities);

    @Named("uuidToString")
    default String uuidToString(UUID uuid) {
        return uuid != null ? uuid.toString() : null;
    }

    @Named("orderProductListToOrderProductDTOList")
    default List<OrderProductDTO> orderProductListToOrderProductDTOList(List<OrderProductEntity> orderProducts) {
        return orderProducts != null ? orderProducts.stream().map(this::orderProductEntityToOrderProductDTO).collect(Collectors.toList()) : null;
    }

    default OrderProductDTO orderProductEntityToOrderProductDTO(OrderProductEntity orderProductEntity) {
        return OrderProductMapper.INSTANCE.fromEntity(orderProductEntity);
    }

    OrderEntity orderDtoToOrderEntity(OrderDTO orderDTO);

    @Mapping(source = "orderId", target = "order", qualifiedByName = "mapOrder")
    @Mapping(source = "productId", target = "product", qualifiedByName = "mapProduct")
    OrderProductEntity fromDTO(OrderProductDTO orderProductDTO, @Context OrderRepository orderRepository, @Context ProductRepository productRepository);

    List<OrderProductEntity> fromDTOs(List<OrderProductDTO> orderProductDTOs);

    @Named("mapOrder")
    default OrderEntity mapOrder(Long orderId, @Context OrderRepository orderRepository) {
        return orderRepository.findById(orderId).orElse(null);
    }

    @Named("mapProduct")
    default ProductEntity mapProduct(Long productId, @Context ProductRepository productRepository) {
        return productRepository.findById(productId).orElse(null);
    }


    @Mapping(source = "orderId", target = "order.id")
    @Mapping(source = "productId", target = "product.id")
    OrderProductEntity fromDTO(OrderProductDTO orderProductDTO);

}
