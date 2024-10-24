package com.example.demo.data.controller;

import com.example.demo.basics.baseVo.Result;
import com.example.demo.basics.log.LogType;
import com.example.demo.basics.log.SystemLog;
import com.example.demo.basics.utils.ResultUtil;
import com.example.demo.basics.utils.SecurityUtil;
import com.example.demo.data.entity.Permission;
import com.example.demo.data.entity.User;
import com.example.demo.data.service.IPermissionService;
import com.example.demo.data.service.IUserService;
import com.example.demo.data.utils.NullUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/zhou/myDoor")
@Tag(name = "我的门户管理接口")
@Transactional
public class MyDoorController {
    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private IPermissionService iPermissionService;

    @Autowired
    private IUserService iUserService;

    @SystemLog(about = "查询个人门户菜单A", type = LogType.DATA_CENTER, dotype = "MY-DOOR-01")
    @Operation(summary = "查询个人门户菜单A")
    @RequestMapping(value = "/getMyDoorList", method = RequestMethod.GET)
    public Result<List<MyDoorMenuClass>> getMyDoorList() {
        User user = securityUtil.getCurrUser();
        user = iUserService.getById(user.getId());
        List<MyDoorMenuClass> ans = new ArrayList<>();
        String myDoor = user.getMyDoor();
        if(NullUtils.isNull(myDoor)) {
            return new ResultUtil().setData(ans);
        }
        String[] zwz666s = myDoor.split("ZWZ666");
        List<Permission> all = iPermissionService.list();
        for (String zwz666 : zwz666s) {
            for (Permission permission : all) {
                if(Objects.equals(permission.getName(),zwz666)) {
                    MyDoorMenuClass menu = new MyDoorMenuClass();
                    menu.setName(permission.getName());
                    menu.setTitle(permission.getTitle());
                    ans.add(menu);
                    break;
                }
            }
        }
        return new ResultUtil().setData(ans);
    }

    @SystemLog(about = "查询个人门户菜单B", type = LogType.DATA_CENTER, dotype = "MY-DOOR-02")
    @Operation(summary = "查询个人门户菜单B")
    @RequestMapping(value = "getMyDoorList6",method = RequestMethod.GET)
    public Result<List<MyDoorMenuClass>> getMyDoorList6() {
        User user = securityUtil.getCurrUser();
        user = iUserService.getById(user.getId());
        List<MyDoorMenuClass> ans = new ArrayList<>();
        String myDoor = user.getMyDoor();
        if(NullUtils.isNull(myDoor)) {
            ans.add(getNullMenu());ans.add(getNullMenu());ans.add(getNullMenu());
            ans.add(getNullMenu());ans.add(getNullMenu());ans.add(getNullMenu());
            return new ResultUtil().setData(ans);
        }
        String[] zwz666s = myDoor.split("ZWZ666");
        List<Permission> all = iPermissionService.list();
        for (String zwz666 : zwz666s) {
            for (Permission permission : all) {
                if(Objects.equals(permission.getName(),zwz666)) {
                    MyDoorMenuClass menu = new MyDoorMenuClass();
                    menu.setName(permission.getName());
                    menu.setTitle(permission.getTitle());
                    ans.add(menu);
                    break;
                }
            }
        }
        int size = ans.size();
        if(size < 6) {
            int time = 6 - size;
            for(int i = 0 ; i < time; i ++) {
                ans.add(getNullMenu());
            }
        }
        return new ResultUtil().setData(ans);
    }

    @SystemLog(about = "修改个人门户菜单",type = LogType.DATA_CENTER,dotype = "MY-DOOR-03")
    @Operation(summary = "修改个人门户菜单")
    @RequestMapping(value = "/setMyDoorList",method = RequestMethod.GET)
    public Result<Object> setMyDoorList(@RequestParam String str){
        User user = securityUtil.getCurrUser();
        user = iUserService.getById(user.getId());
        if(user != null) {
            if(NullUtils.isNull(str)) {
                user.setMyDoor("");
                iUserService.saveOrUpdate(user);
            } else {
                user.setMyDoor(str);
                iUserService.saveOrUpdate(user);
            }
            return ResultUtil.success("OK");
        }
        return ResultUtil.error("ROSTER IS NULL");
    }

    @Data
    private static class MyDoorMenuClass {
        private String name;
        private String title;
    }

    private MyDoorMenuClass getNullMenu() {
        MyDoorMenuClass menu = new MyDoorMenuClass();
        menu.setName("null");
        menu.setTitle("尚未添加");
        return menu;
    }
}
