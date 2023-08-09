package com.mybatisflex;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan({"com.mybatisflex.mapper"})
@SpringBootApplication
public class MybatisFlexApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisFlexApplication.class, args);
    }

}
