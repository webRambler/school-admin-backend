package com.example.school.repository;

import com.example.school.entity.Score;
import com.example.school.vo.ScoreDetailVO;
import com.example.school.vo.ScoreRankVO;

import java.util.List;

public interface IScoreRepository {

    int insertScore(Score score);

    List<Score> selectAllScores();

    Score selectScoreById(Long id);

    List<Score> selectScoresByStudentId(Long studentId);

    List<Score> selectScoresByCourseId(Long courseId);

    Score selectScoreByStudentAndCourse(Long studentId, Long courseId);

    Score selectScoreByStudentCourseAndExamType(Long studentId, Long courseId, String examType);

    List<Score> selectScoresByExamType(String examType);

    List<Score> selectFailedScores();

    int updateScore(Score score);

    int deleteScore(Long id);

    int deleteScoresByStudentId(Long studentId);

    int deleteScoresByCourseId(Long courseId);

    // ====== 连接查询 ======

    ScoreDetailVO selectScoreDetail(Long scoreId);

    List<ScoreDetailVO> selectScoreDetailsByStudentId(Long studentId);

    List<ScoreDetailVO> selectScoreDetailsByCourseId(Long courseId);

    List<ScoreDetailVO> selectAllScoreDetails();

    List<ScoreDetailVO> selectFailedScoreDetails();

    List<ScoreDetailVO> selectScoreDetailsByExamType(String examType);

    List<ScoreRankVO> selectScoreRankingByCourseId(Long courseId);

    List<ScoreRankVO> selectScoreRankingByClassAndCourse(Long classId, Long courseId);
}
