package com.example.school.mapper;

import com.example.school.entity.College;
import com.example.school.vo.CollegeWithClassesVO;
import com.example.school.vo.CollegeWithDeanVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CollegeMapper {

    // 新增学院
    int insertCollege(College college);

    // 查询所有学院
    List<College> selectAllColleges();

    // 根据ID查询学院
    College selectCollegeById(Long id);

    // 根据名称模糊查询学院
    List<College> selectCollegesByName(String name);

    // 多条件查询学院（名称模糊、代码模糊，含院长信息）
    List<CollegeWithDeanVO> searchColleges(@Param("name") String name, @Param("code") String code);

    // 根据学院代码查询学院
    College selectCollegeByCode(String code);

    // 更新学院
    int updateCollege(College college);

    // 删除学院
    int deleteCollege(Long id);

    // 统计学院下班级数量
    Long countClassesByCollegeId(Long collegeId);

    // 获取学院统计信息（连接查询：学院 + 班级 + 学生）
    CollegeWithClassesVO selectCollegeStatistics(Long collegeId);
}
