package com.example.school.controller;

import com.example.school.common.PageResult;
import com.example.school.common.Result;
import com.example.school.dto.CollegeTeacherCreateRequest;
import com.example.school.entity.CollegeTeacher;
import com.example.school.service.ICollegeTeacherService;
import com.example.school.vo.CollegeTeacherVO;
import com.example.school.utils.PageUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/college-teachers")
public class CollegeTeacherController {

    private final ICollegeTeacherService collegeTeacherService;

    public CollegeTeacherController(ICollegeTeacherService collegeTeacherService) {
        this.collegeTeacherService = collegeTeacherService;
    }

    // ========== 基础 CRUD ==========

    // 增：创建学院-教师关系
    @PostMapping
    public Result<CollegeTeacher> createRelation(@Valid @RequestBody CollegeTeacherCreateRequest request) {
        CollegeTeacher created = collegeTeacherService.createRelation(request);
        return Result.success("创建成功", created);
    }

    // 查：根据ID查询关系记录
    @GetMapping("/{id}")
    public Result<CollegeTeacher> getById(@PathVariable Long id) {
        return Result.success(collegeTeacherService.getById(id));
    }

    // 改：设置/取消院长标记
    @PatchMapping("/{id}/dean")
    public Result<Void> setIsDean(@PathVariable Long id, @RequestParam Integer isDean) {
        collegeTeacherService.setIsDean(id, isDean);
        return Result.success("操作成功", null);
    }

    // 删：删除关系记录
    @DeleteMapping("/{id}")
    public Result<Void> deleteRelation(@PathVariable Long id) {
        collegeTeacherService.deleteRelation(id);
        return Result.success("删除成功", null);
    }

    // ========== 关联查询 ==========

    // 查：查询某学院下所有教师（含是否院长，分页）
    @GetMapping("/college/{collegeId}")
    public Result<PageResult<CollegeTeacherVO>> getTeachersByCollege(
            @PathVariable Long collegeId,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        PageUtils.startPage(pageNum, pageSize);
        return Result.success(PageResult.of(collegeTeacherService.getTeachersByCollege(collegeId)));
    }

    // 查：查询某教师关联的所有学院（分页）
    @GetMapping("/teacher/{teacherId}")
    public Result<PageResult<CollegeTeacherVO>> getCollegesByTeacher(
            @PathVariable Long teacherId,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        PageUtils.startPage(pageNum, pageSize);
        return Result.success(PageResult.of(collegeTeacherService.getCollegesByTeacher(teacherId)));
    }

    // 查：统计学院下关联的教师数量
    @GetMapping("/college/{collegeId}/count")
    public Result<Long> countTeachersByCollege(@PathVariable Long collegeId) {
        return Result.success(collegeTeacherService.countTeachersByCollege(collegeId));
    }
}
