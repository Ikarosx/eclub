SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- avtivity表
CREATE TABLE `activity` (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `title` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '活动标题',
  `time` datetime(0) NOT NULL COMMENT '活动时间',
  `area` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '活动地点',
  `description` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '活动描述',
  `club_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '社团ID',
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户ID',
  `is_top` tinyint(1) NOT NULL COMMENT '0置顶/1不置顶',
  `state` tinyint(1) NOT NULL COMMENT '0正常/1禁用',
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  `operator` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT fk_activity_club_id FOREIGN KEY (`club_id`) REFERENCES club(`id`),
  CONSTRAINT fk_activity_user_id FOREIGN KEY (`user_id`) REFERENCES user(`id`),
  CONSTRAINT fk_activity_operator FOREIGN KEY (`operator`) REFERENCES user(`id`),
  UNIQUE INDEX `unique_title`(`title`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- club表
CREATE TABLE `club` (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '社团名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '社团简介',
  `logo_uri` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'logo/社徽地址',
  `regulation_uri` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '章程文件地址',
  `state` tinyint(1) NOT NULL COMMENT '0正常/1禁用',
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  `operator` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT fk_club_operator FOREIGN KEY (`operator`) REFERENCES user(`id`),
  UNIQUE INDEX `unique_name`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- user_club表
CREATE TABLE `user_club` (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `club_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `year` int(4) NOT NULL COMMENT '年份',
  `state` tinyint(1) NOT NULL COMMENT '0正常/1禁用/2未支付/3禁用',
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  `operator` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT fk_user_club_club_id FOREIGN KEY (`club_id`) REFERENCES club(`id`),
  CONSTRAINT fk_user_club_user_id FOREIGN KEY (`user_id`) REFERENCES user(`id`),
  CONSTRAINT fk_user_club_operator FOREIGN KEY (`operator`) REFERENCES user(`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- department表
CREATE TABLE `department` (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '职位名称',
  `description` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '职位描述',
  `club_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `state` tinyint(1) NOT NULL COMMENT '0正常/1禁用',
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  `operator` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT fk_department_club_id FOREIGN KEY (`club_id`) REFERENCES club(`id`),
  CONSTRAINT fk_department_operator FOREIGN KEY (`operator`) REFERENCES user(`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- staff表
CREATE TABLE `staff` (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `department_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `club_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `year` int(4) NOT NULL,
  `state` tinyint(1) NOT NULL COMMENT '0正常/1禁用',
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  `operator` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT fk_staff_club_id FOREIGN KEY (`club_id`) REFERENCES club(`id`),
  CONSTRAINT fk_staff_department_id FOREIGN KEY (`department_id`) REFERENCES department(`id`),
  CONSTRAINT fk_staff_operator FOREIGN KEY (`operator`) REFERENCES user(`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- 权限
-- 角色表
CREATE TABLE `role` (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `state` tinyint(1) NOT NULL COMMENT '0正常/1禁用',
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  `operator` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY (`name`),
  CONSTRAINT fk_role_operator FOREIGN KEY (`operator`) REFERENCES user(`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- 用户角色表
CREATE TABLE `user_role` (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `state` tinyint(1) NOT NULL COMMENT '0正常/1禁用',
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  `operator` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT fk_user_role_role_id FOREIGN KEY (`role_id`) REFERENCES role(`id`),
  CONSTRAINT fk_user_role_user_id FOREIGN KEY (`user_id`) REFERENCES user(`id`),
  CONSTRAINT fk_user_role_operator FOREIGN KEY (`operator`) REFERENCES user(`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- 权限角色表
CREATE TABLE `permission_role` (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `permission_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `state` tinyint(1) NOT NULL COMMENT '0正常/1禁用',
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  `operator` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT fk_permission_role_permission_id FOREIGN KEY (`permission_id`) REFERENCES permission(`id`),
  CONSTRAINT fk_permission_role_role_id FOREIGN KEY (`role_id`) REFERENCES role(`id`),
  CONSTRAINT fk_permission_role_operator FOREIGN KEY (`operator`) REFERENCES user(`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- 用户表
CREATE TABLE `user` (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `student_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学号',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Bcrypt加密',
  `nickname` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '昵称',
  `user_profile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户头像地址',
  `phone` int(11) NULL DEFAULT NULL,
  `class_grade` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '班级',
  `wx` varchar(64) NULL DEFAULT NULL COMMENT '微信号',
  `qq` varchar(32) NULL DEFAULT NULL COMMENT 'QQ号',
  `email` varchar(64) NULL DEFAULT NULL COMMENT '邮箱',
  `open_id` varchar(255) NOT NULL COMMENT '微信OPENID',
  `state` tinyint(1) NOT NULL COMMENT '0正常/1禁用',
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  `operator` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY (`open_id`),
  UNIQUE KEY (`username`),
  CONSTRAINT fk_user_operator FOREIGN KEY (`operator`) REFERENCES user(`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


-- 权限表
CREATE TABLE `permission` (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `parent_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `is_menu` tinyint(1) NOT NULL COMMENT '0是/1不是',
  `level` int(11) NOT NULL COMMENT '层次',
  `sort` int(11) NOT NULL COMMENT '排序',
  `icon_uri` varchar(32) NOT NULL COMMENT 'icon图标地址',
  `state` tinyint(1) NOT NULL COMMENT '0正常/1禁用',
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  `operator` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT fk_permission_operator FOREIGN KEY (`operator`) REFERENCES user(`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;