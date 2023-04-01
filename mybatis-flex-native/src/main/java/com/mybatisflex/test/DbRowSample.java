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

import com.mybatisflex.annotation.Id;
import com.mybatisflex.core.MybatisFlexBootstrap;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.row.Row;
import com.mybatisflex.core.row.RowKey;
import com.mybatisflex.core.row.RowMapper;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.Date;

public class DbRowSample {

    public static void main(String[] args) {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();

        MybatisFlexBootstrap.getInstance()
                .setDataSource(dataSource)
//                .setLogImpl(StdOutImpl.class) //日志输出配置
                .start();

        //查询 ID 为 1 的数据
        Row row = Db.selectOneById("tb_account", "id", 1);
        System.out.println(row);


        //新增一条数据，自增
        Row newRow = Row.ofKey(RowKey.ID_AUTO) // id 自增
                .set("user_name", "lisi")
                .set("age", 22)
                .set("birthday", new Date());

        Db.insert("tb_account", newRow);
        System.out.println("newRow.id >>>> " + newRow.get("id"));


        //查询所有数据
        System.out.println(Db.selectAll("tb_account"));

    }
}
