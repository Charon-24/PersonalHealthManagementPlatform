-- 个人健康管理与运动追踪平台
-- 初始化建表脚本（MySQL 8.0+）
-- 路径：src/main/resources/db/V1__init_schema.sql

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- =========================
-- 1. 角色表
-- =========================
CREATE TABLE IF NOT EXISTS `role` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '角色ID（自增）',
  `role_name` VARCHAR(50) NOT NULL COMMENT '角色名称',
  `role_key` VARCHAR(50) NOT NULL COMMENT '角色标识',
  `permissions` VARCHAR(1000) NULL COMMENT '权限标识JSON数组',
  `remark` VARCHAR(255) NULL COMMENT '角色描述',
  `create_time` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `update_time` DATETIME(6) NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_name` (`role_name`),
  UNIQUE KEY `uk_role_key` (`role_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色信息表';

-- =========================
-- 2. 普通用户表
-- =========================
CREATE TABLE IF NOT EXISTS `user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID（自增）',
  `username` VARCHAR(50) NOT NULL COMMENT '用户账号',
  `password` VARCHAR(255) NOT NULL COMMENT '登录密码（加密）',
  `phone` VARCHAR(11) NULL COMMENT '手机号',
  `email` VARCHAR(100) NULL COMMENT '电子邮箱',
  `real_name` VARCHAR(50) NULL COMMENT '真实姓名',
  `age` INT NULL COMMENT '年龄',
  `sex` VARCHAR(10) NULL COMMENT '性别',
  `avatar` VARCHAR(255) NULL COMMENT '头像地址',
  `status` INT NULL DEFAULT 1 COMMENT '状态（0禁用/1启用）',
  `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '软删除（0正常/1已删除）',
  `create_time` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '注册时间',
  `update_time` DATETIME(6) NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_username` (`username`),
  UNIQUE KEY `uk_user_phone` (`phone`),
  UNIQUE KEY `uk_user_email` (`email`),
  KEY `idx_user_status` (`status`),
  KEY `idx_user_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='普通用户信息表';

-- =========================
-- 3. 管理员表
-- =========================
CREATE TABLE IF NOT EXISTS `admin` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '管理员ID（自增）',
  `username` VARCHAR(50) NOT NULL COMMENT '管理员账号',
  `password` VARCHAR(255) NOT NULL COMMENT '登录密码（加密）',
  `real_name` VARCHAR(50) NULL COMMENT '真实姓名',
  `sex` VARCHAR(10) NULL COMMENT '性别',
  `email` VARCHAR(100) NULL COMMENT '电子邮箱',
  `avatar` VARCHAR(255) NULL COMMENT '头像地址',
  `role_id` BIGINT NOT NULL COMMENT '角色ID',
  `status` INT NULL DEFAULT 1 COMMENT '状态（0禁用/1启用）',
  `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '软删除（0正常/1已删除）',
  `create_time` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `update_time` DATETIME(6) NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_admin_username` (`username`),
  KEY `idx_admin_role_id` (`role_id`),
  KEY `idx_admin_status` (`status`),
  CONSTRAINT `fk_admin_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
    ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理员信息表';

-- =========================
-- 4. 健康档案表
-- =========================
CREATE TABLE IF NOT EXISTS `health_file` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '档案ID（自增）',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `height` DECIMAL(5,1) NULL COMMENT '身高(cm)',
  `weight` DECIMAL(5,1) NULL COMMENT '体重(kg)',
  `allergy_info` VARCHAR(255) NULL COMMENT '过敏史',
  `medical_history` VARCHAR(255) NULL COMMENT '既往病史',
  `emergency_contact` VARCHAR(50) NULL COMMENT '紧急联系人',
  `contact_phone` VARCHAR(11) NULL COMMENT '联系电话',
  `create_time` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `update_time` DATETIME(6) NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_health_file_user_id` (`user_id`),
  CONSTRAINT `fk_health_file_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
    ON UPDATE RESTRICT ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='健康档案表';

-- =========================
-- 5. 健康数据表
-- =========================
CREATE TABLE IF NOT EXISTS `health_data` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '数据ID（自增）',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `blood_pressure_high` INT NULL COMMENT '收缩压(mmHg)',
  `blood_pressure_low` INT NULL COMMENT '舒张压(mmHg)',
  `blood_sugar` DECIMAL(4,1) NULL COMMENT '血糖(mmol/L)',
  `heart_rate` INT NULL COMMENT '心率(次/分)',
  `sleep_duration` DECIMAL(3,1) NULL COMMENT '睡眠时长(h)',
  `weight` DECIMAL(5,1) NULL COMMENT '当日体重(kg)',
  `record_date` DATE NOT NULL COMMENT '记录日期',
  `create_time` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_health_data_user_record_date` (`user_id`, `record_date`),
  KEY `idx_health_data_record_date` (`record_date`),
  CONSTRAINT `fk_health_data_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
    ON UPDATE RESTRICT ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='健康数据表';

-- =========================
-- 6. 运动项目表
-- =========================
CREATE TABLE IF NOT EXISTS `sport_item` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '项目ID（自增）',
  `item_name` VARCHAR(50) NOT NULL COMMENT '运动项目名称',
  `item_type` VARCHAR(30) NOT NULL COMMENT '项目类型（有氧/无氧）',
  `calorie_per_hour` INT NOT NULL COMMENT '每小时卡路里消耗',
  `difficulty` VARCHAR(20) NULL COMMENT '难度等级（低/中/高）',
  `remark` VARCHAR(255) NULL COMMENT '项目描述',
  `status` INT NULL DEFAULT 1 COMMENT '状态（0禁用/1启用）',
  `create_time` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_sport_item_status` (`status`),
  KEY `idx_sport_item_type` (`item_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='运动项目表';

-- =========================
-- 7. 运动计划表
-- =========================
CREATE TABLE IF NOT EXISTS `sport_plan` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '计划ID（自增）',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `plan_name` VARCHAR(50) NOT NULL COMMENT '计划名称',
  `sport_item_id` BIGINT NOT NULL COMMENT '运动项目ID',
  `plan_cycle` VARCHAR(20) NOT NULL COMMENT '计划周期（周/月）',
  `sport_frequency` INT NOT NULL COMMENT '每周运动次数',
  `sport_duration` INT NOT NULL COMMENT '每次运动时长（分钟）',
  `target` VARCHAR(255) NULL COMMENT '运动目标',
  `status` INT NULL DEFAULT 1 COMMENT '状态（0暂停/1进行中/2完成）',
  `start_date` DATE NOT NULL COMMENT '计划开始日期',
  `end_date` DATE NULL COMMENT '计划结束日期',
  `create_time` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `update_time` DATETIME(6) NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_sport_plan_user_status` (`user_id`, `status`),
  KEY `idx_sport_plan_user_start_date` (`user_id`, `start_date`),
  KEY `idx_sport_plan_item_id` (`sport_item_id`),
  CONSTRAINT `fk_sport_plan_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
    ON UPDATE RESTRICT ON DELETE CASCADE,
  CONSTRAINT `fk_sport_plan_item` FOREIGN KEY (`sport_item_id`) REFERENCES `sport_item` (`id`)
    ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='运动计划表';

-- =========================
-- 8. 运动记录表
-- =========================
CREATE TABLE IF NOT EXISTS `sport_record` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '记录ID（自增）',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `plan_id` BIGINT NULL COMMENT '运动计划ID',
  `sport_item_id` BIGINT NOT NULL COMMENT '运动项目ID',
  `sport_time` DATETIME(6) NOT NULL COMMENT '运动时间',
  `duration` INT NOT NULL COMMENT '运动时长（分钟）',
  `distance` DECIMAL(5,2) NULL COMMENT '运动里程（km）',
  `calorie` INT NOT NULL COMMENT '消耗卡路里',
  `heart_rate` INT NULL COMMENT '平均心率',
  `remark` VARCHAR(255) NULL COMMENT '运动备注',
  `create_time` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_sport_record_user_sport_time` (`user_id`, `sport_time`),
  KEY `idx_sport_record_plan_id` (`plan_id`),
  KEY `idx_sport_record_item_id` (`sport_item_id`),
  CONSTRAINT `fk_sport_record_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
    ON UPDATE RESTRICT ON DELETE CASCADE,
  CONSTRAINT `fk_sport_record_plan` FOREIGN KEY (`plan_id`) REFERENCES `sport_plan` (`id`)
    ON UPDATE RESTRICT ON DELETE SET NULL,
  CONSTRAINT `fk_sport_record_item` FOREIGN KEY (`sport_item_id`) REFERENCES `sport_item` (`id`)
    ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='运动记录表';

-- =========================
-- 9. 饮食食谱表
-- =========================
CREATE TABLE IF NOT EXISTS `diet_recipe` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '食谱ID（自增）',
  `recipe_name` VARCHAR(50) NOT NULL COMMENT '食谱名称',
  `food_material` VARCHAR(255) NOT NULL COMMENT '食材成分',
  `calories` INT NOT NULL COMMENT '总卡路里',
  `protein` DECIMAL(4,1) NULL COMMENT '蛋白质(g)',
  `fat` DECIMAL(4,1) NULL COMMENT '脂肪(g)',
  `carbohydrate` DECIMAL(4,1) NULL COMMENT '碳水化合物(g)',
  `suitable_crowd` VARCHAR(100) NULL COMMENT '适用人群',
  `remark` VARCHAR(255) NULL COMMENT '食谱说明',
  `status` INT NULL DEFAULT 1 COMMENT '状态（0禁用/1启用）',
  `create_time` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_diet_recipe_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='饮食食谱表';

-- =========================
-- 10. 饮食记录表
-- =========================
CREATE TABLE IF NOT EXISTS `diet_record` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '记录ID（自增）',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `recipe_id` BIGINT NULL COMMENT '食谱ID',
  `food_name` VARCHAR(50) NOT NULL COMMENT '食物名称',
  `intake` VARCHAR(30) NOT NULL COMMENT '摄入量',
  `calories` INT NOT NULL COMMENT '摄入卡路里',
  `record_time` DATETIME(6) NOT NULL COMMENT '摄入时间',
  `create_time` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_diet_record_user_record_time` (`user_id`, `record_time`),
  KEY `idx_diet_record_recipe_id` (`recipe_id`),
  CONSTRAINT `fk_diet_record_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
    ON UPDATE RESTRICT ON DELETE CASCADE,
  CONSTRAINT `fk_diet_record_recipe` FOREIGN KEY (`recipe_id`) REFERENCES `diet_recipe` (`id`)
    ON UPDATE RESTRICT ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='饮食记录表';

-- =========================
-- 11. 系统日志表
-- =========================
CREATE TABLE IF NOT EXISTS `system_log` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '日志ID（自增）',
  `user_type` VARCHAR(20) NOT NULL COMMENT '用户类型（管理员/普通用户）',
  `user_id` BIGINT NOT NULL COMMENT '用户/管理员ID',
  `username` VARCHAR(50) NOT NULL COMMENT '账号',
  `operation` VARCHAR(100) NOT NULL COMMENT '操作内容',
  `ip_address` VARCHAR(50) NULL COMMENT '操作IP地址',
  `create_time` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '操作时间',
  PRIMARY KEY (`id`),
  KEY `idx_system_log_user_type_user_id_time` (`user_type`, `user_id`, `create_time`),
  KEY `idx_system_log_username` (`username`),
  KEY `idx_system_log_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统日志表';

SET FOREIGN_KEY_CHECKS = 1;
