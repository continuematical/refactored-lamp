package com.example.demo.data.controller;

import com.example.demo.basics.baseVo.Result;
import com.example.demo.basics.log.LogType;
import com.example.demo.basics.log.SystemLog;
import com.example.demo.basics.utils.ResultUtil;
import com.example.demo.data.entity.Setting;
import com.example.demo.data.service.ISettingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@Tag(name = "全局设置接口")
@RequestMapping("/zhou/setting")
public class SettingController {

    @Autowired
    private ISettingService iSettingService;

    @SystemLog(about = "查看单个配置", type = LogType.DATA_CENTER, dotype = "SETTING-01")
    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    @Operation(summary = "查看单个配置")
    public Result<Setting> getOne(@RequestParam String id) {
        return new ResultUtil<Setting>().setData(iSettingService.getById(id));
    }

    @SystemLog(about = "修改单个配置", type = LogType.DATA_CENTER, dotype = "SETTING-02")
    @RequestMapping(value = "/setOne", method = RequestMethod.GET)
    @Operation(summary = "修改单个配置")
    public Result<Object> setOne(@RequestParam String id, @RequestParam String value) {
        Setting setting = iSettingService.getById(id);
        if (setting == null) return ResultUtil.error("不存在");
        if (!Objects.equals(value, setting.getValue())){
            setting.setValue(value);
            iSettingService.saveOrUpdate(setting);
        }
        return ResultUtil.success();
    }
}
