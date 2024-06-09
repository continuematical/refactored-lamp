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
@Table(name = "a_permission")
@TableName("a_permission")
@Schema(name = "菜单权限")
public class Permission extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Schema(name = "菜单名称")
    private String name;

    @Schema(name = "菜单标题")
    private String title;

    @Schema(name = "菜单层级")
    private Integer level;

    @Schema(name = "启用状态")
    private Integer status = 0;

    @Schema(name = "菜单按钮类型")
    private Integer type;

    @Schema(name = "前端组件名称")
    private String component;

    @Schema(name = "页面路径")
    private String path;

    @Schema(name = "PC端图标")
    private String icon;

    @Schema(name = "父节点ID")
    private String parentId;

    @Schema(name = "按钮类型")
    private String buttonType;

    @Schema(name = "备注")
    private String description;

    @Schema(name = "菜单排序值")
    @Column(precision = 10, scale = 2)
    private BigDecimal sortOrder;

    @Transient
    @TableField(exist=false)
    @Schema(name = "节点展开状态")
    private Boolean expand = true;

    @Transient
    @TableField(exist=false)
    @Schema(name = "结点选中状态")
    private Boolean selected = false;

    @Transient
    @TableField(exist=false)
    @Schema(name = "结点勾选状态")
    private Boolean checked = false;

    @Transient
    @TableField(exist=false)
    @Schema(name = "子菜单列表")
    private List<Permission> children;

    @Transient
    @TableField(exist=false)
    @Schema(name = "菜单子权限列表")
    private List<String> permTypes;
}
