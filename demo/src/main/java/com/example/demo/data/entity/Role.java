package com.example.demo.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.basics.baseclass.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_role")
@TableName("a_role")
@ApiModel(value = "角色")
public class Role extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "数据权限")
    private int dataType;

    @ApiModelProperty(value = "是否默认")
    private Boolean defaultRole;

    @ApiModelProperty(value = "角色备注")
    private String description;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "角色拥有菜单列表")
    private List<RolePermission> permissions;
}