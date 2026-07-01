package com.example.school.controller;

import com.example.school.common.PageResult;
import com.example.school.common.Result;
import com.example.school.dto.ClassRoomCreateRequest;
import com.example.school.dto.ClassRoomUpdateRequest;
import com.example.school.entity.ClassRoom;
import com.example.school.service.IClassRoomService;
import com.example.school.vo.ClassRoomStatisticsVO;
import com.example.school.utils.PageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/classrooms")
public class ClassRoomController {

    private final IClassRoomService classRoomService;

    public ClassRoomController(IClassRoomService classRoomService) {
        this.classRoomService = classRoomService;
    }

    // ========== 基础 CRUD ==========

    // 增：创建班级
    @PostMapping
    public Result<ClassRoom> createClassRoom(@Valid @RequestBody ClassRoomCreateRequest request) {
        ClassRoom classRoom = new ClassRoom();
        BeanUtils.copyProperties(request, classRoom);
        ClassRoom created = classRoomService.createClassRoom(classRoom);
        return Result.success("创建成功", created);
    }

    // 查：分页获取班级列表
    @GetMapping
    public Result<PageResult<ClassRoom>> getAllClassRooms(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        PageUtils.startPage(pageNum, pageSize);
        return Result.success(PageResult.of(classRoomService.getAllClassRooms()));
    }

    // 查：根据ID获取班级
    @GetMapping("/{id}")
    public Result<ClassRoom> getClassRoomById(@PathVariable Long id) {
        return Result.success(classRoomService.getClassRoomById(id));
    }

    // 查：按名称模糊搜索班级（分页）
    @GetMapping("/search")
    public Result<PageResult<ClassRoom>> getClassRoomsByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        PageUtils.startPage(pageNum, pageSize);
        return Result.success(PageResult.of(classRoomService.getClassRoomsByName(name)));
    }

    // 查：按年级查询班级（分页）
    @GetMapping("/grade/{grade}")
    public Result<PageResult<ClassRoom>> getClassRoomsByGrade(
            @PathVariable String grade,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        PageUtils.startPage(pageNum, pageSize);
        return Result.success(PageResult.of(classRoomService.getClassRoomsByGrade(grade)));
    }

    // 查：按专业查询班级（分页）
    @GetMapping("/major/{major}")
    public Result<PageResult<ClassRoom>> getClassRoomsByMajor(
            @PathVariable String major,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        PageUtils.startPage(pageNum, pageSize);
        return Result.success(PageResult.of(classRoomService.getClassRoomsByMajor(major)));
    }

    // 改：更新班级
    @PutMapping("/{id}")
    public Result<ClassRoom> updateClassRoom(@PathVariable Long id, @Valid @RequestBody ClassRoomUpdateRequest request) {
        ClassRoom classRoomDetails = new ClassRoom();
        BeanUtils.copyProperties(request, classRoomDetails);
        ClassRoom updated = classRoomService.updateClassRoom(id, classRoomDetails);
        return Result.success("更新成功", updated);
    }

    // 删：删除班级
    @DeleteMapping("/{id}")
    public Result<Void> deleteClassRoom(@PathVariable Long id) {
        classRoomService.deleteClassRoom(id);
        return Result.success("删除成功", null);
    }

    // ========== 关联查询 ==========

    // 查：统计班级下学生人数
    @GetMapping("/{id}/student-count")
    public Result<Long> countStudentsByClassRoomId(@PathVariable Long id) {
        return Result.success(classRoomService.countStudentsByClassRoomId(id));
    }

    // 查：获取班级成绩统计（三表连接：班级 + 学生 + 成绩）
    @GetMapping("/{id}/statistics")
    public Result<ClassRoomStatisticsVO> getClassRoomStatistics(@PathVariable Long id) {
        return Result.success(classRoomService.getClassRoomStatistics(id));
    }
}
