/**
 * Copyright (c) 2022-2023, Mybatis-Flex (fuhai999@gmail.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mybatisflex.test;

import com.mybatisflex.core.MybatisFlexBootstrap;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.Date;

public class AccountSample {

    public static void main(String[] args) {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();

        MybatisFlexBootstrap bootstrap = MybatisFlexBootstrap.getInstance()
                .setDataSource(dataSource)
//                .setLogImpl(StdOutImpl.class)
                .addMapper(AccountMapper.class)
                .start();


        AccountMapper accountMapper = bootstrap.getMapper(AccountMapper.class);

        //查询 ID 为 1 的数据
        Account account = accountMapper.selectOneById(1);
        System.out.println(account);


        //新增 1 条数据
        Account newAccount = new Account();
        newAccount.setUserName("lisi");
        newAccount.setAge(18);
        newAccount.setBirthday(new Date());
        accountMapper.insert(newAccount);
        System.out.println("newAccount Id: " + newAccount.getId());


        //查询全部数据
        System.out.println(accountMapper.selectAll());


    }
}
