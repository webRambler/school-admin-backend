package com.example.school.service.impl;

import com.example.school.common.BusinessException;
import com.example.school.common.ResultCode;
import com.example.school.entity.Teacher;
import com.example.school.mapper.TeacherMapper;
import com.example.school.service.ITeacherService;
import com.example.school.service.RedisService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TeacherServiceImpl implements ITeacherService {

    private final TeacherMapper teacherMapper;
    private final RedisService redisService;

    private static final String TEACHER_KEY_PREFIX = "teacher:";
    private static final long TEACHER_CACHE_TTL = 30;

    public TeacherServiceImpl(TeacherMapper teacherMapper, RedisService redisService) {
        this.teacherMapper = teacherMapper;
        this.redisService = redisService;
    }

    @Override
    public Teacher createTeacher(Teacher teacher) {
        teacherMapper.insertTeacher(teacher);
        redisService.set(TEACHER_KEY_PREFIX + teacher.getId(), teacher, TEACHER_CACHE_TTL, TimeUnit.MINUTES);
        return teacher;
    }

    @Override
    public List<Teacher> getAllTeachers() {
        return teacherMapper.selectAllTeachers();
    }

    @Override
    public Teacher getTeacherById(Long id) {
        String cacheKey = TEACHER_KEY_PREFIX + id;
        Object cached = redisService.get(cacheKey);
        if (cached instanceof Teacher) {
            return (Teacher) cached;
        }
        Teacher teacher = teacherMapper.selectTeacherById(id);
        if (teacher == null) {
            throw new BusinessException(ResultCode.TEACHER_NOT_FOUND);
        }
        redisService.set(cacheKey, teacher, TEACHER_CACHE_TTL, TimeUnit.MINUTES);
        return teacher;
    }

    @Override
    public List<Teacher> getTeachersByName(String name) {
        return teacherMapper.selectTeachersByName(name);
    }

    @Override
    public List<Teacher> getTeachersByTitle(String title) {
        return teacherMapper.selectTeachersByTitle(title);
    }

    @Override
    public Teacher updateTeacher(Long id, Teacher teacherDetails) {
        Teacher teacher = getTeacherById(id);
        if (teacherDetails.getCollegeId() != null) {
            teacher.setCollegeId(teacherDetails.getCollegeId());
        }
        if (teacherDetails.getName() != null) {
            teacher.setName(teacherDetails.getName());
        }
        if (teacherDetails.getGender() != null) {
            teacher.setGender(teacherDetails.getGender());
        }
        if (teacherDetails.getAge() != null) {
            teacher.setAge(teacherDetails.getAge());
        }
        if (teacherDetails.getTitle() != null) {
            teacher.setTitle(teacherDetails.getTitle());
        }
        if (teacherDetails.getPhone() != null) {
            teacher.setPhone(teacherDetails.getPhone());
        }
        if (teacherDetails.getEmail() != null) {
            teacher.setEmail(teacherDetails.getEmail());
        }
        teacherMapper.updateTeacher(teacher);
        redisService.set(TEACHER_KEY_PREFIX + id, teacher, TEACHER_CACHE_TTL, TimeUnit.MINUTES);
        return teacher;
    }

    @Override
    public void deleteTeacher(Long id) {
        getTeacherById(id);
        teacherMapper.deleteTeacher(id);
        redisService.delete(TEACHER_KEY_PREFIX + id);
    }
}
