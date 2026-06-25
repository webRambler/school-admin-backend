package com.example.school.service.impl;

import com.example.school.common.BusinessException;
import com.example.school.common.ResultCode;
import com.example.school.entity.Student;
import com.example.school.repository.IScoreRepository;
import com.example.school.repository.IStudentRepository;
import com.example.school.service.IStudentService;
import com.example.school.service.RedisService;
import com.example.school.vo.StudentCourseScoreVO;
import com.example.school.vo.StudentWithClassVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class StudentServiceImpl implements IStudentService {

    private final IStudentRepository studentRepository;
    private final IScoreRepository scoreRepository;
    private final RedisService redisService;

    private static final String STUDENT_KEY_PREFIX = "student:";
    private static final long STUDENT_CACHE_TTL = 30;

    public StudentServiceImpl(IStudentRepository studentRepository, IScoreRepository scoreRepository, RedisService redisService) {
        this.studentRepository = studentRepository;
        this.scoreRepository = scoreRepository;
        this.redisService = redisService;
    }

    @Override
    public Student createStudent(Student student) {
        studentRepository.insertStudent(student);
        redisService.set(STUDENT_KEY_PREFIX + student.getId(), student, STUDENT_CACHE_TTL, TimeUnit.MINUTES);
        return student;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.selectAllStudents();
    }

    @Override
    public Student getStudentById(Long id) {
        String cacheKey = STUDENT_KEY_PREFIX + id;
        Object cached = redisService.get(cacheKey);
        if (cached instanceof Student) {
            return (Student) cached;
        }
        Student student = studentRepository.selectStudentById(id);
        if (student == null) {
            throw new BusinessException(ResultCode.STUDENT_NOT_FOUND);
        }
        redisService.set(cacheKey, student, STUDENT_CACHE_TTL, TimeUnit.MINUTES);
        return student;
    }

    @Override
    public List<Student> getStudentsByName(String name) {
        return studentRepository.selectStudentsByName(name);
    }

    @Override
    public List<Student> getStudentsByClassId(Long classId) {
        return studentRepository.selectStudentsByClassId(classId);
    }

    @Override
    public List<Student> getStudentsByGender(String gender) {
        return studentRepository.selectStudentsByGender(gender);
    }

    @Override
    public Student updateStudent(Long id, Student studentDetails) {
        Student student = getStudentById(id);
        if (studentDetails.getName() != null) {
            student.setName(studentDetails.getName());
        }
        if (studentDetails.getGender() != null) {
            student.setGender(studentDetails.getGender());
        }
        if (studentDetails.getAge() != null) {
            student.setAge(studentDetails.getAge());
        }
        if (studentDetails.getClassId() != null) {
            student.setClassId(studentDetails.getClassId());
        }
        if (studentDetails.getPhone() != null) {
            student.setPhone(studentDetails.getPhone());
        }
        if (studentDetails.getEmail() != null) {
            student.setEmail(studentDetails.getEmail());
        }
        studentRepository.updateStudent(student);
        redisService.set(STUDENT_KEY_PREFIX + id, student, STUDENT_CACHE_TTL, TimeUnit.MINUTES);
        return student;
    }

    @Override
    public void deleteStudent(Long id) {
        getStudentById(id);
        // 删除学生时，同时删除其所有成绩
        scoreRepository.deleteScoresByStudentId(id);
        studentRepository.deleteStudent(id);
        redisService.delete(STUDENT_KEY_PREFIX + id);
    }

    // ====== 连接查询 ======

    @Override
    public StudentWithClassVO getStudentWithClass(Long studentId) {
        getStudentById(studentId); // 确保学生存在
        return studentRepository.selectStudentWithClass(studentId);
    }

    @Override
    public List<StudentWithClassVO> getStudentsWithClassByClassId(Long classId) {
        return studentRepository.selectStudentsWithClassByClassId(classId);
    }

    @Override
    public List<StudentWithClassVO> getAllStudentsWithClass() {
        return studentRepository.selectAllStudentsWithClass();
    }

    @Override
    public List<StudentCourseScoreVO> getStudentCourseScores(Long studentId) {
        getStudentById(studentId); // 确保学生存在
        return studentRepository.selectStudentCourseScores(studentId);
    }

    @Override
    public Long countCoursesByStudentId(Long studentId) {
        getStudentById(studentId); // 确保学生存在
        return studentRepository.countCoursesByStudentId(studentId);
    }

    @Override
    public List<StudentWithClassVO> searchStudentsWithClass(String gender, String className, String name, String major, Integer age) {
        return studentRepository.searchStudentsWithClass(gender, className, name, major, age);
    }
}
