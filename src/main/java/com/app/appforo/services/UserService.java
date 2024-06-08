package com.app.appforo.services;

import com.app.appforo.dto.UserDto;
import com.app.appforo.persistence.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface UserService {
    Page<UserDto> findAll(Pageable pageable);

    UserDto save(User user);

    UserDto findById(String userId);

    Map<String, String> delete(String userId);

    UserDto update(User user, String userId);
}
