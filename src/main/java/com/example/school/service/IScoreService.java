package com.example.school.service;

import com.example.school.entity.Score;
import com.example.school.vo.ScoreDetailVO;
import com.example.school.vo.ScoreRankVO;

import java.util.List;

public interface IScoreService {

    Score createScore(Score score);

    List<Score> getAllScores();

    Score getScoreById(Long id);

    List<Score> getScoresByStudentId(Long studentId);

    List<Score> getScoresByCourseId(Long courseId);

    Score getScoreByStudentAndCourse(Long studentId, Long courseId);

    List<Score> getScoresByExamType(String examType);

    List<Score> getFailedScores();

    Score updateScore(Long id, Score scoreDetails);

    void deleteScore(Long id);

    // ====== 连接查询 ======

    ScoreDetailVO getScoreDetail(Long scoreId);

    List<ScoreDetailVO> getScoreDetailsByStudentId(Long studentId);

    List<ScoreDetailVO> getScoreDetailsByCourseId(Long courseId);

    List<ScoreDetailVO> getAllScoreDetails();

    List<ScoreDetailVO> getFailedScoreDetails();

    List<ScoreDetailVO> getScoreDetailsByExamType(String examType);

    List<ScoreRankVO> getScoreRankingByCourseId(Long courseId);

    List<ScoreRankVO> getScoreRankingByClassAndCourse(Long classId, Long courseId);
}
