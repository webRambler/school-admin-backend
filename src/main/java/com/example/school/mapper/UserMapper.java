package com.example.school.mapper;

import com.example.school.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    // 根据用户名查询用户
    User selectByUsername(String username);

    // 新增用户
    int insert(User user);
}
