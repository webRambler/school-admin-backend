package com.example.school.service.impl;

import com.example.school.common.BusinessException;
import com.example.school.common.CacheConstants;
import com.example.school.common.ResultCode;
import com.example.school.entity.User;
import com.example.school.repository.IUserRepository;
import com.example.school.service.RedisService;
import com.example.school.vo.LoginVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private RedisService redisService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("admin");
        user.setPassword("$2a$10$encoded");
        user.setNickname("管理员");
    }

    @Test
    void login_shouldReturnTokenPair_whenCredentialsValid() {
        when(userRepository.selectByUsername("admin")).thenReturn(user);
        when(passwordEncoder.matches("admin123", user.getPassword())).thenReturn(true);
        doNothing().when(redisService).set(anyString(), anyString(), anyLong(), any(TimeUnit.class));

        LoginVO loginVO = authService.login("admin", "admin123");

        assertNotNull(loginVO);
        assertEquals("admin", loginVO.getUsername());
        assertNotNull(loginVO.getAccessToken());
        assertNotNull(loginVO.getRefreshToken());
        verify(redisService, times(3)).set(anyString(), anyString(), anyLong(), any(TimeUnit.class));
    }

    @Test
    void login_shouldThrowUnauthorized_whenUserNotFound() {
        when(userRepository.selectByUsername("admin")).thenReturn(null);

        BusinessException exception = assertThrows(BusinessException.class,
                () -> authService.login("admin", "admin123"));
        assertEquals(ResultCode.UNAUTHORIZED, exception.getResultCode());
    }

    @Test
    void login_shouldThrowUnauthorized_whenPasswordMismatch() {
        when(userRepository.selectByUsername("admin")).thenReturn(user);
        when(passwordEncoder.matches("wrong", user.getPassword())).thenReturn(false);

        BusinessException exception = assertThrows(BusinessException.class,
                () -> authService.login("admin", "wrong"));
        assertEquals(ResultCode.UNAUTHORIZED, exception.getResultCode());
    }

    @Test
    void register_shouldThrowBadRequest_whenUsernameExists() {
        when(userRepository.selectByUsername("admin")).thenReturn(user);

        BusinessException exception = assertThrows(BusinessException.class,
                () -> authService.register("admin", "admin123", "管理员"));
        assertEquals(ResultCode.BAD_REQUEST, exception.getResultCode());
        verify(userRepository, never()).insert(any(User.class));
    }

    @Test
    void register_shouldCreateUserAndReturnTokenPair_whenUsernameAvailable() {
        when(userRepository.selectByUsername("newuser")).thenReturn(null);
        when(passwordEncoder.encode("admin123")).thenReturn("encoded");

        LoginVO loginVO = authService.register("newuser", "admin123", "新用户");

        assertNotNull(loginVO);
        assertEquals("newuser", loginVO.getUsername());
        verify(userRepository).insert(any(User.class));
        verify(redisService, times(3)).set(anyString(), anyString(), anyLong(), any(TimeUnit.class));
    }

    @Test
    void refresh_shouldThrowUnauthorized_whenRefreshTokenEmpty() {
        BusinessException exception = assertThrows(BusinessException.class,
                () -> authService.refresh(""));
        assertEquals(ResultCode.UNAUTHORIZED, exception.getResultCode());
    }

    @Test
    void refresh_shouldThrowUnauthorized_whenRefreshTokenExpired() {
        when(redisService.get(CacheConstants.AUTH_REFRESH_PREFIX + "expired")).thenReturn(null);

        BusinessException exception = assertThrows(BusinessException.class,
                () -> authService.refresh("expired"));
        assertEquals(ResultCode.UNAUTHORIZED, exception.getResultCode());
    }

    @Test
    void refresh_shouldReturnNewTokenPair_whenRefreshTokenValid() {
        when(redisService.get(CacheConstants.AUTH_REFRESH_PREFIX + "valid")).thenReturn("admin");
        when(userRepository.selectByUsername("admin")).thenReturn(user);

        LoginVO loginVO = authService.refresh("valid");

        assertNotNull(loginVO);
        verify(redisService).delete(CacheConstants.AUTH_REFRESH_PREFIX + "valid");
        verify(redisService).delete(CacheConstants.AUTH_USER_REFRESH_PREFIX + "admin");
    }

    @Test
    void logout_shouldDoNothing_whenAccessTokenEmpty() {
        assertDoesNotThrow(() -> authService.logout(""));
        verify(redisService, never()).delete(anyString());
    }

    @Test
    void logout_shouldDeleteAccessAndRefreshTokens_whenAccessTokenValid() {
        when(redisService.get(CacheConstants.AUTH_ACCESS_PREFIX + "access")).thenReturn("admin");
        when(redisService.get(CacheConstants.AUTH_USER_REFRESH_PREFIX + "admin")).thenReturn("refresh");

        authService.logout("access");

        verify(redisService).delete(CacheConstants.AUTH_ACCESS_PREFIX + "access");
        verify(redisService).delete(CacheConstants.AUTH_REFRESH_PREFIX + "refresh");
        verify(redisService).delete(CacheConstants.AUTH_USER_REFRESH_PREFIX + "admin");
    }
}
