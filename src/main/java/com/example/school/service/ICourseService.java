package com.example.school.service;

import com.example.school.entity.Course;
import com.example.school.vo.CourseStatisticsVO;
import com.example.school.vo.CourseWithTeacherVO;

import java.math.BigDecimal;
import java.util.List;

public interface ICourseService {

    Course createCourse(Course course);

    List<Course> getAllCourses();

    Course getCourseById(Long id);

    List<Course> getCoursesByName(String name);

    List<Course> getCoursesByCredit(BigDecimal credit);

    List<Course> getCoursesBySemester(String semester);

    List<CourseWithTeacherVO> searchCourses(String name, String teacherName, BigDecimal credit);

    Course updateCourse(Long id, Course courseDetails);

    void deleteCourse(Long id);

    CourseStatisticsVO getCourseStatistics(Long courseId);
}
