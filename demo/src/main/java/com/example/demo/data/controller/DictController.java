package com.example.demo.data.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.basics.baseVo.Result;
import com.example.demo.basics.log.LogType;
import com.example.demo.basics.log.SystemLog;
import com.example.demo.basics.utils.ResultUtil;
import com.example.demo.data.entity.Dict;
import com.example.demo.data.entity.DictData;
import com.example.demo.data.entity.Log;
import com.example.demo.data.service.IDictDataService;
import com.example.demo.data.service.IDictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveGeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController()
@RequestMapping("/zhou/dict")
@Tag(name = "字典管理接口")
public class DictController {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private IDictService iDictService;

    @Autowired
    private IDictDataService iDictDataService;

    @SystemLog(about = "查询所有数据字典", type = LogType.DATA_CENTER, dotype = "DICT-01")
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @Operation(summary = "查询所有数据字典")
    public Result<List<Dict>> getAll() {
        return new ResultUtil<List<Dict>>().setData(iDictService.list());
    }

    @SystemLog(about = "模拟搜索", type = LogType.DATA_CENTER, dotype = "DICT-02")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @Operation(summary = "模拟搜索")
    public Result<List<Dict>> search(@RequestParam String key) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", key);
        return new ResultUtil<List<Dict>>().setData(iDictService.list(queryWrapper));
    }

    @SystemLog(about = "添加字典数据", type = LogType.DATA_CENTER, dotype = "DICT-03")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Operation(summary = "添加字典数据")
    public Result<Object> add(Dict dict) {
        QueryWrapper<Dict> qw = new QueryWrapper<>();
        qw.eq("type", dict.getType());
        long dictCount = iDictService.count(qw);
        if (dictCount < 1L) {
            iDictService.saveOrUpdate(dict);
            return ResultUtil.success();
        }
        return ResultUtil.error("字典已存在,不能同名");
    }

    @SystemLog(about = "编辑数据字典", type = LogType.DATA_CENTER, dotype = "DICT-04")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @Operation(summary = "编辑数据字典")
    public Result<Object> edit(Dict dict) {
        Dict oldDict = iDictService.getById(dict.getId());
        if (oldDict == null) {
            return ResultUtil.error("字典不存在");
        }
        if (!Objects.equals(dict.getType(), oldDict.getType())) {
            QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("type", dict.getType());
            long dictCount = iDictService.count(queryWrapper);
            if (dictCount > 0L) {
                return ResultUtil.error("字典已存在,不能同名");
            }
        }
        iDictService.saveOrUpdate(dict);
        return ResultUtil.success();
    }

    @SystemLog(about = "删除数据字典", type = LogType.DATA_CENTER, dotype = "DICT-05")
    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @Operation(summary = "删除数据字典")
    public Result<Object> delByIds(@RequestParam String[] ids) {
        for (String id : ids) {
            Dict selectDict = iDictService.getById(id);
            if (selectDict == null) continue;
            iDictService.removeById(id);
            QueryWrapper<DictData> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("dict_id", id);
            iDictDataService.remove(queryWrapper);
            redisTemplate.delete("dictData::" + selectDict.getType());
        }
        return ResultUtil.success();
    }
}