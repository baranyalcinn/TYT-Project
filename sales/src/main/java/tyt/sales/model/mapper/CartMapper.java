package tyt.sales.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import tyt.sales.model.CartEntity;
import tyt.sales.model.ProductEntity;
import tyt.sales.model.dto.CartDTO;
import tyt.sales.model.dto.ProductDTO;

import java.util.List;

/**
 * This interface is used to map between CartEntity and CartDTO objects.
 * It uses the MapStruct library to generate the implementation at compile time.
 */
@Mapper(uses = ProductMapper.class)
public interface CartMapper {

    /**
     * An instance of the CartMapper interface.
     * This instance is used to call the methods defined in this interface.
     */
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    /**
     * Maps a CartEntity object to a CartDTO object.
     *
     * @param cartEntity The CartEntity object to be mapped.
     * @return The mapped CartDTO object.
     */
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.price", target = "productPrice")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "totalPrice", target = "totalPrice")
    CartDTO fromEntity(CartEntity cartEntity);

    /**
     * Maps a list of CartEntity objects to a list of CartDTO objects.
     *
     * @param cartItems The list of CartEntity objects to be mapped.
     * @return The list of mapped CartDTO objects.
     */
    List<CartDTO> fromEntities(List<CartEntity> cartItems);

    /**
     * Maps a ProductDTO object and a ProductEntity object to a CartEntity object.
     *
     * @param product The ProductDTO object to be mapped.
     * @param productEntity The ProductEntity object to be mapped.
     * @return The mapped CartEntity object.
     */
    @Mapping(source = "product.id", target = "product.id")
    @Mapping(source = "product.id", target = "id")
    @Mapping(target = "quantity", constant = "0")
    @Mapping(target = "totalPrice", expression = "java(product.getPrice() * 0)")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.price", target = "productPrice")
    CartEntity toEntity(ProductDTO product, ProductEntity productEntity);
}