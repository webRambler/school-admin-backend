package com.example.school.service;

import com.example.school.entity.ClassRoom;
import com.example.school.vo.ClassRoomStatisticsVO;

import java.util.List;

public interface IClassRoomService {

    ClassRoom createClassRoom(ClassRoom classRoom);

    List<ClassRoom> getAllClassRooms();

    ClassRoom getClassRoomById(Long id);

    List<ClassRoom> getClassRoomsByName(String name);

    List<ClassRoom> getClassRoomsByGrade(String grade);

    List<ClassRoom> getClassRoomsByMajor(String major);

    ClassRoom updateClassRoom(Long id, ClassRoom classRoomDetails);

    void deleteClassRoom(Long id);

    Long countStudentsByClassRoomId(Long classRoomId);

    ClassRoomStatisticsVO getClassRoomStatistics(Long classRoomId);
}
