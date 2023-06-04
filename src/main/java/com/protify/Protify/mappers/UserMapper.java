package com.protify.Protify.mappers;

import com.protify.Protify.dtos.UserDto;
import com.protify.Protify.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    @Mapping(target = "playlists", ignore = true)
    @Mapping(target = "id", ignore = true)
    User create(UserDto data);

    @Mapping(target = "playlists", ignore = true)
    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget User user, UserDto data);
}
