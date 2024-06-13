package com.example.demo.basics.security;

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

@Tag(name = "查询用户的角色和菜单权限")
public class SecurityUserDetails extends User implements UserDetails {

    private static final long serialVersionUID = 1L;

    private List<RoleDTO> roles;

    private List<PermissionDTO> permissions;

    @Override
    @Operation(summary = "查询用户的角色和菜单权限")
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        // 菜单权限
        if(permissions!=null && permissions.size() > 0){
            for (PermissionDTO dto : permissions) {
                if(!NullUtils.isNull(dto.getTitle()) && !NullUtils.isNull(dto.getPath())) {
                    grantedAuthorityList.add(new SimpleGrantedAuthority(dto.getTitle()));
                }
            }
        }
        // 角色
        if(roles != null && roles.size() > 0){
            roles.forEach(role -> {
                if(!NullUtils.isNull(role.getName())){
                    grantedAuthorityList.add(new SimpleGrantedAuthority(role.getName()));
                }
            });
        }
        return grantedAuthorityList;
    }

    @Override
    @Operation(summary = "账号是否启用")
    public boolean isEnabled() {
        return true;
                //Objects.equals(CommonConstant.USER_STATUS_NORMAL,this.getStatus());
    }

    @Operation(summary = "账号是否过期")
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Operation(summary = "账号密码是否过期")
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Operation(summary = "账号是否禁用")
    public boolean isAccountNonLocked() {
        return true;
        //!Objects.equals(CommonConstant.USER_STATUS_LOCK, this.getStatus());
    }

    /**
     * 自定义类构造器
     * @param user 系统账户
     */
    public SecurityUserDetails(User user) {
        if(user != null) {
            this.setUsername(user.getUsername());
            this.setPassword(user.getPassword());
            this.setStatus(user.getStatus());
            this.permissions  = user.getPermissions();
            this.roles = user.getRoles();
        }
    }
}