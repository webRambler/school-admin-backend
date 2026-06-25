package com.example.school.service.impl;

import com.example.school.common.BusinessException;
import com.example.school.common.ResultCode;
import com.example.school.entity.College;
import com.example.school.repository.ICollegeRepository;
import com.example.school.service.ICollegeService;
import com.example.school.service.RedisService;
import com.example.school.vo.CollegeWithClassesVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CollegeServiceImpl implements ICollegeService {

    private final ICollegeRepository collegeRepository;
    private final RedisService redisService;

    private static final String COLLEGE_KEY_PREFIX = "college:";
    private static final long COLLEGE_CACHE_TTL = 30;

    public CollegeServiceImpl(ICollegeRepository collegeRepository, RedisService redisService) {
        this.collegeRepository = collegeRepository;
        this.redisService = redisService;
    }

    @Override
    public College createCollege(College college) {
        // 检查学院代码是否已存在
        College existing = collegeRepository.selectCollegeByCode(college.getCode());
        if (existing != null) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "学院代码 [" + college.getCode() + "] 已存在");
        }
        collegeRepository.insertCollege(college);
        redisService.set(COLLEGE_KEY_PREFIX + college.getId(), college, COLLEGE_CACHE_TTL, TimeUnit.MINUTES);
        return college;
    }

    @Override
    public List<College> getAllColleges() {
        return collegeRepository.selectAllColleges();
    }

    @Override
    public College getCollegeById(Long id) {
        String cacheKey = COLLEGE_KEY_PREFIX + id;
        Object cached = redisService.get(cacheKey);
        if (cached instanceof College) {
            return (College) cached;
        }
        College college = collegeRepository.selectCollegeById(id);
        if (college == null) {
            throw new BusinessException(ResultCode.COLLEGE_NOT_FOUND);
        }
        redisService.set(cacheKey, college, COLLEGE_CACHE_TTL, TimeUnit.MINUTES);
        return college;
    }

    @Override
    public List<College> getCollegesByName(String name) {
        return collegeRepository.selectCollegesByName(name);
    }

    @Override
    public College getCollegeByCode(String code) {
        College college = collegeRepository.selectCollegeByCode(code);
        if (college == null) {
            throw new BusinessException(ResultCode.COLLEGE_NOT_FOUND, "学院代码 [" + code + "] 不存在");
        }
        return college;
    }

    @Override
    public College updateCollege(Long id, College collegeDetails) {
        College college = getCollegeById(id);
        if (collegeDetails.getName() != null) {
            college.setName(collegeDetails.getName());
        }
        if (collegeDetails.getCode() != null) {
            // 检查新代码是否与其他学院冲突
            College existing = collegeRepository.selectCollegeByCode(collegeDetails.getCode());
            if (existing != null && !existing.getId().equals(id)) {
                throw new BusinessException(ResultCode.BAD_REQUEST, "学院代码 [" + collegeDetails.getCode() + "] 已存在");
            }
            college.setCode(collegeDetails.getCode());
        }
        if (collegeDetails.getDescription() != null) {
            college.setDescription(collegeDetails.getDescription());
        }
        if (collegeDetails.getDean() != null) {
            college.setDean(collegeDetails.getDean());
        }
        if (collegeDetails.getPhone() != null) {
            college.setPhone(collegeDetails.getPhone());
        }
        collegeRepository.updateCollege(college);
        redisService.set(COLLEGE_KEY_PREFIX + id, college, COLLEGE_CACHE_TTL, TimeUnit.MINUTES);
        return college;
    }

    @Override
    public void deleteCollege(Long id) {
        getCollegeById(id);
        // 检查学院下是否还有班级
        Long classCount = collegeRepository.countClassesByCollegeId(id);
        if (classCount > 0) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "该学院下还有" + classCount + "个班级，无法删除");
        }
        collegeRepository.deleteCollege(id);
        redisService.delete(COLLEGE_KEY_PREFIX + id);
    }

    @Override
    public Long countClassesByCollegeId(Long collegeId) {
        getCollegeById(collegeId); // 确保学院存在
        return collegeRepository.countClassesByCollegeId(collegeId);
    }

    @Override
    public CollegeWithClassesVO getCollegeStatistics(Long collegeId) {
        getCollegeById(collegeId); // 确保学院存在
        return collegeRepository.selectCollegeStatistics(collegeId);
    }
}
