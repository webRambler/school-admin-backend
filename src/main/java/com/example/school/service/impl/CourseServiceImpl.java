package com.example.school.service.impl;

import com.example.school.common.BusinessException;
import com.example.school.common.ResultCode;
import com.example.school.entity.Course;
import com.example.school.mapper.CourseMapper;
import com.example.school.service.ICourseService;
import com.example.school.service.RedisService;
import com.example.school.vo.CourseStatisticsVO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CourseServiceImpl implements ICourseService {

    private final CourseMapper courseMapper;
    private final RedisService redisService;

    private static final String COURSE_KEY_PREFIX = "course:";
    private static final long COURSE_CACHE_TTL = 30;

    public CourseServiceImpl(CourseMapper courseMapper, RedisService redisService) {
        this.courseMapper = courseMapper;
        this.redisService = redisService;
    }

    @Override
    public Course createCourse(Course course) {
        courseMapper.insertCourse(course);
        redisService.set(COURSE_KEY_PREFIX + course.getId(), course, COURSE_CACHE_TTL, TimeUnit.MINUTES);
        return course;
    }

    @Override
    public List<Course> getAllCourses() {
        return courseMapper.selectAllCourses();
    }

    @Override
    public Course getCourseById(Long id) {
        String cacheKey = COURSE_KEY_PREFIX + id;
        Object cached = redisService.get(cacheKey);
        if (cached instanceof Course) {
            return (Course) cached;
        }
        Course course = courseMapper.selectCourseById(id);
        if (course == null) {
            throw new BusinessException(ResultCode.COURSE_NOT_FOUND);
        }
        redisService.set(cacheKey, course, COURSE_CACHE_TTL, TimeUnit.MINUTES);
        return course;
    }

    @Override
    public List<Course> getCoursesByName(String name) {
        return courseMapper.selectCoursesByName(name);
    }

    @Override
    public List<Course> getCoursesByCredit(BigDecimal credit) {
        return courseMapper.selectCoursesByCredit(credit);
    }

    @Override
    public List<Course> getCoursesBySemester(String semester) {
        return courseMapper.selectCoursesBySemester(semester);
    }

    @Override
    public Course updateCourse(Long id, Course courseDetails) {
        Course course = getCourseById(id);
        if (courseDetails.getName() != null) {
            course.setName(courseDetails.getName());
        }
        if (courseDetails.getCredit() != null) {
            course.setCredit(courseDetails.getCredit());
        }
        if (courseDetails.getHours() != null) {
            course.setHours(courseDetails.getHours());
        }
        if (courseDetails.getSemester() != null) {
            course.setSemester(courseDetails.getSemester());
        }
        courseMapper.updateCourse(course);
        redisService.set(COURSE_KEY_PREFIX + id, course, COURSE_CACHE_TTL, TimeUnit.MINUTES);
        return course;
    }

    @Override
    public void deleteCourse(Long id) {
        getCourseById(id);
        courseMapper.deleteCourse(id);
        redisService.delete(COURSE_KEY_PREFIX + id);
    }

    @Override
    public CourseStatisticsVO getCourseStatistics(Long courseId) {
        getCourseById(courseId); // 确保课程存在
        return courseMapper.selectCourseStatistics(courseId);
    }
}
