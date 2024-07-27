package com.example.demo.data.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.basics.baseVo.PageVo;
import com.example.demo.basics.baseVo.Result;
import com.example.demo.basics.log.LogType;
import com.example.demo.basics.log.SystemLog;
import com.example.demo.basics.utils.PageUtil;
import com.example.demo.basics.utils.ResultUtil;
import com.example.demo.data.entity.Dict;
import com.example.demo.data.entity.DictData;
import com.example.demo.data.service.IDictDataService;
import com.example.demo.data.service.IDictService;
import com.example.demo.data.utils.NullUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/zhou/dictData")
@Tag(name = "字典数据接口")
@Transactional
public class DictDataController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private IDictService iDictService;

    @Autowired
    private IDictDataService iDictDataService;

    private static final String REDIS_DIST_DATA_PRE_STR = "dictData::";

    @SystemLog(about = "查询单个数据字典的值", type = LogType.DATA_CENTER, dotype = "DICT_DATA-01")
    @RequestMapping(value = "/getByType/{type}", method = RequestMethod.GET)
    @Operation(summary = "查询单个数据字典的值")
    public Result<Object> getByType(@PathVariable String type) {
        QueryWrapper<Dict> qw = new QueryWrapper<>();
        qw.eq("type", type);
        Dict selectDict = iDictService.getOne(qw);
        if (selectDict == null) {
            return ResultUtil.error("字典 " + type + " 不存在");
        }
        QueryWrapper<DictData> dataQw = new QueryWrapper<>();
        dataQw.eq("dict_id", selectDict.getId());
        return ResultUtil.data(iDictDataService.list(dataQw));
    }

    @SystemLog(about = "查询数据字典的值", type = LogType.DATA_CENTER, dotype = "DICT_DATA-02")
    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @Operation(summary = "查询数据字典的值")
    public Result<IPage<DictData>> getByCondition(@ModelAttribute DictData dictData, @ModelAttribute PageVo pageVo) {
        QueryWrapper<DictData> qw = new QueryWrapper<>();
        if (!NullUtils.isNull(dictData.getDictId())) qw.eq("dict_id", dictData.getDictId());
        if (!Objects.equals(null, dictData.getStatus())) qw.eq("dict_status", dictData.getStatus());
        if (!NullUtils.isNull(dictData.getValue())) qw.like("value", dictData.getValue());
        if (!NullUtils.isNull(dictData.getTitle())) qw.like("title", dictData.getTitle());
        if (!NullUtils.isNull(dictData.getDescription())) qw.like("description", dictData.getDescription());

        IPage<DictData> data = iDictDataService.page(PageUtil.initMyPage(pageVo), qw);
        for (DictData dd : data.getRecords()) {
            if (dd != null) {
                Dict dict = iDictService.getById(dd.getDictId());
                if (dict != null) {
                    dd.setDictName(dict.getTitle());
                }
            }
        }
        return new ResultUtil<IPage<DictData>>().setData(data);
    }

    @SystemLog(about = "删除数据字典的值", type = LogType.DATA_CENTER, dotype = "DICT_DATA-03")
    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @Operation(summary = "删除数据字典的值")
    public Result<Object> delByIds(@RequestParam String[] ids) {
        for (String id : ids) {
            DictData selectDictData = iDictDataService.getById(id);
            Dict dict = iDictService.getById(selectDictData.getDictId());
            if (selectDictData == null) continue;
            iDictDataService.removeById(selectDictData);
            stringRedisTemplate.delete(REDIS_DIST_DATA_PRE_STR + dict.getType());
        }
        return ResultUtil.success();
    }

    @SystemLog(about = "添加数据字典值", type = LogType.DATA_CENTER, dotype = "DICT_DATA-04")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Operation(summary = "添加数据字典值")
    public Result<Object> add(DictData dictData) {
        Dict selectDict = iDictService.getById(dictData.getDictId());
        if (selectDict == null) return ResultUtil.error("字典不存在");
        iDictDataService.saveOrUpdate(dictData);
        stringRedisTemplate.delete(REDIS_DIST_DATA_PRE_STR + selectDict.getType());
        return ResultUtil.success();
    }

    @SystemLog(about = "编辑数据字典值", type = LogType.DATA_CENTER, dotype = "DICT_DATA-05")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @Operation(summary = "编辑数据字典值")
    public Result<Object> edit(DictData dictData) {
        iDictDataService.saveOrUpdate(dictData);
        Dict selectDict = iDictService.getById(dictData.getDictId());
        stringRedisTemplate.delete(REDIS_DIST_DATA_PRE_STR + selectDict.getType());
        return ResultUtil.success();
    }
}
