package com.example.school.repository.impl;

import com.example.school.entity.CollegeTeacher;
import com.example.school.mapper.CollegeTeacherMapper;
import com.example.school.repository.ICollegeTeacherRepository;
import com.example.school.vo.CollegeTeacherVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CollegeTeacherRepositoryImpl implements ICollegeTeacherRepository {

    private final CollegeTeacherMapper collegeTeacherMapper;

    public CollegeTeacherRepositoryImpl(CollegeTeacherMapper collegeTeacherMapper) {
        this.collegeTeacherMapper = collegeTeacherMapper;
    }

    @Override
    public int insert(CollegeTeacher collegeTeacher) {
        return collegeTeacherMapper.insert(collegeTeacher);
    }

    @Override
    public CollegeTeacher selectById(Long id) {
        return collegeTeacherMapper.selectById(id);
    }

    @Override
    public CollegeTeacher selectByCollegeAndTeacher(Long collegeId, Long teacherId) {
        return collegeTeacherMapper.selectByCollegeAndTeacher(collegeId, teacherId);
    }

    @Override
    public List<CollegeTeacherVO> selectByCollegeId(Long collegeId) {
        return collegeTeacherMapper.selectByCollegeId(collegeId);
    }

    @Override
    public List<CollegeTeacherVO> selectByTeacherId(Long teacherId) {
        return collegeTeacherMapper.selectByTeacherId(teacherId);
    }

    @Override
    public CollegeTeacherVO selectDeanByCollegeId(Long collegeId) {
        return collegeTeacherMapper.selectDeanByCollegeId(collegeId);
    }

    @Override
    public int updateIsDean(Long id, Integer isDean) {
        return collegeTeacherMapper.updateIsDean(id, isDean);
    }

    @Override
    public int deleteById(Long id) {
        return collegeTeacherMapper.deleteById(id);
    }

    @Override
    public int deleteByCollegeId(Long collegeId) {
        return collegeTeacherMapper.deleteByCollegeId(collegeId);
    }

    @Override
    public int deleteByTeacherId(Long teacherId) {
        return collegeTeacherMapper.deleteByTeacherId(teacherId);
    }

    @Override
    public Long countByCollegeId(Long collegeId) {
        return collegeTeacherMapper.countByCollegeId(collegeId);
    }
}
