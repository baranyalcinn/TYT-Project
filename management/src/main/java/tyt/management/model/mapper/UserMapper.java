package tyt.management.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import tyt.management.controller.request.CreateUserRequest;
import tyt.management.controller.request.UpdateUserRequest;
import tyt.management.model.UserEntity;
import tyt.management.model.dto.UserDTO;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity toEntity(UserDTO userDTO);

    UserDTO toDTO(UserEntity userEntity);

    UserDTO createRequestToDto(CreateUserRequest createUserRequest);

    UserDTO updateRequestToDto(UpdateUserRequest updateUserRequest);

}
