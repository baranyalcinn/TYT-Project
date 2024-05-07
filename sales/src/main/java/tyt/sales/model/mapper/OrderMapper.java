package tyt.sales.model.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import tyt.sales.repository.OrderRepository;
import tyt.sales.repository.ProductRepository;
import tyt.sales.model.OrderEntity;
import tyt.sales.model.OrderProductEntity;
import tyt.sales.model.ProductEntity;
import tyt.sales.model.dto.OrderDTO;
import tyt.sales.model.dto.OrderProductDTO;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * This interface provides mapping methods between OrderEntity and OrderDTO objects.
 * It uses MapStruct library to generate the implementation at compile time.
 */
@Mapper(componentModel = "spring", uses = {OrderProductMapper.class})
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    /**
     * Maps OrderEntity to OrderDTO.
     * @param orderEntity The OrderEntity object to be converted.
     * @return The converted OrderDTO object.
     */
    @Mapping(source = "orderNumber", target = "orderNumber", qualifiedByName = "uuidToString")
    @Mapping(source = "orderProducts", target = "orderProducts", qualifiedByName = "orderProductListToOrderProductDTOList")
    @Mapping(source = "offer", target = "offer")
    OrderDTO fromEntity(OrderEntity orderEntity);


    /**
     * Converts UUID to String.
     * @param uuid The UUID to be converted.
     * @return The converted String or null if the input is null.
     */
    @Named("uuidToString")
    default String uuidToString(UUID uuid) {
        return uuid != null ? uuid.toString() : null;
    }

    /**
     * Converts a list of OrderProductEntity objects to a list of OrderProductDTO objects.
     * @param orderProducts The list of OrderProductEntity objects to be converted.
     * @return The converted list of OrderProductDTO objects or null if the input is null.
     */
    @Named("orderProductListToOrderProductDTOList")
    default List<OrderProductDTO> orderProductListToOrderProductDTOList(List<OrderProductEntity> orderProducts) {
        return orderProducts != null ? orderProducts.stream().map(this::orderProductEntityToOrderProductDTO).collect(Collectors.toList()) : null;
    }

    /**
     * Converts OrderProductEntity to OrderProductDTO.
     * @param orderProductEntity The OrderProductEntity object to be converted.
     * @return The converted OrderProductDTO object.
     */
    default OrderProductDTO orderProductEntityToOrderProductDTO(OrderProductEntity orderProductEntity) {
        return OrderProductMapper.INSTANCE.fromEntity(orderProductEntity);
    }

    /**
     * Converts OrderDTO to OrderEntity.
     * @param orderDTO The OrderDTO object to be converted.
     * @return The converted OrderEntity object.
     */

    @Mapping(source = "offer", target = "offer")
    OrderEntity orderDtoToOrderEntity(OrderDTO orderDTO);

    /**
     * Converts OrderProductDTO to OrderProductEntity with the help of repositories.
     * @param orderProductDTO The OrderProductDTO object to be converted.
     * @param orderRepository The OrderRepository to fetch OrderEntity.
     * @param productRepository The ProductRepository to fetch ProductEntity.
     * @return The converted OrderProductEntity object.
     */
    @Mapping(source = "orderId", target = "order", qualifiedByName = "mapOrder")
    @Mapping(source = "productId", target = "product", qualifiedByName = "mapProduct")
    @Named("fromDTOWithRepositories")
    OrderProductEntity fromDTO(OrderProductDTO orderProductDTO, @Context OrderRepository orderRepository, @Context ProductRepository productRepository);

    /**
     * Converts a list of OrderProductDTO objects to a list of OrderProductEntity objects.
     * @param orderProductDTOs The list of OrderProductDTO objects to be converted.
     * @return The converted list of OrderProductEntity objects.
     */
    List<OrderProductEntity> fromDTOs(List<OrderProductDTO> orderProductDTOs);

    /**
     * Fetches OrderEntity by id from the repository.
     * @param orderId The id of the OrderEntity to be fetched.
     * @param orderRepository The OrderRepository to fetch OrderEntity.
     * @return The fetched OrderEntity or null if not found.
     */
    @Named("mapOrder")
    default OrderEntity mapOrder(Long orderId, @Context OrderRepository orderRepository) {
        return orderRepository.findById(orderId).orElse(null);
    }

    /**
     * Fetches ProductEntity by id from the repository.
     * @param productId The id of the ProductEntity to be fetched.
     * @param productRepository The ProductRepository to fetch ProductEntity.
     * @return The fetched ProductEntity or null if not found.
     */
    @Named("mapProduct")
    default ProductEntity mapProduct(Long productId, @Context ProductRepository productRepository) {
        return productRepository.findById(productId).orElse(null);
    }

    /**
     * Converts OrderProductDTO to OrderProductEntity without the help of repositories.
     * @param orderProductDTO The OrderProductDTO object to be converted.
     * @return The converted OrderProductEntity object.
     */
    @Mapping(source = "orderId", target = "order.id")
    @Mapping(source = "productId", target = "product.id")
    @Named("fromDTOWithoutRepositories")
    OrderProductEntity fromDTO(OrderProductDTO orderProductDTO);

}