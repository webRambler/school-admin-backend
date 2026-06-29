package com.example.school.repository;

import com.example.school.entity.CollegeTeacher;
import com.example.school.vo.CollegeTeacherVO;

import java.util.List;

public interface ICollegeTeacherRepository {

    int insert(CollegeTeacher collegeTeacher);

    CollegeTeacher selectById(Long id);

    CollegeTeacher selectByCollegeAndTeacher(Long collegeId, Long teacherId);

    List<CollegeTeacherVO> selectByCollegeId(Long collegeId);

    List<CollegeTeacherVO> selectByTeacherId(Long teacherId);

    CollegeTeacherVO selectDeanByCollegeId(Long collegeId);

    int updateIsDean(Long id, Integer isDean);

    int deleteById(Long id);

    int deleteByCollegeId(Long collegeId);

    int deleteByTeacherId(Long teacherId);

    Long countByCollegeId(Long collegeId);
}
