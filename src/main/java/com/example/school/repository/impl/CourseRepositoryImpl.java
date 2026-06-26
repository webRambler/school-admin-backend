package com.example.school.repository.impl;

import com.example.school.entity.Course;
import com.example.school.mapper.CourseMapper;
import com.example.school.repository.ICourseRepository;
import com.example.school.vo.CourseStatisticsVO;
import com.example.school.vo.CourseWithTeacherVO;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class CourseRepositoryImpl implements ICourseRepository {

    private final CourseMapper courseMapper;

    public CourseRepositoryImpl(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    @Override
    public int insertCourse(Course course) {
        return courseMapper.insertCourse(course);
    }

    @Override
    public List<Course> selectAllCourses() {
        return courseMapper.selectAllCourses();
    }

    @Override
    public Course selectCourseById(Long id) {
        return courseMapper.selectCourseById(id);
    }

    @Override
    public List<Course> selectCoursesByName(String name) {
        return courseMapper.selectCoursesByName(name);
    }

    @Override
    public List<Course> selectCoursesByCredit(BigDecimal credit) {
        return courseMapper.selectCoursesByCredit(credit);
    }

    @Override
    public List<Course> selectCoursesBySemester(String semester) {
        return courseMapper.selectCoursesBySemester(semester);
    }

    @Override
    public List<CourseWithTeacherVO> searchCourses(String name, String teacherName, BigDecimal credit) {
        return courseMapper.searchCourses(name, teacherName, credit);
    }

    @Override
    public int updateCourse(Course course) {
        return courseMapper.updateCourse(course);
    }

    @Override
    public int deleteCourse(Long id) {
        return courseMapper.deleteCourse(id);
    }

    @Override
    public CourseStatisticsVO selectCourseStatistics(Long courseId) {
        return courseMapper.selectCourseStatistics(courseId);
    }
}
