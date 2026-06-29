package com.example.school.mapper;

import com.example.school.entity.CollegeTeacher;
import com.example.school.vo.CollegeTeacherVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CollegeTeacherMapper {

    /** 插入关系记录 */
    int insert(CollegeTeacher collegeTeacher);

    /** 根据ID查询 */
    CollegeTeacher selectById(Long id);

    /** 根据学院ID和教师ID精确查询 */
    CollegeTeacher selectByCollegeAndTeacher(@Param("collegeId") Long collegeId, @Param("teacherId") Long teacherId);

    /** 查询某学院下所有教师关系（含教师姓名、职称） */
    List<CollegeTeacherVO> selectByCollegeId(Long collegeId);

    /** 查询某教师关联的所有学院（含学院名称） */
    List<CollegeTeacherVO> selectByTeacherId(Long teacherId);

    /** 查询某学院的院长记录 */
    CollegeTeacherVO selectDeanByCollegeId(Long collegeId);

    /** 更新 is_dean 字段 */
    int updateIsDean(@Param("id") Long id, @Param("isDean") Integer isDean);

    /** 删除关系记录 */
    int deleteById(Long id);

    /** 根据学院ID删除所有关系 */
    int deleteByCollegeId(Long collegeId);

    /** 根据教师ID删除所有关系 */
    int deleteByTeacherId(Long teacherId);

    /** 统计学院下关联的教师数量 */
    Long countByCollegeId(Long collegeId);
}
