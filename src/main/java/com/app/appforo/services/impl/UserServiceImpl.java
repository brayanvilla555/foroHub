package com.app.appforo.services.impl;

import com.app.appforo.dto.UserDto;
import com.app.appforo.mapper.UserMapper;
import com.app.appforo.persistence.entity.User;
import com.app.appforo.persistence.repository.UserRepository;
import com.app.appforo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);

        List<UserDto> userDtos = UserMapper.toListUserDto(users);

        return new PageImpl<>(userDtos, pageable, users.getTotalElements());
    }

    @Override
    public UserDto save(User user) {
        if(user == null || user.getId() != null){
            throw new IllegalArgumentException("En la peticion no estás mandando el mody");
        }

        //encriptar la contraseña
        return UserMapper.toUserDto(userRepository.save(user));
    }

    @Override
    public UserDto findById(String userId) {
        try {
            Integer idUser = Integer.parseInt(userId);
            Optional<User> user = userRepository.findById(idUser);

            if(!user.isPresent()){
                throw new IllegalArgumentException("No se encontro el usuario");
            }else{
                return UserMapper.toUserDto(user.get());
            }
        }catch (NumberFormatException ex){
            throw new IllegalArgumentException("No se puede convertir a número");
        }
    }

    @Override
    public Map<String, String> delete(String userId) {
        Map<String, String> response = new HashMap<>();
        try {
            Integer idUser = Integer.parseInt(userId);
            Optional<User> user = userRepository.findById(idUser);

            if(!user.isPresent()){
                response.put("message", "Usuario no encontrado");
                response.put("statusCode", "404");
            }else{
               userRepository.deleteById(idUser);
               response.put("message", "Usuario eliminado");
               response.put("statusCode", "200");
            }
        }catch (NumberFormatException ex){
            throw new IllegalArgumentException("No se puede convertir a número");
        }

        return  response;
    }

    @Override
    public UserDto update(User user, String userId) {
        try {
            if(user.getId() != Integer.parseInt(userId)){
                throw new IllegalArgumentException("Los id no concuerdan");
            }
            Optional<User> findUser = userRepository.findById(user.getId());
            if(!findUser.isPresent()){
                throw new IllegalArgumentException("Usuario no encontrado");
            }
            user.setPassword(findUser.get().getPassword());
            return UserMapper.toUserDto(userRepository.save(user));
        }catch (NumberFormatException ex){
            throw new IllegalArgumentException("No se puede convertir a número");
        }
    }
}
