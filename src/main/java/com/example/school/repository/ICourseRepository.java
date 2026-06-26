package com.example.school.repository;

import com.example.school.entity.Course;
import com.example.school.vo.CourseStatisticsVO;
import com.example.school.vo.CourseWithTeacherVO;

import java.math.BigDecimal;
import java.util.List;

public interface ICourseRepository {

    int insertCourse(Course course);

    List<Course> selectAllCourses();

    Course selectCourseById(Long id);

    List<Course> selectCoursesByName(String name);

    List<Course> selectCoursesByCredit(BigDecimal credit);

    List<Course> selectCoursesBySemester(String semester);

    List<CourseWithTeacherVO> searchCourses(String name, String teacherName, BigDecimal credit);

    int updateCourse(Course course);

    int deleteCourse(Long id);

    CourseStatisticsVO selectCourseStatistics(Long courseId);
}
