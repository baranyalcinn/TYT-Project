package tyt.sales.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import tyt.sales.model.CartEntity;
import tyt.sales.model.ProductEntity;
import tyt.sales.model.dto.CartDTO;
import tyt.sales.model.dto.ProductDTO;

import java.util.List;

@Mapper(uses = ProductMapper.class)
public interface CartMapper {

    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.price", target = "productPrice")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "totalPrice", target = "totalPrice")
    CartDTO fromEntity(CartEntity cartEntity);

    List<CartDTO> fromEntities(List<CartEntity> cartItems);

    @Mapping(source = "product.id", target = "product.id")
    @Mapping(source = "product.id", target = "id")
    @Mapping(target = "quantity", constant = "0")
    CartEntity toEntity(ProductDTO product, ProductEntity productEntity);
}