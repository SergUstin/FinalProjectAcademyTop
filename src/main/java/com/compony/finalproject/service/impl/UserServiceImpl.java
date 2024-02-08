package com.compony.finalproject.service.impl;

import com.compony.finalproject.dto.UserDto;
import com.compony.finalproject.mappers.UserMapper;
import com.compony.finalproject.model.User;
import com.compony.finalproject.repository.UserRepository;
import com.compony.finalproject.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public void registerUser(UserDto userDTO) {
        User user = userMapper.toEntity(userDTO);
        userRepository.save(user);
    }

    @Override
    public void updateUser(UserDto userDTO) {
        User user = userMapper.toEntity(userDTO);
        userRepository.save(user);
    }

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getById(Long id) {
        return userMapper.toDto(userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Landlord not found with id: " + id)));
    }
}
