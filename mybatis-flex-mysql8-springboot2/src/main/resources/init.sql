CREATE DATABASE if not exists test;
USE test;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    email VARCHAR(50)
);

INSERT INTO users (name, email) VALUES ('张三', 'zhangsan@qq.com');
INSERT INTO users (name, email) VALUES ('李四', 'lisi@qq.com');