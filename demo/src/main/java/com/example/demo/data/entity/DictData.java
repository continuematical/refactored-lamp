package com.example.demo.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.basics.baseclass.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import io.swagger.v3.oas.annotations.media.Schema;


@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_dict_data")
@TableName("a_dict_data")
@Schema(name = "数据字典值")
public class DictData extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Schema(name = "数据字典ID")
    private String dictId;

    @Transient
    @TableField(exist=false)
    @Schema(name = "数据字典名称")
    private String dictName;

    @Schema(name = "数据字典键")
    private String title;

    @Schema(name = "数据字典值排序值")
    @Column(precision = 10, scale = 2)
    private BigDecimal sortOrder;

    @Schema(name = "数据字典值")
    private String value;

    @Schema(name = "数据字典值备注")
    private String description;

    @Schema(name = "是否启用")
    private Integer status = 0;
}
