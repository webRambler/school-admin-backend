package com.example.school.service.impl;

import com.example.school.common.BusinessException;
import com.example.school.common.CacheConstants;
import com.example.school.common.ResultCode;
import com.example.school.entity.Teacher;
import com.example.school.repository.ITeacherRepository;
import com.example.school.service.ITeacherService;
import com.example.school.service.RedisService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TeacherServiceImpl implements ITeacherService {

    private final ITeacherRepository teacherRepository;
    private final RedisService redisService;

    private static final String TEACHER_KEY_PREFIX = CacheConstants.TEACHER_PREFIX;
    private static final long TEACHER_CACHE_TTL = CacheConstants.ENTITY_TTL_MINUTES;

    public TeacherServiceImpl(ITeacherRepository teacherRepository, RedisService redisService) {
        this.teacherRepository = teacherRepository;
        this.redisService = redisService;
    }

    @Override
    @Transactional
    public Teacher createTeacher(Teacher teacher) {
        teacherRepository.insertTeacher(teacher);
        redisService.set(TEACHER_KEY_PREFIX + teacher.getId(), teacher, TEACHER_CACHE_TTL, TimeUnit.MINUTES);
        return teacher;
    }

    @Override
    public List<Teacher> getAllTeachers() {
        return teacherRepository.selectAllTeachers();
    }

    @Override
    public Teacher getTeacherById(Long id) {
        String cacheKey = TEACHER_KEY_PREFIX + id;
        Object cached = redisService.get(cacheKey);
        if (cached instanceof Teacher) {
            return (Teacher) cached;
        }
        Teacher teacher = teacherRepository.selectTeacherById(id);
        if (teacher == null) {
            throw new BusinessException(ResultCode.TEACHER_NOT_FOUND);
        }
        redisService.set(cacheKey, teacher, TEACHER_CACHE_TTL, TimeUnit.MINUTES);
        return teacher;
    }

    @Override
    public List<Teacher> getTeachersByName(String name) {
        return teacherRepository.selectTeachersByName(name);
    }

    @Override
    public List<Teacher> getTeachersByTitle(String title) {
        return teacherRepository.selectTeachersByTitle(title);
    }

    @Override
    public List<Teacher> searchTeachers(String name, Long collegeId) {
        return teacherRepository.searchTeachers(name, collegeId);
    }

    @Override
    @Transactional
    public Teacher updateTeacher(Long id, Teacher teacherDetails) {
        Teacher teacher = getTeacherById(id);
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
        teacherRepository.updateTeacher(teacher);
        redisService.set(TEACHER_KEY_PREFIX + id, teacher, TEACHER_CACHE_TTL, TimeUnit.MINUTES);
        return teacher;
    }

    @Override
    @Transactional
    public void deleteTeacher(Long id) {
        getTeacherById(id);
        teacherRepository.deleteTeacher(id);
        redisService.delete(TEACHER_KEY_PREFIX + id);
    }
}
