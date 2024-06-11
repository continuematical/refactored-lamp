package com.example.demo.basics.security;

import com.example.demo.basics.parameter.CommonConstant;
import com.example.demo.data.entity.User;
import com.example.demo.data.utils.NullUtils;
import com.example.demo.data.vo.PermissionDTO;
import com.example.demo.data.vo.RoleDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Tag(name = "查询用户的角色和菜单权限")
public class SecurityUserDetails extends User implements UserDetails {
    private static final long serialVersionUID = 1L;

    private List<RoleDTO> roles;

    private List<PermissionDTO> permissions;

    @Override
    @Operation(summary = "查询用户的角色和菜单权限")
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        /**
         * 菜单权限
         */
        if (permissions != null && permissions.size() > 0) {
            for (PermissionDTO permission : permissions) {
                if (!NullUtils.isNull(permission.getTitle()) && !NullUtils.isNull(permission.getPath())) {
                    grantedAuthorities.add(new SimpleGrantedAuthority(permission.getTitle()));
                }
            }
        }
        /**
         * 角色
         */
        if (roles != null && roles.size() > 0) {
            roles.forEach(role -> {
                if (!NullUtils.isNull(role.getName())) {
                    grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
                }
            });
        }
        return grantedAuthorities;
    }

    @Override
    @Operation(summary = "账号是否过期")
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Operation(summary = "账号是否禁用")
    public boolean isAccountNonLocked() {
        return !Objects.equals(CommonConstant.USER_STATUS_NORMAL, this.getStatus());
    }

    @Override
    @Operation(summary = "账号密码是否过期")
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Operation(summary = "账号是否启用")
    public boolean isEnabled() {
        return Objects.equals(CommonConstant.USER_STATUS_NORMAL, this.getStatus());
    }

    /**
     * 自定义类构造器
     */
    public SecurityUserDetails(User user) {
        if (user != null) {
            this.setUsername(user.getUsername());
            this.setPassword(user.getPassword());
            this.setEmail(user.getEmail());
            this.permissions = user.getPermissions();
            this.roles = user.getRoles();
        }
    }
}
