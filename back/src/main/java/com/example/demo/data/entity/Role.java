package com.example.demo.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.basics.baseclass.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "角色")
public class Role extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Schema(name = "角色名称")
    private String name;

    @Schema(name = "数据权限")
    private int dataType;

    @Schema(name = "是否默认")
    private Boolean defaultRole;

    @Schema(name = "角色备注")
    private String description;

    @Transient
    @TableField(exist=false)
    @Schema(name = "角色拥有菜单列表")
    private List<RolePermission> permissions;
}
