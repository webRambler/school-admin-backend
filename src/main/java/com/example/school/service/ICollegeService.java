package com.example.school.service;

import com.example.school.dto.CollegeCreateRequest;
import com.example.school.dto.CollegeUpdateRequest;
import com.example.school.entity.College;
import com.example.school.vo.CollegeWithClassesVO;
import com.example.school.vo.CollegeWithDeanVO;

import java.util.List;

public interface ICollegeService {

    College createCollege(CollegeCreateRequest collegeCreateRequest);

    List<College> getAllColleges();

    College getCollegeById(Long id);

    CollegeWithDeanVO getCollegeDetailById(Long id);

    List<College> getCollegesByName(String name);

    List<CollegeWithDeanVO> searchColleges(String name, String code);

    College getCollegeByCode(String code);

    College updateCollege(Long id, CollegeUpdateRequest collegeUpdateRequest);

    void deleteCollege(Long id);

    // ====== 连接查询 ======

    Long countClassesByCollegeId(Long collegeId);

    CollegeWithClassesVO getCollegeStatistics(Long collegeId);

    void updateCollegeWithDean(Long collegeId, Long deanId, Integer isDean);
}
