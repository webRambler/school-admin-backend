package com.example.school.repository;

import com.example.school.entity.User;

public interface IUserRepository {

    User selectByUsername(String username);

    int insert(User user);
}
