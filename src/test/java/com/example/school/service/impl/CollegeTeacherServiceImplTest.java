package com.example.school.service.impl;

import com.example.school.common.BusinessException;
import com.example.school.common.ResultCode;
import com.example.school.dto.CollegeTeacherCreateRequest;
import com.example.school.entity.College;
import com.example.school.entity.CollegeTeacher;
import com.example.school.entity.Teacher;
import com.example.school.repository.ICollegeRepository;
import com.example.school.repository.ICollegeTeacherRepository;
import com.example.school.repository.ITeacherRepository;
import com.example.school.vo.CollegeTeacherVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CollegeTeacherServiceImplTest {

    @Mock
    private ICollegeTeacherRepository collegeTeacherRepository;

    @Mock
    private ICollegeRepository collegeRepository;

    @Mock
    private ITeacherRepository teacherRepository;

    @InjectMocks
    private CollegeTeacherServiceImpl collegeTeacherService;

    @Test
    void createRelation_shouldSucceed_whenAllValid() {
        CollegeTeacherCreateRequest request = new CollegeTeacherCreateRequest();
        request.setCollegeId(1L);
        request.setTeacherId(2L);
        request.setIsDean(0);

        when(collegeRepository.selectCollegeById(1L)).thenReturn(new College());
        when(teacherRepository.selectTeacherById(2L)).thenReturn(new Teacher());
        when(collegeTeacherRepository.selectByCollegeAndTeacher(1L, 2L)).thenReturn(null);
        doNothing().when(collegeTeacherRepository).insert(any(CollegeTeacher.class));

        CollegeTeacher result = collegeTeacherService.createRelation(request);

        assertNotNull(result);
        assertEquals(Integer.valueOf(0), result.getIsDean());
        verify(collegeTeacherRepository).insert(any(CollegeTeacher.class));
    }

    @Test
    void createRelation_shouldThrow_whenCollegeNotFound() {
        CollegeTeacherCreateRequest request = new CollegeTeacherCreateRequest();
        request.setCollegeId(1L);
        request.setTeacherId(2L);

        when(collegeRepository.selectCollegeById(1L)).thenReturn(null);

        BusinessException exception = assertThrows(BusinessException.class,
                () -> collegeTeacherService.createRelation(request));
        assertEquals(ResultCode.COLLEGE_NOT_FOUND, exception.getResultCode());
    }

    @Test
    void createRelation_shouldThrow_whenRelationAlreadyExists() {
        CollegeTeacherCreateRequest request = new CollegeTeacherCreateRequest();
        request.setCollegeId(1L);
        request.setTeacherId(2L);

        when(collegeRepository.selectCollegeById(1L)).thenReturn(new College());
        when(teacherRepository.selectTeacherById(2L)).thenReturn(new Teacher());
        when(collegeTeacherRepository.selectByCollegeAndTeacher(1L, 2L)).thenReturn(new CollegeTeacher());

        BusinessException exception = assertThrows(BusinessException.class,
                () -> collegeTeacherService.createRelation(request));
        assertEquals(ResultCode.BAD_REQUEST, exception.getResultCode());
    }

    @Test
    void createRelation_shouldThrow_whenDeanAlreadyExists() {
        CollegeTeacherCreateRequest request = new CollegeTeacherCreateRequest();
        request.setCollegeId(1L);
        request.setTeacherId(2L);
        request.setIsDean(1);

        when(collegeRepository.selectCollegeById(1L)).thenReturn(new College());
        when(teacherRepository.selectTeacherById(2L)).thenReturn(new Teacher());
        when(collegeTeacherRepository.selectByCollegeAndTeacher(1L, 2L)).thenReturn(null);
        when(collegeTeacherRepository.selectDeanByCollegeId(1L)).thenReturn(new CollegeTeacherVO());

        BusinessException exception = assertThrows(BusinessException.class,
                () -> collegeTeacherService.createRelation(request));
        assertEquals(ResultCode.BAD_REQUEST, exception.getResultCode());
    }

    @Test
    void setIsDean_shouldThrow_whenAnotherDeanExists() {
        CollegeTeacher existing = new CollegeTeacher();
        existing.setId(1L);
        existing.setCollegeId(1L);

        CollegeTeacherVO dean = new CollegeTeacherVO();
        dean.setId(2L);
        dean.setTeacherId(3L);

        when(collegeTeacherRepository.selectById(1L)).thenReturn(existing);
        when(collegeTeacherRepository.selectDeanByCollegeId(1L)).thenReturn(dean);

        BusinessException exception = assertThrows(BusinessException.class,
                () -> collegeTeacherService.setIsDean(1L, 1));
        assertEquals(ResultCode.BAD_REQUEST, exception.getResultCode());
    }
}
