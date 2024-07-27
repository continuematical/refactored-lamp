package com.example.demo.data.controller;

import cn.hutool.core.date.DateUtil;
import com.example.demo.basics.baseVo.PageVo;
import com.example.demo.basics.baseVo.Result;
import com.example.demo.basics.redis.RedisTemplateHelper;
import com.example.demo.basics.utils.PageUtil;
import com.example.demo.basics.utils.ResultUtil;
import com.example.demo.data.utils.NullUtils;
import com.example.demo.data.vo.RedisVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/zhou/redis")
@Tag(name = "缓存管理接口")
@Transactional
public class RedisController {
    @Schema(name = "最大键值数")
    private static final int maxSize = 100000;

    //日期格式
    private static final String DATE_FORMAT_IN_REDIS = "HH:mm:ss";

    //用于查找redis数据库的全部值
    private static final String STEP_STR_IN_REDIS = "*";

    private static final Integer INIT_SIZE_IN_REDIS = 16;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RedisTemplateHelper redisTemplateHelper;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @Operation(summary = "新增")
    public Result<Object> save(@RequestParam String key, @RequestParam String value, @RequestParam Long expireTime) {
        if (expireTime == 0L) {
            return ResultUtil.success();
        }
        if (expireTime < 0) {
            redisTemplate.opsForValue().set(key, value);
        }
        redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
        return ResultUtil.success();
    }

    @RequestMapping(value = "/delByKeys", method = RequestMethod.POST)
    @Operation(summary = "删除")
    public Result<Object> delByKeys(@RequestParam String[] keys) {
        for (String redisKey : keys) {
            redisTemplate.delete(redisKey);
        }
        return ResultUtil.success();
    }

    @RequestMapping(value = "/delAll", method = RequestMethod.POST)
    @Operation(summary = "全部删除")
    public Result<Object> delAll() {
        redisTemplate.delete(redisTemplateHelper.keys(STEP_STR_IN_REDIS));
        return ResultUtil.success();
    }

    @RequestMapping(value = "/getKeySize", method = RequestMethod.GET)
    @Operation(summary = "获取实时key大小")
    public Result<Object> getKeySize() {
        Map<String, Object> map = new HashMap<>(INIT_SIZE_IN_REDIS);
        map.put("keySize", redisTemplate.getConnectionFactory().getConnection().dbSize());
        map.put("time", DateUtil.format(new Date(), DATE_FORMAT_IN_REDIS));
        return ResultUtil.data(map);
    }

    @RequestMapping(value = "/getMemory", method = RequestMethod.GET)
    @Operation(summary = "获取实时内存大小")
    public Result<Object> getMemory() {
        Map<String, Object> hashMap = new HashMap<>(INIT_SIZE_IN_REDIS);
        Properties properties = redisTemplate.getConnectionFactory().getConnection().info("memory");
        hashMap.put("memory", properties.get("used_memory"));
        hashMap.put("time", DateUtil.format(new Date(), DATE_FORMAT_IN_REDIS));
        return ResultUtil.data(hashMap);
    }

    @RequestMapping(value = "/getAllByPage", method = RequestMethod.GET)
    @Operation(summary = "查询Redis数据")
    public Result<Page<RedisVo>> getAllByPage(@RequestParam(required = false) String key, PageVo pageVo) {
        List<RedisVo> list = new ArrayList<>();
        if (!NullUtils.isNull(key)) {
            key = STEP_STR_IN_REDIS + key + STEP_STR_IN_REDIS;
        } else {
            key = STEP_STR_IN_REDIS;
        }
        Set<String> keyListInSet = redisTemplateHelper.keys(key);
        int keyListInSetSize = keyListInSet.size();
        if (keyListInSetSize > maxSize) {
            keyListInSetSize = maxSize;
        }
        int i = 0;
        for (String keyInSet : keyListInSet) {
            if (i > keyListInSetSize) {
                break;
            }
            RedisVo redisVo = new RedisVo(keyInSet, "", redisTemplate.getExpire(keyInSet, TimeUnit.SECONDS));
            list.add(redisVo);
            i++;
        }
        Page<RedisVo> page = new PageImpl<RedisVo>(PageUtil.listPage(pageVo, list), PageUtil.initPage(pageVo), keyListInSetSize);
        for (RedisVo vo : page.getContent()) {
            String ansValue = null;
            try {
                ansValue = redisTemplate.opsForValue().get(vo.getKey());
                if (ansValue.length() > 100) {
                    ansValue = ansValue.substring(0, 100) + "..";
                }
                vo.setValue(ansValue);
            } catch (Exception ex) {
                vo.setValue("二进制内容");
            }
        }
        return new ResultUtil<Page<RedisVo>>().setData(page);
    }

    @RequestMapping(value = "/getByKey/{key}", method = RequestMethod.GET)
    @Operation(summary = "通过key获取")
    public Result<Object> getByKey(@PathVariable String key) {
        Map<String, Object> map = new HashMap<>();
        String redisValue = redisTemplate.opsForValue().get(key);
        Long expireTimeUnit = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        map.put("value", redisValue);
        map.put("expireTime", expireTimeUnit);
        return ResultUtil.data(map);
    }
}