package com.example.school.service.impl;

import com.example.school.common.BusinessException;
import com.example.school.common.ResultCode;
import com.example.school.dto.CollegeTeacherCreateRequest;
import com.example.school.entity.CollegeTeacher;
import com.example.school.repository.ICollegeRepository;
import com.example.school.repository.ICollegeTeacherRepository;
import com.example.school.repository.ITeacherRepository;
import com.example.school.service.ICollegeTeacherService;
import com.example.school.vo.CollegeTeacherVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollegeTeacherServiceImpl implements ICollegeTeacherService {

    private final ICollegeTeacherRepository collegeTeacherRepository;
    private final ICollegeRepository collegeRepository;
    private final ITeacherRepository teacherRepository;

    public CollegeTeacherServiceImpl(ICollegeTeacherRepository collegeTeacherRepository,
                                     ICollegeRepository collegeRepository,
                                     ITeacherRepository teacherRepository) {
        this.collegeTeacherRepository = collegeTeacherRepository;
        this.collegeRepository = collegeRepository;
        this.teacherRepository = teacherRepository;
    }

    @Override
    public CollegeTeacher createRelation(CollegeTeacherCreateRequest request) {
        // 校验学院是否存在
        if (collegeRepository.selectCollegeById(request.getCollegeId()) == null) {
            throw new BusinessException(ResultCode.COLLEGE_NOT_FOUND);
        }
        // 校验教师是否存在
        if (teacherRepository.selectTeacherById(request.getTeacherId()) == null) {
            throw new BusinessException(ResultCode.TEACHER_NOT_FOUND);
        }
        // 校验关系是否已存在
        CollegeTeacher existing = collegeTeacherRepository.selectByCollegeAndTeacher(
                request.getCollegeId(), request.getTeacherId());
        if (existing != null) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "该教师已在该学院中，请勿重复添加");
        }
        // 若标记为院长，校验该学院是否已有院长
        if (Integer.valueOf(1).equals(request.getIsDean())) {
            CollegeTeacherVO dean = collegeTeacherRepository.selectDeanByCollegeId(request.getCollegeId());
            if (dean != null) {
                throw new BusinessException(ResultCode.BAD_REQUEST,
                        "该学院已有院长（教师ID=" + dean.getTeacherId() + "），请先取消原院长再设置");
            }
        }
        CollegeTeacher collegeTeacher = new CollegeTeacher();
        collegeTeacher.setCollegeId(request.getCollegeId());
        collegeTeacher.setTeacherId(request.getTeacherId());
        collegeTeacher.setIsDean(request.getIsDean() == null ? 0 : request.getIsDean());
        collegeTeacherRepository.insert(collegeTeacher);
        return collegeTeacher;
    }

    @Override
    public CollegeTeacher getById(Long id) {
        CollegeTeacher ct = collegeTeacherRepository.selectById(id);
        if (ct == null) {
            throw new BusinessException(ResultCode.COLLEGE_TEACHER_NOT_FOUND);
        }
        return ct;
    }

    @Override
    public List<CollegeTeacherVO> getTeachersByCollege(Long collegeId) {
        if (collegeRepository.selectCollegeById(collegeId) == null) {
            throw new BusinessException(ResultCode.COLLEGE_NOT_FOUND);
        }
        return collegeTeacherRepository.selectByCollegeId(collegeId);
    }

    @Override
    public List<CollegeTeacherVO> getCollegesByTeacher(Long teacherId) {
        if (teacherRepository.selectTeacherById(teacherId) == null) {
            throw new BusinessException(ResultCode.TEACHER_NOT_FOUND);
        }
        return collegeTeacherRepository.selectByTeacherId(teacherId);
    }

    @Override
    public void setIsDean(Long id, Integer isDean) {
        CollegeTeacher ct = getById(id);
        // 若设置为院长，检查该学院是否已有其他院长
        if (Integer.valueOf(1).equals(isDean)) {
            CollegeTeacherVO existingDean = collegeTeacherRepository.selectDeanByCollegeId(ct.getCollegeId());
            if (existingDean != null && !existingDean.getId().equals(id)) {
                throw new BusinessException(ResultCode.BAD_REQUEST,
                        "该学院已有院长（教师ID=" + existingDean.getTeacherId() + "），请先取消原院长再设置");
            }
        }
        collegeTeacherRepository.updateIsDean(id, isDean);
    }

    @Override
    public void deleteRelation(Long id) {
        getById(id); // 确保存在
        collegeTeacherRepository.deleteById(id);
    }

    @Override
    public Long countTeachersByCollege(Long collegeId) {
        if (collegeRepository.selectCollegeById(collegeId) == null) {
            throw new BusinessException(ResultCode.COLLEGE_NOT_FOUND);
        }
        return collegeTeacherRepository.countByCollegeId(collegeId);
    }
}
