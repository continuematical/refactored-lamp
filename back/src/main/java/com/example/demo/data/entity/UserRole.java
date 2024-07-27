package com.example.demo.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.basics.baseclass.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


@Data
@Accessors(chain = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_user_role")
@TableName("a_user_role")
@Schema(name = "用户角色")
public class UserRole extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Schema(name = "角色ID")
    private String roleId;

    @Schema(name = "用户ID")
    private String userId;

    @Transient
    @TableField(exist=false)
    @Schema(name = "用户名")
    private String userName;

    @Transient
    @TableField(exist=false)
    @Schema(name = "角色名")
    private String roleName;
}
