package com.example.school.repository;

import com.example.school.entity.College;
import com.example.school.vo.CollegeWithClassesVO;

import java.util.List;

public interface ICollegeRepository {

    int insertCollege(College college);

    List<College> selectAllColleges();

    College selectCollegeById(Long id);

    List<College> selectCollegesByName(String name);

    College selectCollegeByCode(String code);

    int updateCollege(College college);

    int deleteCollege(Long id);

    Long countClassesByCollegeId(Long collegeId);

    CollegeWithClassesVO selectCollegeStatistics(Long collegeId);
}
