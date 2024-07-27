package com.example.demo.data.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.basics.baseVo.PageVo;
import com.example.demo.basics.baseVo.Result;
import com.example.demo.basics.log.LogType;
import com.example.demo.basics.log.SystemLog;
import com.example.demo.basics.redis.RedisTemplateHelper;
import com.example.demo.basics.utils.PageUtil;
import com.example.demo.basics.utils.ResultUtil;
import com.example.demo.data.entity.Role;
import com.example.demo.data.entity.RolePermission;
import com.example.demo.data.entity.UserRole;
import com.example.demo.data.service.IRolePermissionService;
import com.example.demo.data.service.IRoleService;
import com.example.demo.data.service.IUserRoleService;
import com.example.demo.data.utils.NullUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@Tag(name = "角色管理接口")
@RequestMapping("/zhou/role")
@Transactional
public class RoleController {
    @Autowired
    private IRoleService iRoleService;

    @Autowired
    private IUserRoleService iUserRoleService;

    @Autowired
    private IRolePermissionService iRolePermissionService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RedisTemplateHelper redisTemplateHelper;

    @SystemLog(about = "查询全部角色", type = LogType.DATA_CENTER,dotype = "ROLE-01")
    @RequestMapping(value = "/getAllList", method = RequestMethod.GET)
    @Operation(summary = "查询全部角色")
    public Result<Object> getAllList(){
        return ResultUtil.data(iRoleService.list());
    }

    @SystemLog(about = "查询角色", type = LogType.DATA_CENTER,dotype = "ROLE-02")
    @RequestMapping(value = "/getAllByPage", method = RequestMethod.GET)
    @Operation(summary = "查询角色")
    public Result<IPage<Role>> getRoleByPage(@ModelAttribute Role role, @ModelAttribute PageVo page) {
        QueryWrapper<Role> qw = new QueryWrapper<>();
        if(!NullUtils.isNull(role.getName())) {
            qw.like("name",role.getName());
        }
        if(!NullUtils.isNull(role.getDescription())) {
            qw.like("description",role.getDescription());
        }
        IPage<Role> roleList = iRoleService.page(PageUtil.initMyPage(page),qw);
        for(Role r : roleList.getRecords()){
            QueryWrapper<RolePermission> rpQw = new QueryWrapper<>();
            rpQw.eq("role_id",r.getId());
            r.setPermissions(iRolePermissionService.list(rpQw));
        }
        return new ResultUtil<IPage<Role>>().setData(roleList);
    }

    @SystemLog(about = "配置默认角色", type = LogType.DATA_CENTER,dotype = "ROLE-03")
    @RequestMapping(value = "/setDefault", method = RequestMethod.POST)
    @Operation(summary = "配置默认角色")
    public Result<Object> setDefault(@RequestParam String id, @RequestParam Boolean isDefault){
        Role role = iRoleService.getById(id);
        if(role != null) {
            if(!Objects.equals(role.getDefaultRole(),isDefault)) {
                role.setDefaultRole(isDefault);
                iRoleService.saveOrUpdate(role);
            }
            return ResultUtil.success();
        }
        return ResultUtil.error("不存在");
    }

    @SystemLog(about = "修改菜单权限", type = LogType.DATA_CENTER,dotype = "ROLE-04")
    @RequestMapping(value = "/editRolePerm", method = RequestMethod.POST)
    @Operation(summary = "修改菜单权限")
    public Result<Object> editRolePerm(@RequestParam String roleId,@RequestParam(required = false) String[] permIds){
        Role role = iRoleService.getById(roleId);
        if(role == null) {
            return ResultUtil.error("角色已被删除");
        }
        if(permIds == null) {
            permIds = new String[0];
        }
        QueryWrapper<RolePermission> oldQw = new QueryWrapper<>();
        oldQw.eq("role_id",role.getId());
        List<RolePermission> oldPermissionList = iRolePermissionService.list(oldQw);
        // 判断新增 = oldPermissionList没有 permIds有
        for (String permId : permIds) {
            boolean flag = true;
            for (RolePermission rp : oldPermissionList) {
                if(Objects.equals(permId,rp.getPermissionId())) {
                    flag = false;
                    break;
                }
            }
            if(flag) {
                RolePermission rp = new RolePermission();
                rp.setRoleId(role.getId());
                rp.setPermissionId(permId);
                iRolePermissionService.saveOrUpdate(rp);
            }
        }
        // 判断删除 = oldPermissionList有 permIds没有
        for (RolePermission rp : oldPermissionList) {
            boolean flag = true;
            for (String permId : permIds) {
                if(Objects.equals(permId,rp.getPermissionId())) {
                    flag = false;
                    break;
                }
            }
            if(flag) {
                iRolePermissionService.removeById(rp.getId());
            }
        }
        Set<String> keysUser = redisTemplateHelper.keys("user:" + "*");
        redisTemplate.delete(keysUser);
        Set<String> keysUserRole = redisTemplateHelper.keys("userRole:" + "*");
        redisTemplate.delete(keysUserRole);
        Set<String> keysUserMenu = redisTemplateHelper.keys("permission::userMenuList:*");
        redisTemplate.delete(keysUserMenu);
        return ResultUtil.data();
    }

    @SystemLog(about = "新增角色", type = LogType.DATA_CENTER,dotype = "ROLE-05")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @Operation(summary = "新增角色")
    public Result<Role> save(Role role){
        iRoleService.saveOrUpdate(role);
        return new ResultUtil<Role>().setData(role);
    }

    @SystemLog(about = "编辑角色", type = LogType.DATA_CENTER,dotype = "ROLE-06")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @Operation(summary = "编辑角色")
    public Result<Role> edit(Role role){
        iRoleService.saveOrUpdate(role);
        Set<String> keysUser = redisTemplateHelper.keys("user:" + "*");
        redisTemplate.delete(keysUser);
        Set<String> keysUserRole = redisTemplateHelper.keys("userRole:" + "*");
        redisTemplate.delete(keysUserRole);
        return new ResultUtil<Role>().setData(role);
    }

    @SystemLog(about = "删除角色", type = LogType.DATA_CENTER,dotype = "ROLE-07")
    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @Operation(summary = "删除角色")
    public Result<Object> delByIds(@RequestParam String[] ids){
        for(String id : ids) {
            QueryWrapper<UserRole> urQw = new QueryWrapper<>();
            urQw.eq("role_id", id);
            long urCount = iUserRoleService.count(urQw);
            if(urCount > 0L){
                return ResultUtil.error("不能删除正在使用的角色");
            }
        }
        for(String id:ids){
            iRoleService.removeById(id);
            QueryWrapper<RolePermission> rpQw = new QueryWrapper<>();
            rpQw.eq("role_id",id);
            iRolePermissionService.remove(rpQw);
        }
        return ResultUtil.success();
    }
}
