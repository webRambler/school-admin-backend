package com.example.school.repository;

import com.example.school.entity.ClassRoom;
import com.example.school.vo.ClassRoomStatisticsVO;

import java.util.List;

public interface IClassRoomRepository {

    int insertClassRoom(ClassRoom classRoom);

    List<ClassRoom> selectAllClassRooms();

    ClassRoom selectClassRoomById(Long id);

    List<ClassRoom> selectClassRoomsByName(String name);

    List<ClassRoom> selectClassRoomsByGrade(String grade);

    List<ClassRoom> selectClassRoomsByMajor(String major);

    int updateClassRoom(ClassRoom classRoom);

    int deleteClassRoom(Long id);

    Long countStudentsByClassRoomId(Long classRoomId);

    ClassRoomStatisticsVO selectClassRoomStatistics(Long classRoomId);
}
