package tyt.sales.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import tyt.sales.model.dto.ProductDTO;
import tyt.sales.model.ProductEntity;

import java.util.List;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "categoryName", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    ProductDTO fromEntity(ProductEntity productEntity);

    @Mapping(target = "orderProducts", ignore = true)
    ProductEntity toEntity(ProductDTO productDTO);

    List<ProductDTO> fromEntities(List<ProductEntity> products);
}