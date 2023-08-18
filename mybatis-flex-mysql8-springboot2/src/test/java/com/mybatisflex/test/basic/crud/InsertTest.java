package com.mybatisflex.test.basic.crud;

import com.mybatisflex.MybatisFlexApplication;
import com.mybatisflex.core.query.QueryWrapper;
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

import static com.mybatisflex.domain.table.UserTableDef.USER;

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

    /**
     * insert(entity)：插入实体类数据，不忽略 null 值
     */
    @Test
    public void testInsert() {
        User user = new User()
                .setName("许大仙")
                .setPassword("123456")
                .setAge(18)
                .setPhone("1234567890")
                .setEmail("Tom@qq.com");
        LocalDateTime now = LocalDateTime.now();
        user.setCreatedTime(now);

        log.info("testInsert之前，{}", user);
        userMapper.insert(user); // 插入实体类数据，不忽略 null 值。
        User userDb = userMapper.selectOneById(user.getId());
        log.info("testInsert之后，{}", userDb);

        Assertions.assertNotNull(userDb.getId(), "新增完成之后 ID 不能为 null");
        Assertions.assertEquals("许大仙", userDb.getName());
        Assertions.assertEquals("123456", userDb.getPassword());
        Assertions.assertEquals(18, userDb.getAge());
        Assertions.assertNull(userDb.getGender(), "gender 的值应该是 null");
        Assertions.assertNull(userDb.getAddress(), "address 的值应该是 null");
        Assertions.assertEquals("1234567890", userDb.getPhone());
        Assertions.assertEquals("Tom@qq.com", userDb.getEmail());
        Assertions.assertTrue(userDb.getCreatedTime().toLocalDate().isEqual(now.toLocalDate()));
        Assertions.assertNull(userDb.getUsername(), "username 的值应该是 null");
        Assertions.assertNull(userDb.getUpdatedTime(), "updatedTime 的值应该是 null");

        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.select(USER.ALL_COLUMNS)
                .from(USER)
                .where(USER.ID.eq(userDb.getId()));
        List<User> userList = userMapper.selectListByQuery(queryWrapper);
        Assertions.assertNotNull(userList, "没有查询到刚插入的数据");
    }

    /**
     * insertSelective(entity)：插入实体类数据，但是忽略 null 的数据，只对有值的内容进行插入。
     * 这样的好处是数据库已经配置了一些默认值，这些默认值才会生效。
     */
    @Test
    public void testInsertSelective() {
        User user = new User()
                .setName("许大仙")
                .setPassword("123456")
                .setAge(18)
                .setPhone("1234567890")
                .setEmail("Tom@qq.com");
        LocalDateTime now = LocalDateTime.now();
        user.setCreatedTime(now);

        log.info("testInsertSelective之前，{}", user);
        userMapper.insertSelective(user); // 插入实体类数据，但是忽略 null 的数据，只对有值的内容进行插入
        User userDb = userMapper.selectOneById(user.getId());
        log.info("testInsertSelective之后，{}", userDb);

        Assertions.assertNotNull(user.getId(), "新增完成之后 ID 不能为 null");
        Assertions.assertEquals("许大仙", userDb.getName());
        Assertions.assertEquals("123456", userDb.getPassword());
        Assertions.assertEquals(18, userDb.getAge());
        Assertions.assertEquals("男", userDb.getGender());
        Assertions.assertNull(userDb.getAddress(), "address 的值应该是 null");
        Assertions.assertEquals("1234567890", userDb.getPhone());
        Assertions.assertEquals("Tom@qq.com", userDb.getEmail());
        Assertions.assertTrue(userDb.getCreatedTime().toLocalDate().isEqual(now.toLocalDate()));
        Assertions.assertNull(userDb.getUsername(), "username 的值应该是 null");
        Assertions.assertNotNull(userDb.getUpdatedTime(), "updatedTime 的值不应该是 null");

        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.select(USER.ALL_COLUMNS)
                .from(USER)
                .where(USER.ID.eq(userDb.getId()));
        List<User> userList = userMapper.selectListByQuery(queryWrapper);
        Assertions.assertNotNull(userList, "没有查询到刚插入的数据");
    }

    /**
     * insert(entity, ignoreNulls)：插入实体类数据。
     * ignoreNulls = true 表示忽略 null 值 ==> 相当于 insertSelective 方法
     */
    @Test
    public void testInsert2() {
        User user = new User()
                .setName("许大仙")
                .setPassword("123456")
                .setAge(18)
                .setPhone("1234567890")
                .setEmail("Tom@qq.com");
        LocalDateTime now = LocalDateTime.now();
        user.setCreatedTime(now);

        log.info("testInsert2之前，{}", user);
        // true 表示忽略 null 值 ==> 相当于 insertSelective 方法
        userMapper.insert(user, true);
        User userDb = userMapper.selectOneById(user.getId());
        log.info("testInsert2之后，{}", userDb);

        Assertions.assertNotNull(user.getId(), "新增完成之后 ID 不能为 null");
        Assertions.assertEquals("许大仙", userDb.getName());
        Assertions.assertEquals("123456", userDb.getPassword());
        Assertions.assertEquals(18, userDb.getAge());
        Assertions.assertEquals("男", userDb.getGender());
        Assertions.assertNull(userDb.getAddress(), "address 的值应该是 null");
        Assertions.assertEquals("1234567890", userDb.getPhone());
        Assertions.assertEquals("Tom@qq.com", userDb.getEmail());
        Assertions.assertTrue(userDb.getCreatedTime().toLocalDate().isEqual(now.toLocalDate()));
        Assertions.assertNull(userDb.getUsername(), "username 的值应该是 null");
        Assertions.assertNotNull(userDb.getUpdatedTime(), "updatedTime 的值不应该是 null");

        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.select(USER.ALL_COLUMNS)
                .from(USER)
                .where(USER.ID.eq(userDb.getId()));
        List<User> userList = userMapper.selectListByQuery(queryWrapper);
        Assertions.assertNotNull(userList, "没有查询到刚插入的数据");
    }

    /**
     * insert(entity, ignoreNulls)：插入实体类数据。
     * ignoreNulls = false 表示不忽略 null 值 ==> 相当于 insert 方法
     */
    @Test
    public void testInsert3() {
        User user = new User()
                .setName("许大仙")
                .setPassword("123456")
                .setAge(18)
                .setPhone("1234567890")
                .setEmail("Tom@qq.com");
        LocalDateTime now = LocalDateTime.now();
        user.setCreatedTime(now);

        log.info("testInsert3之前，{}", user);
        // false 表示不忽略 null 值 ==> 相当于 insert 方法
        userMapper.insert(user, false);
        User userDb = userMapper.selectOneById(user.getId());
        log.info("testInsert3之后，{}", userDb);

        Assertions.assertNotNull(userDb.getId(), "新增完成之后 ID 不能为 null");
        Assertions.assertEquals("许大仙", userDb.getName());
        Assertions.assertEquals("123456", userDb.getPassword());
        Assertions.assertEquals(18, userDb.getAge());
        Assertions.assertNull(userDb.getGender(), "gender 的值应该是 null");
        Assertions.assertNull(userDb.getAddress(), "address 的值应该是 null");
        Assertions.assertEquals("1234567890", userDb.getPhone());
        Assertions.assertEquals("Tom@qq.com", userDb.getEmail());
        Assertions.assertTrue(userDb.getCreatedTime().toLocalDate().isEqual(now.toLocalDate()));
        Assertions.assertNull(userDb.getUsername(), "username 的值应该是 null");
        Assertions.assertNull(userDb.getUpdatedTime(), "updatedTime 的值应该是 null");

        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.select(USER.ALL_COLUMNS)
                .from(USER)
                .where(USER.ID.eq(userDb.getId()));
        List<User> userList = userMapper.selectListByQuery(queryWrapper);
        Assertions.assertNotNull(userList, "没有查询到刚插入的数据");
    }

    /**
     * insertWithPk(entity)：插入带有主键的实体类，不忽略 null 值。
     */
    @Test
    public void testInsertWithPk() {
        User user = new User()
                .setName("许大仙")
                .setPassword("123456")
                .setAge(18)
                .setPhone("1234567890")
                .setEmail("Tom@qq.com");
        LocalDateTime now = LocalDateTime.now();
        user.setCreatedTime(now);

        // 自己手动设置主键
        user.setId(RandomIdGenerator.generateId());

        log.info("testInsertWithPk之前，{}", user);
        // 插入带有主键的实体类，不忽略 null 值
        userMapper.insertWithPk(user);
        User userDb = userMapper.selectOneById(user.getId());
        log.info("testInsertWithPk之后，{}", userDb);

        Assertions.assertNotNull(userDb.getId(), "新增完成之后 ID 不能为 null");
        Assertions.assertEquals("许大仙", userDb.getName());
        Assertions.assertEquals("123456", userDb.getPassword());
        Assertions.assertEquals(18, userDb.getAge());
        Assertions.assertNull(userDb.getGender(), "gender 的值应该是 null");
        Assertions.assertNull(userDb.getAddress(), "address 的值应该是 null");
        Assertions.assertEquals("1234567890", userDb.getPhone());
        Assertions.assertEquals("Tom@qq.com", userDb.getEmail());
        Assertions.assertTrue(userDb.getCreatedTime().toLocalDate().isEqual(now.toLocalDate()));
        Assertions.assertNull(userDb.getUsername(), "username 的值应该是 null");
        Assertions.assertNull(userDb.getUpdatedTime(), "updatedTime 的值应该是 null");

        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.select(USER.ALL_COLUMNS)
                .from(USER)
                .where(USER.ID.eq(userDb.getId()));
        List<User> userList = userMapper.selectListByQuery(queryWrapper);
        Assertions.assertNotNull(userList, "没有查询到刚插入的数据");
    }

    /**
     * insertSelectiveWithPk(entity)：插入带有主键的实体类，忽略 null 值。
     */
    @Test
    public void testInsertSelectiveWithPk() {
        User user = new User()
                .setName("许大仙")
                .setPassword("123456")
                .setAge(18)
                .setPhone("1234567890")
                .setEmail("Tom@qq.com");

        LocalDateTime now = LocalDateTime.now();
        user.setCreatedTime(now);

        // 自己手动设置主键
        user.setId(RandomIdGenerator.generateId());

        log.info("testInsertSelectiveWithPk之前，{}", user);
        userMapper.insertSelectiveWithPk(user); // 插入实体类数据，但是忽略 null 的数据，只对有值的内容进行插入
        User userDb = userMapper.selectOneById(user.getId());
        log.info("testInsertSelectiveWithPk之后，{}", userDb);

        Assertions.assertNotNull(user.getId(), "新增完成之后 ID 不能为 null");
        Assertions.assertEquals("许大仙", userDb.getName());
        Assertions.assertEquals("123456", userDb.getPassword());
        Assertions.assertEquals(18, userDb.getAge());
        Assertions.assertEquals("男", userDb.getGender());
        Assertions.assertNull(userDb.getAddress(), "address 的值应该是 null");
        Assertions.assertEquals("1234567890", userDb.getPhone());
        Assertions.assertEquals("Tom@qq.com", userDb.getEmail());
        Assertions.assertTrue(userDb.getCreatedTime().toLocalDate().isEqual(now.toLocalDate()));
        Assertions.assertNull(userDb.getUsername(), "username 的值应该是 null");
        Assertions.assertNotNull(userDb.getUpdatedTime(), "updatedTime 的值不应该是 null");

        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.select(USER.ALL_COLUMNS)
                .from(USER)
                .where(USER.ID.eq(userDb.getId()));
        List<User> userList = userMapper.selectListByQuery(queryWrapper);
        Assertions.assertNotNull(userList, "没有查询到刚插入的数据");
    }

    /**
     * insertWithPk(entity, ignoreNulls)：带有主键的插入，
     * ignoreNulls = true，表示忽略 null 值
     * 此时实体类不会经过主键生成器生成主键。
     */
    @Test
    public void testInsertWithPk2() {
        User user = new User()
                .setName("许大仙")
                .setPassword("123456")
                .setAge(18)
                .setPhone("1234567890")
                .setEmail("Tom@qq.com");
        LocalDateTime now = LocalDateTime.now();
        user.setCreatedTime(now);

        // 手动设置主键
        user.setId(RandomIdGenerator.generateId());

        log.info("testInsertWithPk2之前，{}", user);
        // 带有主键的插入，此时实体类不会经过主键生成器生成主键，忽略 null 值
        userMapper.insertWithPk(user, true);
        User userDb = userMapper.selectOneById(user.getId());
        log.info("testInsertWithPk2之后，{}", userDb);

        Assertions.assertNotNull(user.getId(), "新增完成之后 ID 不能为 null");
        Assertions.assertEquals("许大仙", userDb.getName());
        Assertions.assertEquals("123456", userDb.getPassword());
        Assertions.assertEquals(18, userDb.getAge());
        Assertions.assertEquals("男", userDb.getGender());
        Assertions.assertNull(userDb.getAddress(), "address 的值应该是 null");
        Assertions.assertEquals("1234567890", userDb.getPhone());
        Assertions.assertEquals("Tom@qq.com", userDb.getEmail());
        Assertions.assertTrue(userDb.getCreatedTime().toLocalDate().isEqual(now.toLocalDate()));
        Assertions.assertNull(userDb.getUsername(), "username 的值应该是 null");
        Assertions.assertNotNull(userDb.getUpdatedTime(), "updatedTime 的值不应该是 null");

        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.select(USER.ALL_COLUMNS)
                .from(USER)
                .where(USER.ID.eq(userDb.getId()));
        List<User> userList = userMapper.selectListByQuery(queryWrapper);
        Assertions.assertNotNull(userList, "没有查询到刚插入的数据");
    }

    /**
     * insertWithPk(entity, ignoreNulls)：带有主键的插入，
     * ignoreNulls = false，表示不忽略 null 值
     * 此时实体类不会经过主键生成器生成主键。
     */
    @Test
    public void testInsertWithPk3() {
        User user = new User()
                .setName("许大仙")
                .setPassword("123456")
                .setAge(18)
                .setPhone("1234567890")
                .setEmail("Tom@qq.com");
        LocalDateTime now = LocalDateTime.now();
        user.setCreatedTime(now);
        // 手动设置主键
        user.setId(RandomIdGenerator.generateId());

        log.info("testInsertWithPk3之前，{}", user);
        // 带有主键的插入，此时实体类不会经过主键生成器生成主键，不忽略 null 值
        userMapper.insertWithPk(user, false);
        User userDb = userMapper.selectOneById(user.getId());
        log.info("testInsertWithPk3之后，{}", userDb);

        Assertions.assertNotNull(userDb.getId(), "新增完成之后 ID 不能为 null");
        Assertions.assertEquals("许大仙", userDb.getName());
        Assertions.assertEquals("123456", userDb.getPassword());
        Assertions.assertEquals(18, userDb.getAge());
        Assertions.assertNull(userDb.getGender(), "gender 的值应该是 null");
        Assertions.assertNull(userDb.getAddress(), "address 的值应该是 null");
        Assertions.assertEquals("1234567890", userDb.getPhone());
        Assertions.assertEquals("Tom@qq.com", userDb.getEmail());
        Assertions.assertTrue(userDb.getCreatedTime().toLocalDate().isEqual(now.toLocalDate()));
        Assertions.assertNull(userDb.getUsername(), "username 的值应该是 null");
        Assertions.assertNull(userDb.getUpdatedTime(), "updatedTime 的值应该是 null");

        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.select(USER.ALL_COLUMNS)
                .from(USER)
                .where(USER.ID.eq(userDb.getId()));
        List<User> userList = userMapper.selectListByQuery(queryWrapper);
        Assertions.assertNotNull(userList, "没有查询到刚插入的数据");
    }

    /**
     * insertBatch(entities)：批量插入实体类数据，只会根据第一条数据来构建插入的字段内容。
     * 相当于多次执行 insert 方法，注意不忽略 null
     */
    @Test
    public void testInsertBatch() {
        List<User> userList = new ArrayList<>();
        userList.add(new User().setName("哈哈" + RandomIdGenerator.generateId()).setAge(20));
        userList.add(new User().setName("嘻嘻" + RandomIdGenerator.generateId()).setEmail("123@qq.com"));
        userList.add(new User().setName("呵呵" + RandomIdGenerator.generateId()).setGender("男"));
        userList.add(new User().setName("哼哼" + RandomIdGenerator.generateId()).setAddress("北京，对吗？"));

        // 批量插入实体类数据，只会根据第一条数据来构建插入的字段内容。
        userMapper.insertBatch(userList);

        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.select(USER.ALL_COLUMNS)
                .from(USER)
                .where(USER.NAME.in(userList.stream().map(User::getName).toList()));
        List<User> userDbList = userMapper.selectListByQuery(queryWrapper);
        Assertions.assertNotNull(userDbList, "没有查询到刚插入的数据");
    }

    /**
     * insertBatch(entities, size)：批量插入实体类数据，按 size 切分。
     */
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
        userMapper.insertBatch(userList, 2); // 其实就是 Math.ceil(7/2) = 4 ，即分 4 次批量插入
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.select(USER.ALL_COLUMNS)
                .from(USER)
                .where(USER.NAME.in(userList.stream().map(User::getName).toList()));
        List<User> userDbList = userMapper.selectListByQuery(queryWrapper);
        Assertions.assertEquals((int) (Math.ceil((double) userList.size() / 2)), 4);
        Assertions.assertNotNull(userDbList, "没有查询到刚插入的数据");
    }

    /**
     * insertOrUpdate(entity)：插入或者更新，若主键有值，则更新，若没有主键值，则插入，插入或者更新都不会忽略 null 值。
     */
    @Test
    public void testInsertOrUpdate() {
        User tom = new User()
                .setName("许大仙")
                .setUsername("Tom")
                .setPassword("123456")
                .setAge(18)
                .setPhone("1234567890")
                .setEmail("Tom@qq.com");
        // 手动设置主键
        int id = RandomIdGenerator.generateId();
        tom.setId(id);

        // 插入或者更新，若主键有值，则更新，若没有主键值，则插入，插入或者更新都不会忽略 null 值。
        userMapper.insertOrUpdate(tom);
        Assertions.assertNull(userMapper.selectOneById(id), "更新失败");

        tom.setId(null);
        // 插入或者更新，若主键有值，则更新，若没有主键值，则插入，插入或者更新都不会忽略 null 值。
        userMapper.insertOrUpdate(tom);
        Assertions.assertNotNull(userMapper.selectOneById(tom.getId()), "新增失败");
        Assertions.assertNull(userMapper.selectOneById(tom.getId()).getGender(), "gender 应该没有值，为 null");
    }


    /**
     * insertOrUpdateSelective(entity)：插入或者更新，若主键有值，则更新，若没有主键值，则插入，插入或者更新都会忽略 null 值。
     */
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

    /**
     * insertOrUpdate(entity, ignoreNulls)：插入或者更新，若主键有值，则更新，若没有主键值，则插入。
     */
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
