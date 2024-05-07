package tyt.sales.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import tyt.sales.model.dto.OfferDTO;
import tyt.sales.model.OfferEntity;

@Mapper
public interface OfferMapper {

    OfferMapper INSTANCE = Mappers.getMapper(OfferMapper.class);

    @Mapping(source = "name", target = "name")
    OfferDTO fromEntity(OfferEntity offerEntity);

    @Mapping(source = "name", target = "name")
    OfferEntity toEntity(OfferDTO offerDTO);
}