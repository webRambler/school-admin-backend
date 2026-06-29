package com.example.school.common;

public enum ResultCode {

    // 成功
    SUCCESS(200, "操作成功"),

    // 客户端错误
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未登录或登录已过期"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不允许"),

    // 服务端错误
    INTERNAL_ERROR(500, "服务器内部错误"),

    // 业务错误 - 班级
    CLASSROOM_NOT_FOUND(3001, "班级不存在"),

    // 业务错误 - 课程
    COURSE_NOT_FOUND(4001, "课程不存在"),

    // 业务错误 - 学生
    STUDENT_NOT_FOUND(5001, "学生不存在"),

    // 业务错误 - 成绩
    SCORE_NOT_FOUND(6001, "成绩不存在"),
    SCORE_ALREADY_EXISTS(6002, "该学生该课程成绩已存在"),

    // 业务错误 - 教师
    TEACHER_NOT_FOUND(7001, "教师不存在"),

    // 业务错误 - 学院
    COLLEGE_NOT_FOUND(8001, "学院不存在"),

    // 业务错误 - 学院教师关系
    COLLEGE_TEACHER_NOT_FOUND(9001, "学院教师关系不存在");

    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
