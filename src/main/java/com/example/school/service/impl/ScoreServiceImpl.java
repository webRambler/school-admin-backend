package com.example.school.service.impl;

import com.example.school.common.BusinessException;
import com.example.school.common.ResultCode;
import com.example.school.entity.Score;
import com.example.school.repository.IScoreRepository;
import com.example.school.service.IScoreService;
import com.example.school.service.RedisService;
import com.example.school.vo.ScoreDetailVO;
import com.example.school.vo.ScoreRankVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ScoreServiceImpl implements IScoreService {

    private final IScoreRepository scoreRepository;
    private final RedisService redisService;

    private static final String SCORE_KEY_PREFIX = "score:";
    private static final long SCORE_CACHE_TTL = 15;

    public ScoreServiceImpl(IScoreRepository scoreRepository, RedisService redisService) {
        this.scoreRepository = scoreRepository;
        this.redisService = redisService;
    }

    @Override
    public Score createScore(Score score) {
        // 检查同一学生同一课程同一考试类型是否已有成绩
        Score existing = scoreRepository.selectScoreByStudentCourseAndExamType(
                score.getStudentId(), score.getCourseId(), score.getExamType());
        if (existing != null) {
            throw new BusinessException(ResultCode.SCORE_ALREADY_EXISTS, "该学生该课程该考试类型成绩已存在，请使用更新接口");
        }
        scoreRepository.insertScore(score);
        redisService.set(SCORE_KEY_PREFIX + score.getId(), score, SCORE_CACHE_TTL, TimeUnit.MINUTES);
        return score;
    }

    @Override
    public List<Score> getAllScores() {
        return scoreRepository.selectAllScores();
    }

    @Override
    public Score getScoreById(Long id) {
        String cacheKey = SCORE_KEY_PREFIX + id;
        Object cached = redisService.get(cacheKey);
        if (cached instanceof Score) {
            return (Score) cached;
        }
        Score score = scoreRepository.selectScoreById(id);
        if (score == null) {
            throw new BusinessException(ResultCode.SCORE_NOT_FOUND);
        }
        redisService.set(cacheKey, score, SCORE_CACHE_TTL, TimeUnit.MINUTES);
        return score;
    }

    @Override
    public List<Score> getScoresByStudentId(Long studentId) {
        return scoreRepository.selectScoresByStudentId(studentId);
    }

    @Override
    public List<Score> getScoresByCourseId(Long courseId) {
        return scoreRepository.selectScoresByCourseId(courseId);
    }

    @Override
    public Score getScoreByStudentAndCourse(Long studentId, Long courseId) {
        Score score = scoreRepository.selectScoreByStudentAndCourse(studentId, courseId);
        if (score == null) {
            throw new BusinessException(ResultCode.SCORE_NOT_FOUND, "未找到该学生该课程的成绩");
        }
        return score;
    }

    @Override
    public List<Score> getScoresByExamType(String examType) {
        return scoreRepository.selectScoresByExamType(examType);
    }

    @Override
    public List<Score> getFailedScores() {
        return scoreRepository.selectFailedScores();
    }

    @Override
    public Score updateScore(Long id, Score scoreDetails) {
        Score score = getScoreById(id);
        if (scoreDetails.getScore() != null) {
            score.setScore(scoreDetails.getScore());
        }
        if (scoreDetails.getExamType() != null) {
            score.setExamType(scoreDetails.getExamType());
        }
        scoreRepository.updateScore(score);
        redisService.set(SCORE_KEY_PREFIX + id, score, SCORE_CACHE_TTL, TimeUnit.MINUTES);
        return score;
    }

    @Override
    public void deleteScore(Long id) {
        getScoreById(id);
        scoreRepository.deleteScore(id);
        redisService.delete(SCORE_KEY_PREFIX + id);
    }

    // ====== 连接查询 ======

    @Override
    public ScoreDetailVO getScoreDetail(Long scoreId) {
        getScoreById(scoreId); // 确保成绩存在
        return scoreRepository.selectScoreDetail(scoreId);
    }

    @Override
    public List<ScoreDetailVO> getScoreDetailsByStudentId(Long studentId) {
        return scoreRepository.selectScoreDetailsByStudentId(studentId);
    }

    @Override
    public List<ScoreDetailVO> getScoreDetailsByCourseId(Long courseId) {
        return scoreRepository.selectScoreDetailsByCourseId(courseId);
    }

    @Override
    public List<ScoreDetailVO> getAllScoreDetails() {
        return scoreRepository.selectAllScoreDetails();
    }

    @Override
    public List<ScoreDetailVO> getFailedScoreDetails() {
        return scoreRepository.selectFailedScoreDetails();
    }

    @Override
    public List<ScoreDetailVO> getScoreDetailsByExamType(String examType) {
        return scoreRepository.selectScoreDetailsByExamType(examType);
    }

    @Override
    public List<ScoreRankVO> getScoreRankingByCourseId(Long courseId) {
        return scoreRepository.selectScoreRankingByCourseId(courseId);
    }

    @Override
    public List<ScoreRankVO> getScoreRankingByClassAndCourse(Long classId, Long courseId) {
        return scoreRepository.selectScoreRankingByClassAndCourse(classId, courseId);
    }
}
