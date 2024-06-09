package com.example.demo.data.controller;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.basics.baseVo.Result;
import com.example.demo.basics.log.LogType;
import com.example.demo.basics.log.SystemLog;
import com.example.demo.basics.parameter.CommonConstant;
import com.example.demo.basics.redis.RedisTemplateHelper;
import com.example.demo.basics.utils.ResultUtil;
import com.example.demo.basics.utils.SecurityUtil;
import com.example.demo.data.entity.Department;
import com.example.demo.data.entity.DepartmentHeader;
import com.example.demo.data.entity.User;
import com.example.demo.data.service.IDepartmentHeaderService;
import com.example.demo.data.service.IDepartmentService;
import com.example.demo.data.service.IUserService;
import com.example.demo.data.utils.NullUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RestController
@Tag(name = "部门管理接口")
@RequestMapping("/department")
public class DepartmentController {
    private SecurityUtil securityUtil;

    private RedisTemplateHelper redisTemplateHelper;

    private IDepartmentService iDepartmentService;

    private IUserService iUserService;

    private IDepartmentHeaderService iDepartmentHeaderService;

    //常量
    private static final String ONE_LEVEL_PARENT_TITLE = "一级部门";

    private static final String REDIS_DEPARTMENT_PRE_STR = "department::";

    private static final String REDIS_STEP_STR = ":";

    @Operation(summary = "增加一级部门标识")
    public List<Department> setInfo(List<Department> list) {
        list.forEach(item -> {
            if (!Objects.equals(CommonConstant.PARENT_ID, item.getParentId())) {
                Department parentDepartment = iDepartmentService.getById(item.getParentId());
                if (parentDepartment == null) {
                    item.setParentTitle("无");
                } else {
                    item.setParentTitle(parentDepartment.getParentTitle());
                }
            } else {
                item.setParentTitle(ONE_LEVEL_PARENT_TITLE);
            }
            /**
             * 部门负责人
             */
            QueryWrapper<DepartmentHeader> dh1 = new QueryWrapper<>();
            dh1.eq("department_id", item.getId());
            dh1.eq("type", 0);
            List<DepartmentHeader> headerList1 = iDepartmentHeaderService.list(dh1);
            List<String> mainHeaderList = new ArrayList<>();
            for (DepartmentHeader dh : headerList1) {
                mainHeaderList.add(dh.getUserId());
            }
            item.setMainHeader(mainHeaderList);

            QueryWrapper<DepartmentHeader> dh2 = new QueryWrapper<>();
            dh2.eq("department_id", item.getId());
            dh2.eq("type", 1);
            List<DepartmentHeader> headerList2 = iDepartmentHeaderService.list(dh2);
            List<String> viceHeaderList = new ArrayList<>();
            for (DepartmentHeader dh : headerList2) {
                viceHeaderList.add(dh.getUserId());
            }
            item.setViceHeader(viceHeaderList);
        });
        return list;
    }

    @Operation(summary = "添加模拟搜索标志")
    private String addLikeStr(String str) {
        return "%" + str + "%";
    }

    @RequestMapping("/getByParentId")
    @SystemLog(about = "查询子部门", type = LogType.DATA_CENTER, dotype = "DEP-01")
    @Operation(summary = "查询子部门")
    public Result<List<Department>> getByParentId(@RequestParam String parentId) {
        List<Department> list = null;
        User nowUser = securityUtil.getCurrUser();
        String key = REDIS_DEPARTMENT_PRE_STR + parentId + REDIS_STEP_STR + nowUser.getId();
        String value = redisTemplateHelper.get(key);
        if (!NullUtils.isNull(value)) {
            return new ResultUtil<List<Department>>().setData(JSON.parseArray(value, Department.class));
        }
        QueryWrapper<Department> depQw = new QueryWrapper<>();
        depQw.eq("parent_id", parentId);
        depQw.orderByAsc("sort_order");
        list = iDepartmentService.list(depQw);
        list = setInfo(list);
        redisTemplateHelper.set(key, JSON.toJSONString(list), 15L, TimeUnit.DAYS);
        return new ResultUtil<List<Department>>().setData(list);
    }

//    @RequestMapping("/search")
//    @SystemLog(about = "查询模糊部门", type = LogType.DATA_CENTER, dotype = "DEP-02")
//    @Tag(name = "查询模糊部门")
//    public Result<List<Department>> search(@RequestParam String title) {
//
//    }
}
