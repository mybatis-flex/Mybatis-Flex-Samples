CREATE DATABASE IF NOT EXISTS `test`;

USE `test`;


CREATE TABLE IF NOT EXISTS `department` (
  id INT PRIMARY KEY AUTO_INCREMENT COMMENT '部门ID',
  name VARCHAR(50) COMMENT '部门名称',
  description VARCHAR(100) COMMENT '部门描述',
  created_time DATETIME DEFAULT NOW() COMMENT '创建时间',
  updated_time DATETIME DEFAULT NOW() ON UPDATE NOW() COMMENT '更新时间'
) COMMENT '部门表';

CREATE TABLE IF NOT EXISTS `user` (
  id INT PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
  name VARCHAR(50) COMMENT '姓名',
  username VARCHAR(50) COMMENT '用户名',
  password VARCHAR(100) COMMENT '密码',
  age INT COMMENT '年龄',
  gender VARCHAR(10) COMMENT '性别' DEFAULT '男',
  address VARCHAR(100) COMMENT '地址',
  phone VARCHAR(20) COMMENT '电话',
  email VARCHAR(50) COMMENT '邮箱',
  department_id INT COMMENT '部门ID',
  created_time DATETIME DEFAULT NOW() COMMENT '创建时间',
  updated_time DATETIME DEFAULT NOW() ON UPDATE NOW() COMMENT '更新时间'
) COMMENT '用户表';

CREATE TABLE IF NOT EXISTS `role` (
  id INT PRIMARY KEY AUTO_INCREMENT COMMENT '角色Id',
  name VARCHAR(50) COMMENT '角色名称',
  description VARCHAR(100) COMMENT '角色描述',
  created_time DATETIME DEFAULT NOW() COMMENT '创建时间',
  updated_time DATETIME DEFAULT NOW() ON UPDATE NOW() COMMENT '更新时间'
) COMMENT '角色表';

CREATE TABLE IF NOT EXISTS `user_role` (
  user_id INT COMMENT '用户ID',
  role_id INT COMMENT '角色ID',
  PRIMARY KEY (user_id, role_id),
  created_time DATETIME DEFAULT NOW() COMMENT '创建时间',
  updated_time DATETIME DEFAULT NOW() ON UPDATE NOW() COMMENT '更新时间'
) COMMENT '用户角色关联表';
