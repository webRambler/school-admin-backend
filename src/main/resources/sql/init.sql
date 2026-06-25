-- ============================================
-- 学校管理系统 - 数据库初始化脚本
-- 数据库：school
-- 表：college, class_room, course, student, score
-- 关系：
--   college 1:N class_room (一个学院有多个班级)
--   class_room 1:N student (一个班级有多个学生)
--   student N:M course   (通过score表实现多对多)
--   student 1:N score    (一个学生有多门成绩)
--   course 1:N score     (一门课程有多个学生的成绩)
-- ============================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS school DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE school;

-- 学院表
DROP TABLE IF EXISTS `college`;
CREATE TABLE `college` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '学院ID',
    `name` VARCHAR(50) NOT NULL COMMENT '学院名称',
    `code` VARCHAR(20) NOT NULL COMMENT '学院代码',
    `description` VARCHAR(200) DEFAULT NULL COMMENT '学院简介',
    `dean` VARCHAR(20) DEFAULT NULL COMMENT '院长',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_code` (`code`),
    KEY `idx_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学院表';

-- 班级表
DROP TABLE IF EXISTS `class_room`;
CREATE TABLE `class_room` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '班级ID',
    `college_id` BIGINT DEFAULT NULL COMMENT '学院ID',
    `name` VARCHAR(50) NOT NULL COMMENT '班级名称',
    `grade` VARCHAR(20) NOT NULL COMMENT '年级',
    `major` VARCHAR(50) NOT NULL COMMENT '专业',
    `teacher` VARCHAR(20) DEFAULT NULL COMMENT '班主任',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_college_id` (`college_id`),
    KEY `idx_grade` (`grade`),
    KEY `idx_major` (`major`),
    CONSTRAINT `fk_classroom_college` FOREIGN KEY (`college_id`) REFERENCES `college` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='班级表';

-- 课程表
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '课程ID',
    `name` VARCHAR(50) NOT NULL COMMENT '课程名称',
    `credit` DECIMAL(3,1) NOT NULL COMMENT '学分',
    `hours` INT NOT NULL COMMENT '学时',
    `semester` VARCHAR(20) DEFAULT NULL COMMENT '学期',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_semester` (`semester`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程表';

-- 学生表
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '学生ID',
    `name` VARCHAR(20) NOT NULL COMMENT '姓名',
    `gender` VARCHAR(5) NOT NULL COMMENT '性别',
    `age` INT NOT NULL COMMENT '年龄',
    `class_id` BIGINT NOT NULL COMMENT '班级ID',
    `phone` VARCHAR(15) DEFAULT NULL COMMENT '手机号',
    `email` VARCHAR(50) DEFAULT NULL COMMENT '邮箱',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_class_id` (`class_id`),
    KEY `idx_name` (`name`),
    KEY `idx_gender` (`gender`),
    CONSTRAINT `fk_student_class` FOREIGN KEY (`class_id`) REFERENCES `class_room` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生表';

-- 成绩表
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '成绩ID',
    `student_id` BIGINT NOT NULL COMMENT '学生ID',
    `course_id` BIGINT NOT NULL COMMENT '课程ID',
    `score` DECIMAL(5,2) NOT NULL COMMENT '分数',
    `exam_type` VARCHAR(20) NOT NULL COMMENT '考试类型(期中/期末/补考)',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_student_course_exam` (`student_id`, `course_id`, `exam_type`),
    KEY `idx_student_id` (`student_id`),
    KEY `idx_course_id` (`course_id`),
    KEY `idx_score` (`score`),
    KEY `idx_exam_type` (`exam_type`),
    CONSTRAINT `fk_score_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_score_course` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='成绩表';

-- 教师表
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '教师ID',
    `name` VARCHAR(20) NOT NULL COMMENT '姓名',
    `gender` VARCHAR(5) NOT NULL COMMENT '性别',
    `age` INT NOT NULL COMMENT '年龄',
    `title` VARCHAR(30) DEFAULT NULL COMMENT '职称(讲师/副教授/教授等)',
    `department` VARCHAR(50) NOT NULL COMMENT '所属院系',
    `phone` VARCHAR(15) DEFAULT NULL COMMENT '手机号',
    `email` VARCHAR(50) DEFAULT NULL COMMENT '邮箱',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_name` (`name`),
    KEY `idx_department` (`department`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='教师表';

-- ============================================
-- 插入测试数据
-- ============================================

-- 学院数据
INSERT INTO `college` (`name`, `code`, `description`, `dean`, `phone`) VALUES
('计算机与信息工程学院', 'CS', '负责计算机科学、软件工程等专业的教学与科研', '刘院长', '010-12345001'),
('软件学院', 'SE', '负责软件工程专业的教学与科研', '陈院长', '010-12345002'),
('大数据学院', 'DS', '负责数据科学与大数据技术专业的教学与科研', '张院长', '010-12345003'),
('人工智能学院', 'AI', '负责人工智能专业的教学与科研', '王院长', '010-12345004');

-- 班级数据
INSERT INTO `class_room` (`college_id`, `name`, `grade`, `major`, `teacher`) VALUES
('1', '计算机2301班', '2023级', '计算机科学与技术', '张老师'),
('1', '计算机2302班', '2023级', '计算机科学与技术', '李老师'),
('2', '软件2301班', '2023级', '软件工程', '王老师'),
('3', '数据2301班', '2023级', '数据科学与大数据技术', '赵老师'),
('4', '人工智能2301班', '2023级', '人工智能', '錢老师');

-- 课程数据
INSERT INTO `course` (`name`, `credit`, `hours`, `semester`) VALUES
('高等数学', 5.0, 80, '2023-2024-1'),
('线性代数', 3.0, 48, '2023-2024-1'),
('大学物理', 4.0, 64, '2023-2024-1'),
('程序设计基础', 4.0, 64, '2023-2024-1'),
('数据结构', 4.0, 64, '2023-2024-2'),
('操作系统', 3.5, 56, '2023-2024-2'),
('数据库原理', 3.5, 56, '2023-2024-2'),
('计算机网络', 3.0, 48, '2023-2024-2');

-- 学生数据（每个班级若干学生）
INSERT INTO `student` (`name`, `gender`, `age`, `class_id`, `phone`, `email`) VALUES
-- 计算机2301班
('陈小明', '男', 20, 1, '13800000001', 'chenxm@example.com'),
('王小红', '女', 19, 1, '13800000002', 'wangxh@example.com'),
('张小刚', '男', 20, 1, '13800000003', 'zhangxg@example.com'),
('李小芳', '女', 19, 1, '13800000004', 'lixf@example.com'),
('刘小伟', '男', 21, 1, '13800000005', 'liuxw@example.com'),
-- 计算机2302班
('赵小丽', '女', 20, 2, '13800000006', 'zhaoxl@example.com'),
('孙小强', '男', 19, 2, '13800000007', 'sunxq@example.com'),
('周小梅', '女', 20, 2, '13800000008', 'zhouxm@example.com'),
('吴小波', '男', 21, 2, '13800000009', 'wuxb@example.com'),
('郑小雪', '女', 19, 2, '13800000010', 'zhengxx@example.com'),
-- 软件2301班
('钱小龙', '男', 20, 3, '13800000011', 'qianxl@example.com'),
('冯小凤', '女', 19, 3, '13800000012', 'fengxf@example.com'),
('蒋小军', '男', 20, 3, '13800000013', 'jiangxj@example.com'),
('沈小琴', '女', 21, 3, '13800000014', 'shenxq@example.com'),
-- 数据2301班
('韩小鹏', '男', 20, 4, '13800000015', 'hanxp@example.com'),
('杨小兰', '女', 19, 4, '13800000016', 'yangxl@example.com'),
('朱小宇', '男', 20, 4, '13800000017', 'zhuxy@example.com'),
-- 人工智能2301班
('秦小华', '女', 19, 5, '13800000018', 'qinxh@example.com'),
('许小飞', '男', 21, 5, '13800000019', 'xuxf@example.com');

-- 成绩数据（每个学生多门课程成绩）
INSERT INTO `score` (`student_id`, `course_id`, `score`, `exam_type`) VALUES
-- 陈小明
(1, 1, 92.5, '期末'), (1, 2, 88.0, '期末'), (1, 4, 95.0, '期末'), (1, 5, 90.0, '期末'),
-- 王小红
(2, 1, 78.5, '期末'), (2, 2, 85.0, '期末'), (2, 4, 82.0, '期末'), (2, 5, 76.0, '期末'),
-- 张小刚
(3, 1, 56.0, '期末'), (3, 2, 62.0, '期末'), (3, 4, 71.0, '期末'), (3, 5, 58.0, '期末'),
-- 李小芳
(4, 1, 88.0, '期末'), (4, 2, 91.0, '期末'), (4, 4, 86.5, '期末'), (4, 5, 92.0, '期末'),
-- 刘小伟
(5, 1, 45.0, '期末'), (5, 2, 52.0, '期末'), (5, 4, 67.0, '期末'), (5, 5, 55.0, '期末'),
-- 赵小丽
(6, 1, 90.0, '期末'), (6, 2, 93.5, '期末'), (6, 3, 87.0, '期末'), (6, 6, 91.0, '期末'),
-- 孙小强
(7, 1, 72.0, '期末'), (7, 2, 68.0, '期末'), (7, 3, 75.0, '期末'), (7, 6, 70.5, '期末'),
-- 周小梅
(8, 1, 83.0, '期末'), (8, 2, 79.0, '期末'), (8, 3, 88.0, '期末'), (8, 6, 85.5, '期末'),
-- 吴小波
(9, 1, 91.0, '期末'), (9, 2, 86.0, '期末'), (9, 3, 92.0, '期末'), (9, 6, 88.0, '期末'),
-- 郑小雪
(10, 1, 65.0, '期末'), (10, 2, 59.0, '期末'), (10, 3, 70.0, '期末'), (10, 6, 63.0, '期末'),
-- 钱小龙
(11, 1, 87.0, '期末'), (11, 4, 93.0, '期末'), (11, 7, 89.5, '期末'), (11, 8, 84.0, '期末'),
-- 冯小凤
(12, 1, 79.0, '期末'), (12, 4, 85.0, '期末'), (12, 7, 82.0, '期末'), (12, 8, 78.5, '期末'),
-- 蒋小军
(13, 1, 42.0, '期末'), (13, 4, 55.0, '期末'), (13, 7, 48.0, '期末'), (13, 8, 51.0, '期末'),
-- 沈小琴
(14, 1, 94.0, '期末'), (14, 4, 97.0, '期末'), (14, 7, 91.0, '期末'), (14, 8, 93.5, '期末'),
-- 韩小鹏
(15, 1, 76.0, '期末'), (15, 2, 82.0, '期末'), (15, 5, 79.5, '期末'), (15, 7, 85.0, '期末'),
-- 杨小兰
(16, 1, 89.0, '期末'), (16, 2, 92.0, '期末'), (16, 5, 86.0, '期末'), (16, 7, 90.0, '期末'),
-- 朱小宇
(17, 1, 58.0, '期末'), (17, 2, 63.0, '期末'), (17, 5, 55.5, '期末'), (17, 7, 61.0, '期末'),
-- 秦小华
(18, 1, 95.0, '期末'), (18, 4, 98.0, '期末'), (18, 5, 93.0, '期末'), (18, 8, 96.5, '期末'),
-- 许小飞
(19, 1, 73.0, '期末'), (19, 4, 68.0, '期末'), (19, 5, 75.0, '期末'), (19, 8, 70.0, '期末');

-- 教师数据
INSERT INTO `teacher` (`name`, `gender`, `age`, `title`, `department`, `phone`, `email`) VALUES
('张建国', '男', 45, '教授', '计算机学院', '13900001001', 'zhangjg@school.edu'),
('李秀英', '女', 38, '副教授', '计算机学院', '13900001002', 'lixye@school.edu'),
('王志强', '男', 52, '教授', '软件学院', '13900001003', 'wangzq@school.edu'),
('赵美华', '女', 35, '讲师', '数学学院', '13900001004', 'zhaomh@school.edu'),
('孙国栋', '男', 41, '副教授', '数据科学学院', '13900001005', 'sungd@school.edu'),
('周丽娟', '女', 29, '讲师', '人工智能学院', '13900001006', 'zhoulj@school.edu'),
('吴大伟', '男', 48, '教授', '软件学院', '13900001007', 'wudw@school.edu'),
('郑小燕', '女', 33, '讲师', '计算机学院', '13900001008', 'zhengxy@school.edu');

-- 补考成绩
INSERT INTO `score` (`student_id`, `course_id`, `score`, `exam_type`) VALUES
(3, 1, 65.0, '补考'),
(5, 1, 60.0, '补考'),
(5, 2, 62.0, '补考'),
(10, 2, 61.0, '补考'),
(13, 1, 55.0, '补考'),
(13, 4, 60.0, '补考'),
(13, 7, 58.0, '补考'),
(17, 1, 62.0, '补考'),
(17, 5, 60.0, '补考');

-- ============================================
-- 用户登录表
-- ============================================

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username` VARCHAR(50) NOT NULL COMMENT '登录账号',
    `password` VARCHAR(100) NOT NULL COMMENT 'BCrypt 加密密码',
    `nickname` VARCHAR(50) DEFAULT NULL COMMENT '昵称',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- 初始管理员账号，密码明文：admin123
INSERT INTO `user` (`username`, `password`, `nickname`) VALUES
('admin', '$2a$10$cK0ybzIP0.7Ut//AskOiC.qRnpRRUGaY79b9p6NrHqlmvD5ggLEQa', '管理员');
