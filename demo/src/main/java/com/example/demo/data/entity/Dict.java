package com.example.demo.data.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.basics.baseclass.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_dict")
@TableName("a_dict")
@Schema(name = "数据字典")
public class Dict extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Schema(name = "数据字典标题")
    private String title;

    @Schema(name = "数据字典排序值")
    @Column(precision = 10, scale = 2)
    private BigDecimal sortOrder;

    @Schema(name = "数据字典备注")
    private String description;

    @Schema(name = "数据字典类型")
    private String type;
}
