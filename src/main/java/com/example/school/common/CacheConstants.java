package com.example.school.common;

/**
 * Redis 缓存 key 统一命名常量。
 * <p>命名规范：业务模块:业务标识:{id}
 */
public final class CacheConstants {

    private CacheConstants() {
        // 常量类禁止实例化
    }

    // 认证模块
    public static final String AUTH_ACCESS_PREFIX = "auth:access:";
    public static final String AUTH_REFRESH_PREFIX = "auth:refresh:";
    public static final String AUTH_USER_REFRESH_PREFIX = "auth:user:refresh:";

    // 业务模块
    public static final String STUDENT_PREFIX = "student:";
    public static final String CLASSROOM_PREFIX = "classroom:";
    public static final String COURSE_PREFIX = "course:";
    public static final String SCORE_PREFIX = "score:";
    public static final String TEACHER_PREFIX = "teacher:";
    public static final String COLLEGE_PREFIX = "college:";

    // 缓存有效期（分钟）
    public static final long ENTITY_TTL_MINUTES = 30;
    public static final long SCORE_TTL_MINUTES = 15;
}
