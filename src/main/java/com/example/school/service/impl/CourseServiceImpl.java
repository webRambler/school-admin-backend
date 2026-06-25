package com.example.school.service.impl;

import com.example.school.common.BusinessException;
import com.example.school.common.ResultCode;
import com.example.school.entity.Course;
import com.example.school.repository.ICourseRepository;
import com.example.school.service.ICourseService;
import com.example.school.service.RedisService;
import com.example.school.vo.CourseStatisticsVO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CourseServiceImpl implements ICourseService {

    private final ICourseRepository courseRepository;
    private final RedisService redisService;

    private static final String COURSE_KEY_PREFIX = "course:";
    private static final long COURSE_CACHE_TTL = 30;

    public CourseServiceImpl(ICourseRepository courseRepository, RedisService redisService) {
        this.courseRepository = courseRepository;
        this.redisService = redisService;
    }

    @Override
    public Course createCourse(Course course) {
        courseRepository.insertCourse(course);
        redisService.set(COURSE_KEY_PREFIX + course.getId(), course, COURSE_CACHE_TTL, TimeUnit.MINUTES);
        return course;
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.selectAllCourses();
    }

    @Override
    public Course getCourseById(Long id) {
        String cacheKey = COURSE_KEY_PREFIX + id;
        Object cached = redisService.get(cacheKey);
        if (cached instanceof Course) {
            return (Course) cached;
        }
        Course course = courseRepository.selectCourseById(id);
        if (course == null) {
            throw new BusinessException(ResultCode.COURSE_NOT_FOUND);
        }
        redisService.set(cacheKey, course, COURSE_CACHE_TTL, TimeUnit.MINUTES);
        return course;
    }

    @Override
    public List<Course> getCoursesByName(String name) {
        return courseRepository.selectCoursesByName(name);
    }

    @Override
    public List<Course> getCoursesByCredit(BigDecimal credit) {
        return courseRepository.selectCoursesByCredit(credit);
    }

    @Override
    public List<Course> getCoursesBySemester(String semester) {
        return courseRepository.selectCoursesBySemester(semester);
    }

    @Override
    public Course updateCourse(Long id, Course courseDetails) {
        Course course = getCourseById(id);
        if (courseDetails.getTeacherId() != null) {
            course.setTeacherId(courseDetails.getTeacherId());
        }
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
        courseRepository.updateCourse(course);
        redisService.set(COURSE_KEY_PREFIX + id, course, COURSE_CACHE_TTL, TimeUnit.MINUTES);
        return course;
    }

    @Override
    public void deleteCourse(Long id) {
        getCourseById(id);
        courseRepository.deleteCourse(id);
        redisService.delete(COURSE_KEY_PREFIX + id);
    }

    @Override
    public CourseStatisticsVO getCourseStatistics(Long courseId) {
        getCourseById(courseId); // 确保课程存在
        return courseRepository.selectCourseStatistics(courseId);
    }
}
