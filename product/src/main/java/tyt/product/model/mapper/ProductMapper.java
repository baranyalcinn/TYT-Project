package tyt.product.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import tyt.product.controller.request.CreateProductRequest;
import tyt.product.controller.request.UpdateProductRequest;
import tyt.product.model.ProductEntity;
import tyt.product.model.dto.ProductDTO;

@Mapper
public interface ProductMapper {


    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "active", defaultValue = "true")
    ProductEntity toEntity(ProductDTO productDTO);

    @Mapping(target = "isActive", constant = "true")
    ProductDTO toDTO(ProductEntity productEntity);

    ProductDTO createRequestToDto(CreateProductRequest createProductRequest);

    ProductDTO updateRequestToDto(UpdateProductRequest updateProductRequest);


}
