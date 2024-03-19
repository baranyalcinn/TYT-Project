package tyt.management.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import tyt.management.controller.request.CreateUserRequest;
import tyt.management.controller.request.UpdateUserRequest;
import tyt.management.model.UserEntity;
import tyt.management.model.dto.UserDTO;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "active", defaultValue = "true")
    UserEntity toEntity(UserDTO userDTO);

    @Mapping(target = "isActive", constant = "true")
    UserDTO toDTO(UserEntity userEntity);

    UserDTO createRequestToDto(CreateUserRequest createUserRequest);

    UserDTO updateRequestToDto(UpdateUserRequest updateUserRequest);

}
