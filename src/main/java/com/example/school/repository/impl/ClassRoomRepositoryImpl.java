package com.example.school.repository.impl;

import com.example.school.entity.ClassRoom;
import com.example.school.mapper.ClassRoomMapper;
import com.example.school.repository.IClassRoomRepository;
import com.example.school.vo.ClassRoomStatisticsVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClassRoomRepositoryImpl implements IClassRoomRepository {

    private final ClassRoomMapper classRoomMapper;

    public ClassRoomRepositoryImpl(ClassRoomMapper classRoomMapper) {
        this.classRoomMapper = classRoomMapper;
    }

    @Override
    public int insertClassRoom(ClassRoom classRoom) {
        return classRoomMapper.insertClassRoom(classRoom);
    }

    @Override
    public List<ClassRoom> selectAllClassRooms() {
        return classRoomMapper.selectAllClassRooms();
    }

    @Override
    public ClassRoom selectClassRoomById(Long id) {
        return classRoomMapper.selectClassRoomById(id);
    }

    @Override
    public List<ClassRoom> selectClassRoomsByName(String name) {
        return classRoomMapper.selectClassRoomsByName(name);
    }

    @Override
    public List<ClassRoom> selectClassRoomsByGrade(String grade) {
        return classRoomMapper.selectClassRoomsByGrade(grade);
    }

    @Override
    public List<ClassRoom> selectClassRoomsByMajor(String major) {
        return classRoomMapper.selectClassRoomsByMajor(major);
    }

    @Override
    public int updateClassRoom(ClassRoom classRoom) {
        return classRoomMapper.updateClassRoom(classRoom);
    }

    @Override
    public int deleteClassRoom(Long id) {
        return classRoomMapper.deleteClassRoom(id);
    }

    @Override
    public Long countStudentsByClassRoomId(Long classRoomId) {
        return classRoomMapper.countStudentsByClassRoomId(classRoomId);
    }

    @Override
    public ClassRoomStatisticsVO selectClassRoomStatistics(Long classRoomId) {
        return classRoomMapper.selectClassRoomStatistics(classRoomId);
    }
}
