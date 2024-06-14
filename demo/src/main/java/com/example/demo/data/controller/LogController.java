package com.example.demo.data.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.basics.baseVo.PageVo;
import com.example.demo.basics.baseVo.Result;
import com.example.demo.basics.log.LogType;
import com.example.demo.basics.log.SystemLog;
import com.example.demo.basics.utils.PageUtil;
import com.example.demo.basics.utils.ResultUtil;
import com.example.demo.data.entity.Log;
import com.example.demo.data.service.ILogService;
import com.example.demo.data.utils.NullUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/zhou/log")
@Tag(name = "日志管理接口")
@Transactional
public class LogController {
    @Autowired
    private ILogService iLogService;

    @SystemLog(about = "查询日志", type = LogType.DATA_CENTER, dotype = "LOG-01")
    @RequestMapping(value = "/getAllByPage", method = RequestMethod.GET)
    @Operation(summary = "查询日志")
    public Result<Object> getAllByPage(@ModelAttribute Log log, @ModelAttribute PageVo pageVo) {
        QueryWrapper<Log> queryWrapper = new QueryWrapper<>();
        if (!NullUtils.isNull(log.getName())) queryWrapper.like("name", log.getName());
        if (log.getLogType() != null) queryWrapper.eq("log_type", log.getLogType());
        if (!NullUtils.isNull(log.getUsername())) queryWrapper.like("username", log.getUsername());
        if (!NullUtils.isNull(log.getIp())) queryWrapper.like("ip", log.getIp());
        if (!NullUtils.isNull(log.getStartDate())) {
            queryWrapper.ge("create_time", log.getStartDate());
            queryWrapper.le("create_time", log.getEndDate());
        }
        return ResultUtil.data(iLogService.page(PageUtil.initMyPage(pageVo), queryWrapper));
    }
}
