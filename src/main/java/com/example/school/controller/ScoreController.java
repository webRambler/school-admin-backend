package com.example.school.controller;

import com.example.school.common.PageResult;
import com.example.school.common.Result;
import com.example.school.dto.ScoreCreateRequest;
import com.example.school.dto.ScoreUpdateRequest;
import com.example.school.entity.Score;
import com.example.school.service.IScoreService;
import com.example.school.vo.ScoreDetailVO;
import com.example.school.vo.ScoreRankVO;
import com.example.school.utils.PageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/scores")
public class ScoreController {

    private final IScoreService scoreService;

    public ScoreController(IScoreService scoreService) {
        this.scoreService = scoreService;
    }

    // ========== 基础 CRUD ==========

    // 增：创建成绩（同一学生同一课程不可重复添加）
    @PostMapping
    public Result<Score> createScore(@Valid @RequestBody ScoreCreateRequest request) {
        Score score = new Score();
        BeanUtils.copyProperties(request, score);
        Score created = scoreService.createScore(score);
        return Result.success("创建成功", created);
    }

    // 查：分页获取成绩列表
    @GetMapping
    public Result<PageResult<Score>> getAllScores(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        PageUtils.startPage(pageNum, pageSize);
        return Result.success(PageResult.of(scoreService.getAllScores()));
    }

    // 查：根据ID获取成绩
    @GetMapping("/{id}")
    public Result<Score> getScoreById(@PathVariable Long id) {
        return Result.success(scoreService.getScoreById(id));
    }

    // 查：按学生ID查询成绩（分页）
    @GetMapping("/student/{studentId}")
    public Result<PageResult<Score>> getScoresByStudentId(
            @PathVariable Long studentId,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        PageUtils.startPage(pageNum, pageSize);
        return Result.success(PageResult.of(scoreService.getScoresByStudentId(studentId)));
    }

    // 查：按课程ID查询成绩（分页）
    @GetMapping("/course/{courseId}")
    public Result<PageResult<Score>> getScoresByCourseId(
            @PathVariable Long courseId,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        PageUtils.startPage(pageNum, pageSize);
        return Result.success(PageResult.of(scoreService.getScoresByCourseId(courseId)));
    }

    // 查：按学生和课程查询成绩
    @GetMapping("/student/{studentId}/course/{courseId}")
    public Result<Score> getScoreByStudentAndCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        return Result.success(scoreService.getScoreByStudentAndCourse(studentId, courseId));
    }

    // 查：按考试类型查询成绩（分页）
    @GetMapping("/exam-type/{examType}")
    public Result<PageResult<Score>> getScoresByExamType(
            @PathVariable String examType,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        PageUtils.startPage(pageNum, pageSize);
        return Result.success(PageResult.of(scoreService.getScoresByExamType(examType)));
    }

    // 查：查询不及格成绩（分页）
    @GetMapping("/failed")
    public Result<PageResult<Score>> getFailedScores(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        PageUtils.startPage(pageNum, pageSize);
        return Result.success(PageResult.of(scoreService.getFailedScores()));
    }

    // 改：更新成绩
    @PutMapping("/{id}")
    public Result<Score> updateScore(@PathVariable Long id, @Valid @RequestBody ScoreUpdateRequest request) {
        Score scoreDetails = new Score();
        BeanUtils.copyProperties(request, scoreDetails);
        Score updated = scoreService.updateScore(id, scoreDetails);
        return Result.success("更新成功", updated);
    }

    // 删：删除成绩
    @DeleteMapping("/{id}")
    public Result<Void> deleteScore(@PathVariable Long id) {
        scoreService.deleteScore(id);
        return Result.success("删除成功", null);
    }

    // ========== 连接查询（成绩详情：三表连接 score + student + course） ==========

    // 查：获取成绩详情（含学生和课程信息）
    @GetMapping("/{id}/detail")
    public Result<ScoreDetailVO> getScoreDetail(@PathVariable Long id) {
        return Result.success(scoreService.getScoreDetail(id));
    }

    // 查：按学生ID查询成绩详情列表（分页）
    @GetMapping("/student/{studentId}/detail")
    public Result<PageResult<ScoreDetailVO>> getScoreDetailsByStudentId(
            @PathVariable Long studentId,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        PageUtils.startPage(pageNum, pageSize);
        return Result.success(PageResult.of(scoreService.getScoreDetailsByStudentId(studentId)));
    }

    // 查：按课程ID查询成绩详情列表（分页）
    @GetMapping("/course/{courseId}/detail")
    public Result<PageResult<ScoreDetailVO>> getScoreDetailsByCourseId(
            @PathVariable Long courseId,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        PageUtils.startPage(pageNum, pageSize);
        return Result.success(PageResult.of(scoreService.getScoreDetailsByCourseId(courseId)));
    }

    // 查：分页获取所有成绩详情
    @GetMapping("/detail")
    public Result<PageResult<ScoreDetailVO>> getAllScoreDetails(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        PageUtils.startPage(pageNum, pageSize);
        return Result.success(PageResult.of(scoreService.getAllScoreDetails()));
    }

    // 查：查询不及格成绩详情（含学生和课程信息，分页）
    @GetMapping("/failed/detail")
    public Result<PageResult<ScoreDetailVO>> getFailedScoreDetails(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        PageUtils.startPage(pageNum, pageSize);
        return Result.success(PageResult.of(scoreService.getFailedScoreDetails()));
    }

    // 查：按考试类型查询成绩详情（分页）
    @GetMapping("/exam-type/{examType}/detail")
    public Result<PageResult<ScoreDetailVO>> getScoreDetailsByExamType(
            @PathVariable String examType,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        PageUtils.startPage(pageNum, pageSize);
        return Result.success(PageResult.of(scoreService.getScoreDetailsByExamType(examType)));
    }

    // ========== 成绩排名 ==========

    // 查：课程成绩排名（分页）
    @GetMapping("/ranking/course/{courseId}")
    public Result<PageResult<ScoreRankVO>> getScoreRankingByCourseId(
            @PathVariable Long courseId,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        PageUtils.startPage(pageNum, pageSize);
        return Result.success(PageResult.of(scoreService.getScoreRankingByCourseId(courseId)));
    }

    // 查：班级课程成绩排名（分页）
    @GetMapping("/ranking/class/{classId}/course/{courseId}")
    public Result<PageResult<ScoreRankVO>> getScoreRankingByClassAndCourse(
            @PathVariable Long classId, @PathVariable Long courseId,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        PageUtils.startPage(pageNum, pageSize);
        return Result.success(PageResult.of(scoreService.getScoreRankingByClassAndCourse(classId, courseId)));
    }
}
