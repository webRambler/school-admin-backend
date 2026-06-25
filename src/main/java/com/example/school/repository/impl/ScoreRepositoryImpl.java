package com.example.school.repository.impl;

import com.example.school.entity.Score;
import com.example.school.mapper.ScoreMapper;
import com.example.school.repository.IScoreRepository;
import com.example.school.vo.ScoreDetailVO;
import com.example.school.vo.ScoreRankVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ScoreRepositoryImpl implements IScoreRepository {

    private final ScoreMapper scoreMapper;

    public ScoreRepositoryImpl(ScoreMapper scoreMapper) {
        this.scoreMapper = scoreMapper;
    }

    @Override
    public int insertScore(Score score) {
        return scoreMapper.insertScore(score);
    }

    @Override
    public List<Score> selectAllScores() {
        return scoreMapper.selectAllScores();
    }

    @Override
    public Score selectScoreById(Long id) {
        return scoreMapper.selectScoreById(id);
    }

    @Override
    public List<Score> selectScoresByStudentId(Long studentId) {
        return scoreMapper.selectScoresByStudentId(studentId);
    }

    @Override
    public List<Score> selectScoresByCourseId(Long courseId) {
        return scoreMapper.selectScoresByCourseId(courseId);
    }

    @Override
    public Score selectScoreByStudentAndCourse(Long studentId, Long courseId) {
        return scoreMapper.selectScoreByStudentAndCourse(studentId, courseId);
    }

    @Override
    public Score selectScoreByStudentCourseAndExamType(Long studentId, Long courseId, String examType) {
        return scoreMapper.selectScoreByStudentCourseAndExamType(studentId, courseId, examType);
    }

    @Override
    public List<Score> selectScoresByExamType(String examType) {
        return scoreMapper.selectScoresByExamType(examType);
    }

    @Override
    public List<Score> selectFailedScores() {
        return scoreMapper.selectFailedScores();
    }

    @Override
    public int updateScore(Score score) {
        return scoreMapper.updateScore(score);
    }

    @Override
    public int deleteScore(Long id) {
        return scoreMapper.deleteScore(id);
    }

    @Override
    public int deleteScoresByStudentId(Long studentId) {
        return scoreMapper.deleteScoresByStudentId(studentId);
    }

    @Override
    public int deleteScoresByCourseId(Long courseId) {
        return scoreMapper.deleteScoresByCourseId(courseId);
    }

    // ====== 连接查询 ======

    @Override
    public ScoreDetailVO selectScoreDetail(Long scoreId) {
        return scoreMapper.selectScoreDetail(scoreId);
    }

    @Override
    public List<ScoreDetailVO> selectScoreDetailsByStudentId(Long studentId) {
        return scoreMapper.selectScoreDetailsByStudentId(studentId);
    }

    @Override
    public List<ScoreDetailVO> selectScoreDetailsByCourseId(Long courseId) {
        return scoreMapper.selectScoreDetailsByCourseId(courseId);
    }

    @Override
    public List<ScoreDetailVO> selectAllScoreDetails() {
        return scoreMapper.selectAllScoreDetails();
    }

    @Override
    public List<ScoreDetailVO> selectFailedScoreDetails() {
        return scoreMapper.selectFailedScoreDetails();
    }

    @Override
    public List<ScoreDetailVO> selectScoreDetailsByExamType(String examType) {
        return scoreMapper.selectScoreDetailsByExamType(examType);
    }

    @Override
    public List<ScoreRankVO> selectScoreRankingByCourseId(Long courseId) {
        return scoreMapper.selectScoreRankingByCourseId(courseId);
    }

    @Override
    public List<ScoreRankVO> selectScoreRankingByClassAndCourse(Long classId, Long courseId) {
        return scoreMapper.selectScoreRankingByClassAndCourse(classId, courseId);
    }
}
