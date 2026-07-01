package com.example.school.controller;

import com.example.school.common.PageResult;
import com.example.school.common.Result;
import com.example.school.dto.CollegeCreateRequest;
import com.example.school.dto.CollegeUpdateRequest;
import com.example.school.entity.College;
import com.example.school.service.ICollegeService;
import com.example.school.vo.CollegeWithClassesVO;
import com.example.school.vo.CollegeWithDeanVO;
import com.example.school.utils.PageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/colleges")
public class CollegeController {

    private final ICollegeService collegeService;

    public CollegeController(ICollegeService collegeService) {
        this.collegeService = collegeService;
    }

    // ========== 基础 CRUD ==========

    // 增：创建学院
    @PostMapping
    public Result<College> createCollege(@Valid @RequestBody CollegeCreateRequest request) {
        College created = collegeService.createCollege(request);
        return Result.success("创建成功", created);
    }

    // 查：多条件分页查询学院（名称模糊、代码模糊，含院长信息）
    @GetMapping
    public Result<PageResult<CollegeWithDeanVO>> searchColleges(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        PageUtils.startPage(pageNum, pageSize);
        return Result.success(PageResult.of(collegeService.searchColleges(name, code)));
    }

    // 查：根据ID获取学院（含院长信息）
    @GetMapping("/{id}")
    public Result<CollegeWithDeanVO> getCollegeById(@PathVariable Long id) {
        return Result.success(collegeService.getCollegeDetailById(id));
    }

    

    // 查：按学院代码精确查询
    @GetMapping("/code/{code}")
    public Result<College> getCollegeByCode(@PathVariable String code) {
        return Result.success(collegeService.getCollegeByCode(code));
    }

    // 改：更新学院
    @PutMapping("/{id}")
    public Result<College> updateCollege(@PathVariable Long id, @Valid @RequestBody CollegeUpdateRequest request) {
        College updated = collegeService.updateCollege(id, request);
        return Result.success("更新成功", updated);
    }

    // 删：删除学院
    @DeleteMapping("/{id}")
    public Result<Void> deleteCollege(@PathVariable Long id) {
        collegeService.deleteCollege(id);
        return Result.success("删除成功", null);
    }

    // ========== 关联查询 ==========

    // 查：统计学院下班级数量
    @GetMapping("/{id}/class-count")
    public Result<Long> countClassesByCollegeId(@PathVariable Long id) {
        return Result.success(collegeService.countClassesByCollegeId(id));
    }

    // 查：获取学院统计信息（班级数、学生数）
    @GetMapping("/{id}/statistics")
    public Result<CollegeWithClassesVO> getCollegeStatistics(@PathVariable Long id) {
        return Result.success(collegeService.getCollegeStatistics(id));
    }
}
