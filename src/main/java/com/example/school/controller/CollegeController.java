package com.example.school.controller;

import com.example.school.common.PageResult;
import com.example.school.common.Result;
import com.example.school.dto.CollegeCreateRequest;
import com.example.school.dto.CollegeUpdateRequest;
import com.example.school.entity.College;
import com.example.school.service.ICollegeService;
import com.example.school.vo.CollegeWithClassesVO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
        College college = new College();
        BeanUtils.copyProperties(request, college);
        College created = collegeService.createCollege(college);
        return Result.success("创建成功", created);
    }

    // 查：分页获取学院列表
    @GetMapping
    public Result<PageResult<College>> getAllColleges(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return Result.success(PageResult.of(collegeService.getAllColleges()));
    }

    // 查：根据ID获取学院
    @GetMapping("/{id}")
    public Result<College> getCollegeById(@PathVariable Long id) {
        return Result.success(collegeService.getCollegeById(id));
    }

    // 查：按名称模糊搜索学院
    @GetMapping("/search")
    public Result<List<College>> getCollegesByName(@RequestParam String name) {
        return Result.success(collegeService.getCollegesByName(name));
    }

    // 查：按学院代码精确查询
    @GetMapping("/code/{code}")
    public Result<College> getCollegeByCode(@PathVariable String code) {
        return Result.success(collegeService.getCollegeByCode(code));
    }

    // 改：更新学院
    @PutMapping("/{id}")
    public Result<College> updateCollege(@PathVariable Long id, @Valid @RequestBody CollegeUpdateRequest request) {
        College collegeDetails = new College();
        BeanUtils.copyProperties(request, collegeDetails);
        College updated = collegeService.updateCollege(id, collegeDetails);
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
