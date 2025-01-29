package com.example.user_practice_1.service;

import com.example.user_practice_1.dto.UserDto;
import com.example.user_practice_1.entity.User;
import com.example.user_practice_1.exception.IncompleteRequestBodyException;
import com.example.user_practice_1.exception.UserNotFoundException;
import com.example.user_practice_1.repository.IUserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService{
    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
        this.modelMapper = new ModelMapper();

        TypeMap<User, UserDto> userToUserDtoTypeMap = modelMapper.createTypeMap(User.class, UserDto.class);
        userToUserDtoTypeMap.addMapping(User::getId, UserDto::setUserId);
        userToUserDtoTypeMap.addMapping(User::getAge, UserDto::setUserAge);
        userToUserDtoTypeMap.addMapping(User::getSalary, UserDto::setUserSalary);
        userToUserDtoTypeMap.addMapping(User::getName, UserDto::setUserName);
        userToUserDtoTypeMap.addMapping(User::getMobileNumber, UserDto::setUserMobileNumber);

        TypeMap<UserDto, User> userDtoToUserTypeMap = modelMapper.createTypeMap(UserDto.class, User.class);
        userDtoToUserTypeMap.addMapping(UserDto::getUserId, User::setId);
        userDtoToUserTypeMap.addMapping(UserDto::getUserAge, User::setAge);
        userDtoToUserTypeMap.addMapping(UserDto::getUserSalary, User::setSalary);
        userDtoToUserTypeMap.addMapping(UserDto::getUserName, User::setName);
        userDtoToUserTypeMap.addMapping(UserDto::getUserMobileNumber, User::setMobileNumber);
    }


    @Override
    public UserDto save(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }

    @Override
    public UserDto findById(int userId) {
        Optional<User> foundUser = userRepository.findById(userId);
        UserDto userDto;

        if (foundUser.isPresent()) {
            userDto = modelMapper.map(foundUser.get(), UserDto.class);
        } else {
            throw new UserNotFoundException("User with id " + userId + " not found.");
        }

        return userDto;
    }

    @Override
    public void update(int userId, UserDto updateUserDto) {
        UserDto matchingUserDto = findById(userId);
        if (matchingUserDto == null) {
            throw new UserNotFoundException("User with id " + updateUserDto.getUserId() + " not found.");
        }

        if (updateUserDto.getUserName() == null || updateUserDto.getUserAge() == 0
            || updateUserDto.getUserSalary() == 0 || updateUserDto.getUserMobileNumber() == null) {
            throw new IncompleteRequestBodyException("Request body must contain the following fields: userName, userAge, userSalary, and userMobileNumber");
        }

        updateUserDto.setUserId(userId);
        userRepository.save(modelMapper.map(updateUserDto, User.class));
    }

    @Override
    public void partialUpdate(int userId, UserDto updateUserDto) {
        UserDto matchingUserDto = findById(userId);
        if (matchingUserDto != null) {
            if (updateUserDto.getUserName() != null) {
                matchingUserDto.setUserName(updateUserDto.getUserName());
            }
            if (updateUserDto.getUserAge() != 0) {
                matchingUserDto.setUserAge(updateUserDto.getUserAge());
            }
            if (updateUserDto.getUserSalary() != 0) {
                matchingUserDto.setUserSalary(updateUserDto.getUserSalary());
            }
            if (updateUserDto.getUserMobileNumber() != null) {
                matchingUserDto.setUserMobileNumber(updateUserDto.getUserMobileNumber());
            }
        } else {
            throw new UserNotFoundException("User with id" + updateUserDto + " not found.");
        }
        userRepository.save(modelMapper.map(matchingUserDto, User.class));
    }

    @Override
    public boolean deleteById(int userId) {
        UserDto user = findById(userId);
        if (user == null) {
            return false;
        }
        userRepository.deleteById(userId);
        return true;
    }
}
