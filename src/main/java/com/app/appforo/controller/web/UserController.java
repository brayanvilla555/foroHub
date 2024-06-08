package com.app.appforo.controller.web;

import com.app.appforo.dto.UserDto;
import com.app.appforo.persistence.entity.User;
import com.app.appforo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<Page<UserDto>> getUsers(Pageable pageable) {
        return ResponseEntity.ok(userService.findAll(pageable));
    }


    @PostMapping("/")
    public ResponseEntity<UserDto> addUser(@RequestBody User user) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.save(user));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> findById(@PathVariable String userId) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(userService.findById(userId));
    }

    @DeleteMapping("/{userId}")
    public Map<String, String> delete(@PathVariable String userId) {
        return userService.delete(userId);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> update(@RequestBody User user, @PathVariable String userId) {

        return ResponseEntity
                .status(HttpStatus.MULTI_STATUS)
                .body(userService.update(user, userId));
    }

}
