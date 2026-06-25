package com.example.school.repository.impl;

import com.example.school.entity.College;
import com.example.school.mapper.CollegeMapper;
import com.example.school.repository.ICollegeRepository;
import com.example.school.vo.CollegeWithClassesVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CollegeRepositoryImpl implements ICollegeRepository {

    private final CollegeMapper collegeMapper;

    public CollegeRepositoryImpl(CollegeMapper collegeMapper) {
        this.collegeMapper = collegeMapper;
    }

    @Override
    public int insertCollege(College college) {
        return collegeMapper.insertCollege(college);
    }

    @Override
    public List<College> selectAllColleges() {
        return collegeMapper.selectAllColleges();
    }

    @Override
    public College selectCollegeById(Long id) {
        return collegeMapper.selectCollegeById(id);
    }

    @Override
    public List<College> selectCollegesByName(String name) {
        return collegeMapper.selectCollegesByName(name);
    }

    @Override
    public College selectCollegeByCode(String code) {
        return collegeMapper.selectCollegeByCode(code);
    }

    @Override
    public int updateCollege(College college) {
        return collegeMapper.updateCollege(college);
    }

    @Override
    public int deleteCollege(Long id) {
        return collegeMapper.deleteCollege(id);
    }

    @Override
    public Long countClassesByCollegeId(Long collegeId) {
        return collegeMapper.countClassesByCollegeId(collegeId);
    }

    @Override
    public CollegeWithClassesVO selectCollegeStatistics(Long collegeId) {
        return collegeMapper.selectCollegeStatistics(collegeId);
    }
}
