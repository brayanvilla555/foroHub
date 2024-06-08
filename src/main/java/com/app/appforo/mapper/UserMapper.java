package com.app.appforo.mapper;

import com.app.appforo.dto.UserDto;
import com.app.appforo.persistence.entity.User;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDto toUserDto(User user) {
        UserDto userDto = new UserDto(
                user.getId(),
                user.getName(),
                user.getUsername()
        );
        return userDto;
    }

    public static List<UserDto> toListUserDto(Page<User> users) {

        List<UserDto> userDtos = new ArrayList<>();
        users.stream().forEach(
                user -> userDtos.add(toUserDto(user))
        );

        return userDtos;
    }

    public static User toUser(UserDto userDto) {
        User user = new User();

        return user.builder()
                .id(userDto.id())
                .name(userDto.name())
                .username(userDto.username())
                .build();
    }

}
