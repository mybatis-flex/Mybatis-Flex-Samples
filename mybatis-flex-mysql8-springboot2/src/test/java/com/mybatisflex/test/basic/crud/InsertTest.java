package com.mybatisflex.test.basic.crud;

import com.mybatisflex.MybatisFlexApplication;
import com.mybatisflex.domain.User;
import com.mybatisflex.mapper.UserMapper;
import com.mybatisflex.utils.RandomIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest(classes = {
        MybatisFlexApplication.class})
public class InsertTest {

    @Autowired
    private UserMapper userMapper;

    private LocalDateTime beginTime;

    @BeforeEach
    public void setUp(TestInfo testInfo) {
        String methodName = testInfo.getTestMethod().orElseThrow(() -> new IllegalStateException("没有找到对应的方法名")).getName();
        log.info("方法名 ==> {} ，执行开始", methodName);
        beginTime = LocalDateTime.now();
    }

    @AfterEach
    public void tearDown(TestInfo testInfo) {
        String methodName = testInfo.getTestMethod().orElseThrow(() -> new IllegalStateException("没有找到对应的方法名")).getName();
        LocalDateTime endTime = LocalDateTime.now();
        Duration duration = Duration.between(beginTime, endTime);
        log.info("方法名 ==> {} ，执行结束，耗时 ===> {} 毫秒", methodName, duration.toMillis());
    }

    @Test
    public void testInsert() {
        User tom = new User()
                .setName("许大仙")
                .setPassword("123456")
                .setAge(18)
                .setPhone("1234567890")
                .setEmail("Tom@qq.com");
        tom.setCreatedTime(LocalDateTime.now());
        tom.setUpdatedTime(LocalDateTime.now());

        log.info("testInsert之前，{}", tom);
        userMapper.insert(tom);
        log.info("testInsert之后，{}", tom);

        Assertions.assertNotNull(tom.getId(), "新增完成之后 ID 不能为 null");
        Assertions.assertNull(tom.getUsername(), "username 的值应该是 null");
        Assertions.assertNull(tom.getGender(), "username 的值应该是 null ，而不思默认值");
        Assertions.assertNotNull(userMapper.selectOneById(tom.getId()));
    }

    @Test
    public void testInsertSelective() {
        User tom = new User()
                .setName("许大仙")
                .setUsername("Tom")
                .setPassword("123456")
                .setAge(18)
                .setGender("男")
                .setPhone("1234567890")
                .setEmail("Tom@qq.com");

        userMapper.insertSelective(tom);
    }

    @Test
    public void testInsert2() {
        User tom = new User()
                .setName("许大仙")
                .setUsername("Tom")
                .setPassword("123456")
                .setAge(18)
                .setGender("男")
                .setPhone("1234567890")
                .setEmail("Tom@qq.com");

        // true 表示忽略 null 值
        userMapper.insert(tom, true);

        // false 表示不忽略 null 值
        userMapper.insert(tom, false);
    }

    @Test
    public void testInsertWithPk() {
        User tom = new User()
                .setName("许大仙")
                .setUsername("Tom")
                .setPassword("123456")
                .setAge(18)
                .setGender("男")
                .setPhone("1234567890")
                .setEmail("Tom@qq.com");

        tom.setId(RandomIdGenerator.generateId());

        // 插入带有主键的实体类，不忽略 null 值
        userMapper.insertWithPk(tom);
    }

    @Test
    public void testInsertSelectiveWithPk() {
        User tom = new User()
                .setName("许大仙")
                .setUsername("Tom")
                .setPassword("123456")
                .setAge(18)
                .setGender("男")
                .setPhone("1234567890")
                .setEmail("Tom@qq.com");

        tom.setId(RandomIdGenerator.generateId());

        // 插入带有主键的实体类，忽略 null 值
        userMapper.insertSelectiveWithPk(tom);
    }

    @Test
    public void testInsertWithPk2() {
        User tom = new User()
                .setName("许大仙")
                .setUsername("Tom")
                .setPassword("123456")
                .setAge(18)
                .setGender("男")
                .setPhone("1234567890")
                .setEmail("Tom@qq.com");

        tom.setId(RandomIdGenerator.generateId());

        // 带有主键的插入，此时实体类不会经过主键生成器生成主键，忽略 null 值
        userMapper.insertWithPk(tom, true);


        tom.setId(RandomIdGenerator.generateId());

        // 带有主键的插入，此时实体类不会经过主键生成器生成主键，不忽略 null 值
        userMapper.insertWithPk(tom, false);
    }

    @Test
    public void testInsertBatch() {
        List<User> userList = new ArrayList<>();
        userList.add(new User().setName("哈哈").setAge(20));
        userList.add(new User().setName("嘻嘻").setEmail("123@qq.com"));
        userList.add(new User().setName("呵呵").setGender("男"));
        userList.add(new User().setName("哼哼").setAddress("北京，对吗？"));

        // 批量插入实体类数据，只会根据第一条数据来构建插入的字段内容。
        userMapper.insertBatch(userList);
    }

    @Test
    public void testInsertBatch2() {
        List<User> userList = new ArrayList<>();
        userList.add(new User().setName("哈哈").setAge(20));
        userList.add(new User().setName("嘻嘻").setEmail("123@qq.com"));
        userList.add(new User().setName("呵呵").setGender("男"));
        userList.add(new User().setName("哼哼").setAddress("北京，对吗？"));
        userList.add(new User().setName("哈哈1").setAddress("北京"));
        userList.add(new User().setName("哈哈2").setAddress("上海"));
        userList.add(new User().setName("哈哈3").setAddress("天津"));

        // 批量插入实体类数据，按 size 切分
        userMapper.insertBatch(userList, 2);
    }

    @Test
    public void testInsertOrUpdate() {
        User tom = new User()
                .setName("许大仙")
                .setUsername("Tom")
                .setPassword("123456")
                .setAge(18)
                .setGender("男")
                .setPhone("1234567890")
                .setEmail("Tom@qq.com");

        tom.setId(RandomIdGenerator.generateId());

        // 插入或者更新，若主键有值，则更新，若没有主键值，则插入，插入或者更新都不会忽略 null 值。
        userMapper.insertOrUpdate(tom);

        tom.setId(null);
        // 插入或者更新，若主键有值，则更新，若没有主键值，则插入，插入或者更新都不会忽略 null 值。
        userMapper.insertOrUpdate(tom);
    }


    @Test
    public void testInsertOrUpdateSelective() {
        User tom = new User()
                .setName("许大仙")
                .setUsername("Tom")
                .setPassword("123456")
                .setAge(18)
                .setGender("男")
                .setPhone("1234567890")
                .setEmail("Tom@qq.com");

        tom.setId(RandomIdGenerator.generateId());

        // 插入或者更新，若主键有值，则更新，若没有主键值，则插入，插入或者更新都会忽略 null 值。
        userMapper.insertOrUpdateSelective(tom);

        tom.setId(null);
        // 插入或者更新，若主键有值，则更新，若没有主键值，则插入，插入或者更新都会忽略 null 值。
        userMapper.insertOrUpdateSelective(tom);
    }

    @Test
    public void testInsertOrUpdate2() {
        User tom = new User()
                .setName("许大仙")
                .setUsername("Tom")
                .setPassword("123456")
                .setAge(18)
                .setGender("男")
                .setPhone("1234567890")
                .setEmail("Tom@qq.com");

        tom.setId(RandomIdGenerator.generateId());

        // 插入或者更新，若主键有值，则更新，若没有主键值，则插入，插入或者更新都会忽略 null 值。
        userMapper.insertOrUpdate(tom, true);

        tom.setId(null);
        // 插入或者更新，若主键有值，则更新，若没有主键值，则插入，插入或者更新都不会忽略 null 值。
        userMapper.insertOrUpdate(tom, false);
    }


}
