package com.mybatisflex.domain.base;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import lombok.Data;
import org.apache.ibatis.type.LocalDateTimeTypeHandler;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
public class BaseEntity implements Serializable {
    /**
     * 主键
     */
    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 创建时间
     */
    @Column(typeHandler = LocalDateTimeTypeHandler.class)
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    @Column(typeHandler = LocalDateTimeTypeHandler.class)
    private LocalDateTime updatedTime;

}
