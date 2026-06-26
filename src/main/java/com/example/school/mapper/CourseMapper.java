package com.example.school.mapper;

import com.example.school.entity.Course;
import com.example.school.vo.CourseStatisticsVO;
import com.example.school.vo.CourseWithTeacherVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface CourseMapper {

    // 新增课程
    int insertCourse(Course course);

    // 查询所有课程
    List<Course> selectAllCourses();

    // 根据ID查询课程
    Course selectCourseById(Long id);

    // 根据名称模糊查询课程
    List<Course> selectCoursesByName(String name);

    // 根据学分查询课程
    List<Course> selectCoursesByCredit(@Param("credit") BigDecimal credit);

    // 根据学期查询课程
    List<Course> selectCoursesBySemester(String semester);

    // 多条件查询课程（课程名称、教师姓名、学分）
    List<CourseWithTeacherVO> searchCourses(@Param("name") String name,
                                            @Param("teacherName") String teacherName,
                                            @Param("credit") BigDecimal credit);

    // 更新课程
    int updateCourse(Course course);

    // 删除课程
    int deleteCourse(Long id);

    // 获取课程成绩统计（连接查询：课程 + 成绩）
    CourseStatisticsVO selectCourseStatistics(Long courseId);
}
