package com.example.school.service.impl;

import com.example.school.common.BusinessException;
import com.example.school.common.ResultCode;
import com.example.school.entity.ClassRoom;
import com.example.school.mapper.ClassRoomMapper;
import com.example.school.service.IClassRoomService;
import com.example.school.service.RedisService;
import com.example.school.vo.ClassRoomStatisticsVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ClassRoomServiceImpl implements IClassRoomService {

    private final ClassRoomMapper classRoomMapper;
    private final RedisService redisService;

    private static final String CLASSROOM_KEY_PREFIX = "classroom:";
    private static final long CLASSROOM_CACHE_TTL = 30;

    public ClassRoomServiceImpl(ClassRoomMapper classRoomMapper, RedisService redisService) {
        this.classRoomMapper = classRoomMapper;
        this.redisService = redisService;
    }

    @Override
    public ClassRoom createClassRoom(ClassRoom classRoom) {
        classRoomMapper.insertClassRoom(classRoom);
        redisService.set(CLASSROOM_KEY_PREFIX + classRoom.getId(), classRoom, CLASSROOM_CACHE_TTL, TimeUnit.MINUTES);
        return classRoom;
    }

    @Override
    public List<ClassRoom> getAllClassRooms() {
        return classRoomMapper.selectAllClassRooms();
    }

    @Override
    public ClassRoom getClassRoomById(Long id) {
        // 先查缓存
        String cacheKey = CLASSROOM_KEY_PREFIX + id;
        Object cached = redisService.get(cacheKey);
        if (cached instanceof ClassRoom) {
            return (ClassRoom) cached;
        }
        // 缓存未命中，查数据库
        ClassRoom classRoom = classRoomMapper.selectClassRoomById(id);
        if (classRoom == null) {
            throw new BusinessException(ResultCode.CLASSROOM_NOT_FOUND);
        }
        redisService.set(cacheKey, classRoom, CLASSROOM_CACHE_TTL, TimeUnit.MINUTES);
        return classRoom;
    }

    @Override
    public List<ClassRoom> getClassRoomsByName(String name) {
        return classRoomMapper.selectClassRoomsByName(name);
    }

    @Override
    public List<ClassRoom> getClassRoomsByGrade(String grade) {
        return classRoomMapper.selectClassRoomsByGrade(grade);
    }

    @Override
    public List<ClassRoom> getClassRoomsByMajor(String major) {
        return classRoomMapper.selectClassRoomsByMajor(major);
    }

    @Override
    public ClassRoom updateClassRoom(Long id, ClassRoom classRoomDetails) {
        ClassRoom classRoom = getClassRoomById(id);
        if (classRoomDetails.getCollegeId() != null) {
            classRoom.setCollegeId(classRoomDetails.getCollegeId());
        }
        if (classRoomDetails.getName() != null) {
            classRoom.setName(classRoomDetails.getName());
        }
        if (classRoomDetails.getGrade() != null) {
            classRoom.setGrade(classRoomDetails.getGrade());
        }
        if (classRoomDetails.getMajor() != null) {
            classRoom.setMajor(classRoomDetails.getMajor());
        }
        if (classRoomDetails.getTeacher() != null) {
            classRoom.setTeacher(classRoomDetails.getTeacher());
        }
        classRoomMapper.updateClassRoom(classRoom);
        redisService.set(CLASSROOM_KEY_PREFIX + id, classRoom, CLASSROOM_CACHE_TTL, TimeUnit.MINUTES);
        return classRoom;
    }

    @Override
    public void deleteClassRoom(Long id) {
        getClassRoomById(id);
        // 检查班级下是否还有学生
        Long studentCount = classRoomMapper.countStudentsByClassRoomId(id);
        if (studentCount > 0) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "该班级下还有" + studentCount + "名学生，无法删除");
        }
        classRoomMapper.deleteClassRoom(id);
        redisService.delete(CLASSROOM_KEY_PREFIX + id);
    }

    @Override
    public Long countStudentsByClassRoomId(Long classRoomId) {
        return classRoomMapper.countStudentsByClassRoomId(classRoomId);
    }

    @Override
    public ClassRoomStatisticsVO getClassRoomStatistics(Long classRoomId) {
        getClassRoomById(classRoomId); // 确保班级存在
        return classRoomMapper.selectClassRoomStatistics(classRoomId);
    }
}
