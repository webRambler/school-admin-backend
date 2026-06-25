package com.example.school.service.impl;

import com.example.school.common.BusinessException;
import com.example.school.common.ResultCode;
import com.example.school.entity.Score;
import com.example.school.mapper.ScoreMapper;
import com.example.school.service.IScoreService;
import com.example.school.service.RedisService;
import com.example.school.vo.ScoreDetailVO;
import com.example.school.vo.ScoreRankVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ScoreServiceImpl implements IScoreService {

    private final ScoreMapper scoreMapper;
    private final RedisService redisService;

    private static final String SCORE_KEY_PREFIX = "score:";
    private static final long SCORE_CACHE_TTL = 15;

    public ScoreServiceImpl(ScoreMapper scoreMapper, RedisService redisService) {
        this.scoreMapper = scoreMapper;
        this.redisService = redisService;
    }

    @Override
    public Score createScore(Score score) {
        // 检查同一学生同一课程同一考试类型是否已有成绩
        Score existing = scoreMapper.selectScoreByStudentCourseAndExamType(
                score.getStudentId(), score.getCourseId(), score.getExamType());
        if (existing != null) {
            throw new BusinessException(ResultCode.SCORE_ALREADY_EXISTS, "该学生该课程该考试类型成绩已存在，请使用更新接口");
        }
        scoreMapper.insertScore(score);
        redisService.set(SCORE_KEY_PREFIX + score.getId(), score, SCORE_CACHE_TTL, TimeUnit.MINUTES);
        return score;
    }

    @Override
    public List<Score> getAllScores() {
        return scoreMapper.selectAllScores();
    }

    @Override
    public Score getScoreById(Long id) {
        String cacheKey = SCORE_KEY_PREFIX + id;
        Object cached = redisService.get(cacheKey);
        if (cached instanceof Score) {
            return (Score) cached;
        }
        Score score = scoreMapper.selectScoreById(id);
        if (score == null) {
            throw new BusinessException(ResultCode.SCORE_NOT_FOUND);
        }
        redisService.set(cacheKey, score, SCORE_CACHE_TTL, TimeUnit.MINUTES);
        return score;
    }

    @Override
    public List<Score> getScoresByStudentId(Long studentId) {
        return scoreMapper.selectScoresByStudentId(studentId);
    }

    @Override
    public List<Score> getScoresByCourseId(Long courseId) {
        return scoreMapper.selectScoresByCourseId(courseId);
    }

    @Override
    public Score getScoreByStudentAndCourse(Long studentId, Long courseId) {
        Score score = scoreMapper.selectScoreByStudentAndCourse(studentId, courseId);
        if (score == null) {
            throw new BusinessException(ResultCode.SCORE_NOT_FOUND, "未找到该学生该课程的成绩");
        }
        return score;
    }

    @Override
    public List<Score> getScoresByExamType(String examType) {
        return scoreMapper.selectScoresByExamType(examType);
    }

    @Override
    public List<Score> getFailedScores() {
        return scoreMapper.selectFailedScores();
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
        scoreMapper.updateScore(score);
        redisService.set(SCORE_KEY_PREFIX + id, score, SCORE_CACHE_TTL, TimeUnit.MINUTES);
        return score;
    }

    @Override
    public void deleteScore(Long id) {
        getScoreById(id);
        scoreMapper.deleteScore(id);
        redisService.delete(SCORE_KEY_PREFIX + id);
    }

    // ====== 连接查询 ======

    @Override
    public ScoreDetailVO getScoreDetail(Long scoreId) {
        getScoreById(scoreId); // 确保成绩存在
        return scoreMapper.selectScoreDetail(scoreId);
    }

    @Override
    public List<ScoreDetailVO> getScoreDetailsByStudentId(Long studentId) {
        return scoreMapper.selectScoreDetailsByStudentId(studentId);
    }

    @Override
    public List<ScoreDetailVO> getScoreDetailsByCourseId(Long courseId) {
        return scoreMapper.selectScoreDetailsByCourseId(courseId);
    }

    @Override
    public List<ScoreDetailVO> getAllScoreDetails() {
        return scoreMapper.selectAllScoreDetails();
    }

    @Override
    public List<ScoreDetailVO> getFailedScoreDetails() {
        return scoreMapper.selectFailedScoreDetails();
    }

    @Override
    public List<ScoreDetailVO> getScoreDetailsByExamType(String examType) {
        return scoreMapper.selectScoreDetailsByExamType(examType);
    }

    @Override
    public List<ScoreRankVO> getScoreRankingByCourseId(Long courseId) {
        return scoreMapper.selectScoreRankingByCourseId(courseId);
    }

    @Override
    public List<ScoreRankVO> getScoreRankingByClassAndCourse(Long classId, Long courseId) {
        return scoreMapper.selectScoreRankingByClassAndCourse(classId, courseId);
    }
}
