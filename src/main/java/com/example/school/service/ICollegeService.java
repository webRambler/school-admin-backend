package com.example.school.service;

import com.example.school.entity.College;
import com.example.school.vo.CollegeWithClassesVO;

import java.util.List;

public interface ICollegeService {

    College createCollege(College college);

    List<College> getAllColleges();

    College getCollegeById(Long id);

    List<College> getCollegesByName(String name);

    College getCollegeByCode(String code);

    College updateCollege(Long id, College collegeDetails);

    void deleteCollege(Long id);

    // ====== 连接查询 ======

    Long countClassesByCollegeId(Long collegeId);

    CollegeWithClassesVO getCollegeStatistics(Long collegeId);
}
