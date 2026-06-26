package com.example.school.controller;

import com.example.school.common.PageResult;
import com.example.school.common.Result;
import com.example.school.dto.CourseCreateRequest;
import com.example.school.dto.CourseUpdateRequest;
import com.example.school.entity.Course;
import com.example.school.service.ICourseService;
import com.example.school.vo.CourseStatisticsVO;
import com.example.school.vo.CourseWithTeacherVO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final ICourseService courseService;

    public CourseController(ICourseService courseService) {
        this.courseService = courseService;
    }

    // ========== 基础 CRUD ==========

    // 增：创建课程
    @PostMapping
    public Result<Course> createCourse(@Valid @RequestBody CourseCreateRequest request) {
        Course course = new Course();
        BeanUtils.copyProperties(request, course);
        Course created = courseService.createCourse(course);
        return Result.success("创建成功", created);
    }

    // 查：分页获取课程列表
    @GetMapping
    public Result<PageResult<Course>> getAllCourses(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return Result.success(PageResult.of(courseService.getAllCourses()));
    }

    // 查：根据ID获取课程
    @GetMapping("/{id}")
    public Result<Course> getCourseById(@PathVariable Long id) {
        return Result.success(courseService.getCourseById(id));
    }

    // 查：多条件查询课程（课程名称模糊、教师姓名模糊、学分精确）
    @GetMapping("/search")
    public Result<PageResult<CourseWithTeacherVO>> searchCourses(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String teacherName,
            @RequestParam(required = false) BigDecimal credit,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return Result.success(PageResult.of(courseService.searchCourses(name, teacherName, credit)));
    }

    // 改：更新课程
    @PutMapping("/{id}")
    public Result<Course> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseUpdateRequest request) {
        Course courseDetails = new Course();
        BeanUtils.copyProperties(request, courseDetails);
        Course updated = courseService.updateCourse(id, courseDetails);
        return Result.success("更新成功", updated);
    }

    // 删：删除课程
    @DeleteMapping("/{id}")
    public Result<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return Result.success("删除成功", null);
    }

    // ========== 关联查询 ==========

    // 查：获取课程成绩统计（连接查询：课程 + 成绩）
    @GetMapping("/{id}/statistics")
    public Result<CourseStatisticsVO> getCourseStatistics(@PathVariable Long id) {
        return Result.success(courseService.getCourseStatistics(id));
    }
}
