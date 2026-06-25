package com.example.school.controller;

import com.example.school.common.PageResult;
import com.example.school.common.Result;
import com.example.school.dto.StudentCreateRequest;
import com.example.school.dto.StudentUpdateRequest;
import com.example.school.entity.Student;
import com.example.school.service.IStudentService;
import com.example.school.vo.StudentCourseScoreVO;
import com.example.school.vo.StudentWithClassVO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final IStudentService studentService;

    public StudentController(IStudentService studentService) {
        this.studentService = studentService;
    }

    // ========== 基础 CRUD ==========

    // 增：创建学生
    @PostMapping
    public Result<Student> createStudent(@Valid @RequestBody StudentCreateRequest request) {
        Student student = new Student();
        BeanUtils.copyProperties(request, student);
        Student created = studentService.createStudent(student);
        return Result.success("创建成功", created);
    }

    // 查：分页获取学生列表
    @GetMapping
    public Result<PageResult<Student>> getAllStudents(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return Result.success(PageResult.of(studentService.getAllStudents()));
    }

    // 查：根据ID获取学生
    @GetMapping("/{id}")
    public Result<Student> getStudentById(@PathVariable Long id) {
        return Result.success(studentService.getStudentById(id));
    }

    // 查：按姓名模糊搜索学生
    @GetMapping("/search")
    public Result<List<Student>> getStudentsByName(@RequestParam String name) {
        return Result.success(studentService.getStudentsByName(name));
    }

    // 查：按班级ID查询学生
    @GetMapping("/class/{classId}")
    public Result<List<Student>> getStudentsByClassId(@PathVariable Long classId) {
        return Result.success(studentService.getStudentsByClassId(classId));
    }

    // 查：按性别查询学生
    @GetMapping("/gender/{gender}")
    public Result<List<Student>> getStudentsByGender(@PathVariable String gender) {
        return Result.success(studentService.getStudentsByGender(gender));
    }

    // 改：更新学生
    @PutMapping("/{id}")
    public Result<Student> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentUpdateRequest request) {
        Student studentDetails = new Student();
        BeanUtils.copyProperties(request, studentDetails);
        Student updated = studentService.updateStudent(id, studentDetails);
        return Result.success("更新成功", updated);
    }

    // 删：删除学生（同时删除其成绩）
    @DeleteMapping("/{id}")
    public Result<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return Result.success("删除成功", null);
    }

    // ========== 连接查询 ==========

    // 查：获取学生及其班级信息（LEFT JOIN class_room）
    @GetMapping("/{id}/with-class")
    public Result<StudentWithClassVO> getStudentWithClass(@PathVariable Long id) {
        return Result.success(studentService.getStudentWithClass(id));
    }

    // 查：获取班级下所有学生及其班级信息
    @GetMapping("/class/{classId}/with-class")
    public Result<List<StudentWithClassVO>> getStudentsWithClassByClassId(@PathVariable Long classId) {
        return Result.success(studentService.getStudentsWithClassByClassId(classId));
    }

    // 查：分页获取所有学生及其班级信息
    @GetMapping("/with-class")
    public Result<PageResult<StudentWithClassVO>> getAllStudentsWithClass(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return Result.success(PageResult.of(studentService.getAllStudentsWithClass()));
    }

    // 查：获取学生的所有课程和成绩（三表连接：student + score + course）
    @GetMapping("/{id}/courses-scores")
    public Result<List<StudentCourseScoreVO>> getStudentCourseScores(@PathVariable Long id) {
        return Result.success(studentService.getStudentCourseScores(id));
    }

    // 查：统计学生选修课程数量
    @GetMapping("/{id}/course-count")
    public Result<Long> countCoursesByStudentId(@PathVariable Long id) {
        return Result.success(studentService.countCoursesByStudentId(id));
    }
}
