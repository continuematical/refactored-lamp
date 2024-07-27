package com.example.demo.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.basics.baseclass.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_department")
@TableName("a_department")
@Schema(name = "部门")
public class Department extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Schema(name = "部门标题")
    private String title;

    @Schema(name = "状态")
    private Integer status;

    @Schema(name = "排序值")
    @Column(precision = 10, scale = 2)
    private BigDecimal sortOrder;

    @Schema(name = "父部门ID")
    private String parentId;

    @Schema(name = "父节点标识")
    private Boolean isParent = false;

    @Transient
    @TableField(exist = false)
    @Schema(name = "领导人")
    private List<String> mainHeader;

    @Transient
    @TableField(exist = false)
    @Schema(name = "副领导人")
    private List<String> viceHeader;

    @Transient
    @TableField(exist = false)
    @Schema(name = "父部门名称")
    private String parentTitle;
}
