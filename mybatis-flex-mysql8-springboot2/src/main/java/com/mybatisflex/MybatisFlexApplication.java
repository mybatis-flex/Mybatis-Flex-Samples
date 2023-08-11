package com.mybatisflex;

import com.mybatisflex.mapper.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan({"com.mybatisflex.mapper"})
public class MybatisFlexApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MybatisFlexApplication.class, args);
        UserMapper bean = context.getBean(UserMapper.class);
        System.out.println("MybatisFlexApplication" + bean);
    }

}
