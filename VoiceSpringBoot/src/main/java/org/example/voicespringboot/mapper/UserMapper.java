package org.example.voicespringboot.mapper;

import org.example.voicespringboot.dto.RegisterDto;
import org.example.voicespringboot.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = UserMapper.class
)
public interface UserMapper {

    User toEntity(final RegisterDto registerDto);

    RegisterDto toDto(final User user);

    List<RegisterDto> toDtoList(final List<User> userList);

}
