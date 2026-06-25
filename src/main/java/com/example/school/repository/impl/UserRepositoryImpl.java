package com.example.school.repository.impl;

import com.example.school.entity.User;
import com.example.school.mapper.UserMapper;
import com.example.school.repository.IUserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements IUserRepository {

    private final UserMapper userMapper;

    public UserRepositoryImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }
}
