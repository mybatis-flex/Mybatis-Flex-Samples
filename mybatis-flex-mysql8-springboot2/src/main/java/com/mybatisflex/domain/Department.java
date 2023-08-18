package com.mybatisflex.domain;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.RelationOneToMany;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.domain.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@Table(value = "department")
@EqualsAndHashCode(callSuper = false)
public class Department extends BaseEntity {

    /**
     * 部门名称
     */
    @Column
    private String name;

    /**
     * 部门描述
     */
    private String description;


    /**
     * selfField 当前实体类的属性
     * targetField 目标对象的关系实体类的属性
     */
    @RelationOneToMany(selfField = "id", targetField = "id")
    private List<User> userList = new ArrayList<>();

}
