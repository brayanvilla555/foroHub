package com.app.appforo.dto;

import lombok.Builder;

@Builder
public record UserDto(
        Integer id,
        String name,
        String username
        ) {

}



