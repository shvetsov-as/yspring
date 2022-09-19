package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.user.UserEntity;
import com.edu.ulab.app.mapper.UserEntityMapper;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.storage.StorageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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
    public UserDto createUser(UserDto userDto) {

        UserEntity userEntity = userEntityMapper.userDtoToUserEntity(userDto);
        storageUtils.save(userEntity);

        log.info("userEntity saved: {}", userEntity);

        userDto = userEntityMapper.userEntityToUserDto(userEntity);
        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        UserEntity entity = storageUtils.findById(userDto.getId());
        if (null != userDto.getFullName()) {
            entity.setFullName(userDto.getFullName());
        }
        if (null != userDto.getTitle()) {
            entity.setTitle(userDto.getTitle());
        }
        if (0 != userDto.getAge()) {
            entity.setAge(userDto.getAge());
        }
        return userEntityMapper.userEntityToUserDto(entity);
    }

    @Override
    public UserDto getUserById(Long id) {
        UserEntity entity = storageUtils.findById(id);
        return userEntityMapper.userEntityToUserDto(entity);
    }

    @Override
    public void deleteUserById(Long id) {
        UserEntity entity = storageUtils.findById(id);
        storageUtils.delete(entity.getId());
    }
}
