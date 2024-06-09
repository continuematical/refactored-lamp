package com.example.demo.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.basics.baseclass.BaseEntity;
import com.example.demo.basics.parameter.CommonConstant;
import com.example.demo.data.vo.PermissionDTO;
import com.example.demo.data.vo.RoleDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Data
@Accessors(chain = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_user")
@TableName("a_user")
@Schema(name = "用户")
public class User extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Schema(name = "姓名")
    @NotNull
    @Size(max = 20, message = "姓名不能为空")
    private String nickname;

    @Schema(name = "账号")
    @Column(unique = true, nullable = false)
    @Pattern(regexp = "^[a-zA-Z0-9_\\u4e00-\\u9fa5]{4,16}$", message = "账号长度不合法")
    private String username;

    @Schema(name = "密码")
    @NotNull(message = "密码不能为空")
    private String password;

    @Schema(name = "密码强度")
    @Column(length = 2)
    private String passStrength;

    @Schema(name = "手机号")
    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式错误")
    private String mobile;

    @Schema(name = "部门Id")
    private String departmentId;

    @Schema(name = "部门")
    private String departmentTitle;

    @Schema(name = "邮箱")
    @Pattern(regexp = "^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"
            , message = "邮箱格式错误")
    private String email;

    @Schema(name = "性别")
    private String sex;

    @Schema(name = "区县")
    private String address;

    @Schema(name = "用户类型")
    private Integer type;

    @Schema(name = "个人门户")
    private String myDoor;

    @Schema(name = "启用状态")
    private Integer status = CommonConstant.USER_STATUS_NORMAL;

    @Schema(name = "头像")
    private String avatar = CommonConstant.USER_DEFAULT_AVATAR;

    @Transient
    @TableField(exist=false)
    @Schema(name = "是否默认角色")
    private Integer defaultRole;

    @Transient
    @TableField(exist=false)
    @Schema(name = "用户拥有的菜单列表")
    private List<PermissionDTO> permissions;

    @Transient
    @TableField(exist=false)
    @Schema(name = "用户拥有的角色列表")
    private List<RoleDTO> roles;
}
