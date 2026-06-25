package com.example.school.mapper;

import com.example.school.entity.ClassRoom;
import com.example.school.vo.ClassRoomStatisticsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ClassRoomMapper {

    // 新增班级
    int insertClassRoom(ClassRoom classRoom);

    // 查询所有班级
    List<ClassRoom> selectAllClassRooms();

    // 根据ID查询班级
    ClassRoom selectClassRoomById(Long id);

    // 根据名称模糊查询班级
    List<ClassRoom> selectClassRoomsByName(String name);

    // 根据年级查询班级
    List<ClassRoom> selectClassRoomsByGrade(String grade);

    // 根据专业查询班级
    List<ClassRoom> selectClassRoomsByMajor(String major);

    // 更新班级
    int updateClassRoom(ClassRoom classRoom);

    // 删除班级
    int deleteClassRoom(Long id);

    // 统计班级下学生人数
    Long countStudentsByClassRoomId(Long classRoomId);

    // 获取班级成绩统计（连接查询：班级 + 学生 + 成绩）
    ClassRoomStatisticsVO selectClassRoomStatistics(Long classRoomId);
}
