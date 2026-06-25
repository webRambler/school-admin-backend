package com.example.school.controller;

import com.example.school.common.PageResult;
import com.example.school.common.Result;
import com.example.school.dto.TeacherCreateRequest;
import com.example.school.dto.TeacherUpdateRequest;
import com.example.school.entity.Teacher;
import com.example.school.service.ITeacherService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final ITeacherService teacherService;

    public TeacherController(ITeacherService teacherService) {
        this.teacherService = teacherService;
    }

    // ========== 基础 CRUD ==========

    // 增：创建教师
    @PostMapping
    public Result<Teacher> createTeacher(@Valid @RequestBody TeacherCreateRequest request) {
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(request, teacher);
        Teacher created = teacherService.createTeacher(teacher);
        return Result.success("创建成功", created);
    }

    // 查：分页获取教师列表
    @GetMapping
    public Result<PageResult<Teacher>> getAllTeachers(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return Result.success(PageResult.of(teacherService.getAllTeachers()));
    }

    // 查：根据ID获取教师
    @GetMapping("/{id}")
    public Result<Teacher> getTeacherById(@PathVariable Long id) {
        return Result.success(teacherService.getTeacherById(id));
    }

    // 查：按姓名模糊搜索教师
    @GetMapping("/search")
    public Result<List<Teacher>> getTeachersByName(@RequestParam String name) {
        return Result.success(teacherService.getTeachersByName(name));
    }

    // 查：按院系查询教师
    @GetMapping("/department/{department}")
    public Result<List<Teacher>> getTeachersByDepartment(@PathVariable String department) {
        return Result.success(teacherService.getTeachersByDepartment(department));
    }

    // 查：按职称查询教师
    @GetMapping("/title/{title}")
    public Result<List<Teacher>> getTeachersByTitle(@PathVariable String title) {
        return Result.success(teacherService.getTeachersByTitle(title));
    }

    // 改：更新教师信息
    @PutMapping("/{id}")
    public Result<Teacher> updateTeacher(@PathVariable Long id, @Valid @RequestBody TeacherUpdateRequest request) {
        Teacher teacherDetails = new Teacher();
        BeanUtils.copyProperties(request, teacherDetails);
        Teacher updated = teacherService.updateTeacher(id, teacherDetails);
        return Result.success("更新成功", updated);
    }

    // 删：删除教师
    @DeleteMapping("/{id}")
    public Result<Void> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return Result.success("删除成功", null);
    }
}
