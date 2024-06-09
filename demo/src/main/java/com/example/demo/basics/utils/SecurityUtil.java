package com.example.demo.basics.utils;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.basics.baseVo.TokenUser;
import com.example.demo.basics.exceptions.MyException;
import com.example.demo.basics.parameter.LoginProperties;
import com.example.demo.basics.redis.RedisTemplateHelper;
import com.example.demo.data.entity.Permission;
import com.example.demo.data.entity.Role;
import com.example.demo.data.entity.User;
import com.example.demo.data.service.IPermissionService;
import com.example.demo.data.service.IRoleService;
import com.example.demo.data.service.IUserService;
import com.example.demo.data.utils.NullUtils;
import com.example.demo.data.vo.PermissionDTO;
import com.example.demo.data.vo.RoleDTO;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@ApiOperation(value = "鉴权工具类")
@Component
public class SecurityUtil {

    @Autowired
    private LoginProperties tokenProperties;

    @Autowired
    private RedisTemplateHelper redisTemplateHelper;

    private IUserService iUserService;

    private IPermissionService iPermissionService;

    private IRoleService iRoleService;

    private static final String TOKEN_REPLACE_FRONT_STR = "-";

    private static final String TOKEN_REPLACE_BACK_STR = "";

    private User selectByUsrName(String username) {
        QueryWrapper<User> userQw = new QueryWrapper<>();
        userQw.eq("username", username);
        User user = iUserService.getOne(userQw);

        if (user == null) {
            return null;
        }

        /**
         *  填充角色
         */
        QueryWrapper<Role> roleQw = new QueryWrapper<>();
        roleQw.inSql("id", "SELECT role_id FROM a_role_permission WHERE del_flag = 0 AND permission_id = '" + user.getId() + "'");
        List<Role> roles = iRoleService.list(roleQw); //可以删除User的角色
        List<RoleDTO> roleDTOS = new ArrayList<>();

        for (Role role : roles) {
            roleDTOS.add(new RoleDTO(role.getName(), role.getId(), role.getDescription());
        }
        user.setRoles(roleDTOS);

        /**
         * 填充菜单
         */
        QueryWrapper<Permission> menuQw = new QueryWrapper<>();
        menuQw.eq("id", "SELECT role_id FROM a_permission WHERE del_flag = 0 AND permission_id = '" + user.getId() + "'");
        List<Permission> permissions = iPermissionService.list(menuQw);
        List<PermissionDTO> permissionDTOS = new ArrayList<>();

        for (Permission permission : permissions) {
            permissionDTOS.add(new PermissionDTO(permission.getPath(), permission.getTitle()));
        }
        user.setPermissions(permissionDTOS);
        return user;
    }

    @ApiOperation(value = "获取新的用户token")
    public String getToken(String username, Boolean saveLogin) {
        if (NullUtils.isNull(username)) {
            throw new MyException("username不能为空");
        }
        boolean saved = false;
        if (saveLogin == null || saveLogin) {
            saved = true;
        }
        User selectUser = selectByUsrName(username);
        /**
         * 菜单和角色的数组
         */
        List<String> permissionTitleList = new ArrayList<>();
        if (tokenProperties.getSaveRoleFlag()) {
            for (PermissionDTO p : selectUser.getPermissions()) {
                if (!NullUtils.isNull(p.getTitle()) && !NullUtils.isNull(p.getPath())) {
                    permissionTitleList.add(p.getTitle());
                }
            }
            for (RoleDTO r : selectUser.getRoles()) {
                permissionTitleList.add(r.getName());
            }
        }
        String ansUserToken = UUID.randomUUID().toString().replace(TOKEN_REPLACE_FRONT_STR, TOKEN_REPLACE_BACK_STR);
        TokenUser tokenUser = new TokenUser(selectUser.getUsername(), permissionTitleList, saved)
        /**
         * 单点登录删除旧Token
         */
        if (tokenProperties.getSsoFlag()) {
            String oldToken = redisTemplateHelper.get(LoginProperties.USER_TOKEN_PRE + selectUser.getUsername());
            if (NullUtils.isNull(oldToken)) {
                redisTemplateHelper.delete(LoginProperties.HTTP_HEADER + oldToken);
            }
        }
        /**
         * 保存至redis设备
         */
        if (saved) {
            redisTemplateHelper.set(LoginProperties.USER_TOKEN_PRE + selectUser.getUsername(), ansUserToken, tokenProperties.getUserSaveLoginTokenDays(), TimeUnit.DAYS);
            redisTemplateHelper.set(LoginProperties.HTTP_TOKEN_PRE + ansUserToken, JSON.toJSONString(tokenUser), tokenProperties.getUserSaveLoginTokenDays(), TimeUnit.DAYS);
        } else {
            redisTemplateHelper.set(LoginProperties.USER_TOKEN_PRE + selectUser.getUsername(), ansUserToken, tokenProperties.getUserTokenInvalidDays(), TimeUnit.MINUTES);
            redisTemplateHelper.set(LoginProperties.HTTP_TOKEN_PRE + ansUserToken, JSON.toJSONString(tokenUser), tokenProperties.getUserTokenInvalidDays(), TimeUnit.MINUTES);
        }
        return ansUserToken;
    }

    @ApiOperation(value = "查询指定的用户的权限列表")
    public List<GrantedAuthority> getCurrUserPerms(String username) {
        List<GrantedAuthority> ans = new ArrayList<>();
        User selectUser = selectByUsrName(username);
        if (selectUser == null) {
            return ans;
        }
        List<PermissionDTO> perList = selectUser.getPermissions();
        if (perList.size() < 1) {
            return ans;
        }
        for (PermissionDTO vo : perList) {
            ans.add(new SimpleGrantedAuthority(vo.getTitle()));
        }
        return ans;
    }

    @ApiModelProperty(value = "查询当前Token的用户对象")
    public User getCurrUser() {
        Object selectUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (Objects.equals("anonymousUser", selectUser.toString())) {
            throw new MyException("登陆失效");
        }
        UserDetails user = (UserDetails) selectUser;
        return selectByUsrName(user.getUsername());
    }
}
