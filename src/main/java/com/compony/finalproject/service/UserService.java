package com.compony.finalproject.service;

import com.compony.finalproject.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto getById(Long id);
    List<UserDto> getAll();
    void registerUser(UserDto userDTO);
    void updateUser(UserDto userDTO);
}
