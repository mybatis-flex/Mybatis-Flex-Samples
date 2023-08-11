package com.mybatisflex.domain;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.domain.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Table(value = "role")
@EqualsAndHashCode(callSuper = false)
public class Role extends BaseEntity {

    /**
     * 权限名称
     */
    @Column
    private String name;

    /**
     * 权限描述
     */
    private String description;
}
