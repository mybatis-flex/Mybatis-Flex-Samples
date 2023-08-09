package com.mybatisflex;

import com.mybatisflex.domain.User;
import com.mybatisflex.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class MybatisFlexApplicationTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * 测试新增
     */
    @Test
    public void testAdd() {
        User user = new User();
        user.setName("许大仙");
        user.setEmail("123456789@qq.com");

        userMapper.insert(user);
    }

    /**
     * 测试查询所有信息
     */
    @Test
    public void testFindAll() {
        List<User> userList = userMapper.selectAll();
        Assertions.assertNotNull(userList, "testFindAll单元测试出错啦");
    }


}
