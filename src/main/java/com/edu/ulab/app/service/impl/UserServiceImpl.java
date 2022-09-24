package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.user.UserEntity;
import com.edu.ulab.app.exception.UserNotFoundException;
import com.edu.ulab.app.mapper.UserEntityMapper;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.storage.StorageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final StorageUtils<UserEntity, Long> storageUtils;
    private final UserEntityMapper userEntityMapper;

    public UserServiceImpl(
            @Qualifier("userStorageUtilsImpl") StorageUtils<UserEntity, Long> storageUtils,
            UserEntityMapper userEntityMapper) {
        this.storageUtils = storageUtils;
        this.userEntityMapper = userEntityMapper;
    }

    @Override
    public UserDto createUser(@NotNull UserDto userDto) {
        UserEntity userEntity = userEntityMapper.userDtoToUserEntity(userDto);
        storageUtils.save(userEntity);

        userDto = userEntityMapper.userEntityToUserDto(userEntity);
        log.info("UserService create UserDto {}", userDto);
        return userDto;
    }

    @Override
    public UserDto updateUser(@NotNull UserDto userDto) {
        UserEntity entity = storageUtils
                .findById(userDto.getId())
                .orElseThrow(
                        () -> new UserNotFoundException(userDto.getId()));
        if (null != userDto.getFullName()) {
            entity.setFullName(userDto.getFullName());
        }
        if (null != userDto.getTitle()) {
            entity.setTitle(userDto.getTitle());
        }
        if (0 != userDto.getAge()) {
            entity.setAge(userDto.getAge());
        }
        log.info("UserService update User {}", entity);
        return userEntityMapper.userEntityToUserDto(entity);
    }

    @Override
    public UserDto getUserById(@NotNull Long id) {
        UserEntity entity = storageUtils.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        log.info("UserService get User By Id entity {}", entity);
        return userEntityMapper.userEntityToUserDto(entity);
    }

    @Override
    public void deleteUserById(@NotNull Long id) {
        UserEntity entity = storageUtils.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        log.info("UserService delete entity {}", entity);
        storageUtils.delete(entity.getId());
    }
}
