package com.example.school.mapper;

import com.example.school.entity.Score;
import com.example.school.vo.ScoreDetailVO;
import com.example.school.vo.ScoreRankVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface ScoreMapper {

    // 新增成绩
    int insertScore(Score score);

    // 查询所有成绩
    List<Score> selectAllScores();

    // 根据ID查询成绩
    Score selectScoreById(Long id);

    // 根据学生ID查询成绩
    List<Score> selectScoresByStudentId(Long studentId);

    // 根据课程ID查询成绩
    List<Score> selectScoresByCourseId(Long courseId);

    // 根据学生ID和课程ID查询成绩
    Score selectScoreByStudentAndCourse(@Param("studentId") Long studentId, @Param("courseId") Long courseId);

    // 根据学生ID、课程ID和考试类型查询成绩
    Score selectScoreByStudentCourseAndExamType(@Param("studentId") Long studentId, @Param("courseId") Long courseId, @Param("examType") String examType);

    // 根据考试类型查询成绩
    List<Score> selectScoresByExamType(String examType);

    // 查询不及格成绩（分数 < 60）
    List<Score> selectFailedScores();

    // 更新成绩
    int updateScore(Score score);

    // 删除成绩
    int deleteScore(Long id);

    // 根据学生ID删除所有成绩
    int deleteScoresByStudentId(Long studentId);

    // 根据课程ID删除所有成绩
    int deleteScoresByCourseId(Long courseId);

    // ====== 连接查询 ======

    // 获取成绩详情（三表连接：score + student + course）
    ScoreDetailVO selectScoreDetail(Long scoreId);

    // 根据学生ID查询成绩详情列表
    List<ScoreDetailVO> selectScoreDetailsByStudentId(Long studentId);

    // 根据课程ID查询成绩详情列表
    List<ScoreDetailVO> selectScoreDetailsByCourseId(Long courseId);

    // 查询所有成绩详情
    List<ScoreDetailVO> selectAllScoreDetails();

    // 查询不及格成绩详情
    List<ScoreDetailVO> selectFailedScoreDetails();

    // 根据考试类型查询成绩详情
    List<ScoreDetailVO> selectScoreDetailsByExamType(String examType);

    // 课程成绩排名（按课程ID，分数降序）
    List<ScoreRankVO> selectScoreRankingByCourseId(Long courseId);

    // 班级课程成绩排名（按班级ID和课程ID，分数降序）
    List<ScoreRankVO> selectScoreRankingByClassAndCourse(@Param("classId") Long classId, @Param("courseId") Long courseId);
}
