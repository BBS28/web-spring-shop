package com.epam.learn.shchehlov.webspringshop.mappers;

import com.epam.learn.shchehlov.webspringshop.dto.UserDto;
import com.epam.learn.shchehlov.webspringshop.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper{

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "firstName", source = "dto.firstName")
    @Mapping(target = "lastName", source = "dto.lastName")
    @Mapping(target = "email", source = "dto.email")
    @Mapping(target = "login", source = "dto.login")
    @Mapping(target = "password", source = "dto.password")
    @Mapping(target = "mailing", source = "dto.mailing")
    @Mapping(target = "role", ignore = true)
    User toUser(UserDto dto);

    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "login", source = "user.login")
    @Mapping(target = "password", source = "user.password")
    @Mapping(target = "mailing", source = "user.mailing")
    @BeanMapping(ignoreByDefault = true)
    UserDto toDto(User user);
}
