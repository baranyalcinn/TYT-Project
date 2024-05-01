package tyt.record.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import tyt.record.model.OrderEntity;
import tyt.record.model.dto.OrderDTO;

import java.util.UUID;

/**
 * This interface is used to map OrderEntity to OrderDTO and vice versa.
 * It uses MapStruct, a code generator that greatly simplifies the implementation of mappings between Java bean types.
 */
@Mapper(uses = OrderProductMapper.class)
public interface OrderMapper {

    /**
     * An instance of the OrderMapper interface.
     * MapStruct provides out of the box support for the generation of a mapper instance.
     */
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    /**
     * This method is used to map OrderEntity to OrderDTO.
     * It uses @Mapping annotations to define the mapping between fields.
     * The 'uuidToString' method is used to convert UUID to String for the 'orderNumber' field.
     *
     * @param orderEntity The OrderEntity object to be converted to OrderDTO.
     * @return The OrderDTO object after conversion.
     */
    @Mapping(source = "orderNumber", target = "orderNumber", qualifiedByName = "uuidToString")
    @Mapping(source = "orderProducts", target = "orderProducts")
    OrderDTO toDto(OrderEntity orderEntity);

    /**
     * This method is used to convert UUID to String.
     * It is named 'uuidToString' and used in the 'toDto' method for the 'orderNumber' field.
     *
     * @param value The UUID to be converted to String.
     * @return The String representation of the UUID, or null if the input is null.
     */
    @Named("uuidToString")
    default String map(UUID value) {
        return value != null ? value.toString() : null;
    }
}