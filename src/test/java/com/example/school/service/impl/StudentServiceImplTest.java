package com.example.school.service.impl;

import com.example.school.common.BusinessException;
import com.example.school.common.ResultCode;
import com.example.school.entity.Student;
import com.example.school.repository.IScoreRepository;
import com.example.school.repository.IStudentRepository;
import com.example.school.service.RedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private IStudentRepository studentRepository;

    @Mock
    private IScoreRepository scoreRepository;

    @Mock
    private RedisService redisService;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(1L);
        student.setName("陈小明");
        student.setGender("男");
        student.setAge(20);
        student.setClassId(1L);
    }

    @Test
    void createStudent_shouldInsertAndCache() {
        doNothing().when(studentRepository).insertStudent(student);
        doNothing().when(redisService).set(anyString(), any(Student.class), anyLong(), any(TimeUnit.class));

        Student created = studentService.createStudent(student);

        assertEquals("陈小明", created.getName());
        verify(studentRepository).insertStudent(student);
        verify(redisService).set(anyString(), eq(student), anyLong(), any(TimeUnit.class));
    }

    @Test
    void getStudentById_shouldReturnFromCache_whenCacheHit() {
        when(redisService.get(anyString())).thenReturn(student);

        Student result = studentService.getStudentById(1L);

        assertEquals(student, result);
        verify(studentRepository, never()).selectStudentById(anyLong());
    }

    @Test
    void getStudentById_shouldThrow_whenNotFound() {
        when(redisService.get(anyString())).thenReturn(null);
        when(studentRepository.selectStudentById(1L)).thenReturn(null);

        BusinessException exception = assertThrows(BusinessException.class,
                () -> studentService.getStudentById(1L));
        assertEquals(ResultCode.STUDENT_NOT_FOUND, exception.getResultCode());
    }

    @Test
    void updateStudent_shouldUpdateNameAndCache() {
        Student details = new Student();
        details.setName("陈大明");

        when(redisService.get(anyString())).thenReturn(null);
        when(studentRepository.selectStudentById(1L)).thenReturn(student);
        doNothing().when(studentRepository).updateStudent(student);
        doNothing().when(redisService).set(anyString(), any(Student.class), anyLong(), any(TimeUnit.class));

        Student updated = studentService.updateStudent(1L, details);

        assertEquals("陈大明", updated.getName());
        verify(studentRepository).updateStudent(student);
    }

    @Test
    void deleteStudent_shouldDeleteScoresAndStudentAndCache() {
        when(redisService.get(anyString())).thenReturn(null);
        when(studentRepository.selectStudentById(1L)).thenReturn(student);
        doNothing().when(scoreRepository).deleteScoresByStudentId(1L);
        doNothing().when(studentRepository).deleteStudent(1L);
        when(redisService.delete(anyString())).thenReturn(true);

        assertDoesNotThrow(() -> studentService.deleteStudent(1L));

        verify(scoreRepository).deleteScoresByStudentId(1L);
        verify(studentRepository).deleteStudent(1L);
        verify(redisService).delete(anyString());
    }
}
