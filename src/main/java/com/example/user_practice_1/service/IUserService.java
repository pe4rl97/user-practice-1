package com.example.user_practice_1.service;

import com.example.user_practice_1.dto.UserDto;

import java.util.List;

public interface IUserService {
    public List<UserDto> findAll();
    public UserDto findById(int userId);
    public UserDto save(UserDto user);
    public UserDto update(int userId, UserDto userDto);
    public UserDto partialUpdate(int userId, UserDto userDto);
    public void deleteById(int userId);
}
