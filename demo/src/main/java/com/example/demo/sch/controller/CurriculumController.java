package com.example.demo.sch.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.basics.baseVo.PageVo;
import com.example.demo.basics.baseVo.Result;
import com.example.demo.basics.utils.PageUtil;
import com.example.demo.basics.utils.ResultUtil;
import com.example.demo.data.utils.NullUtils;
import com.example.demo.sch.entity.Curriculum;
import com.example.demo.sch.service.CurriculumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "课程管理接口")
@RequestMapping("/zhou/curriculum")
@RestController()
public class CurriculumController {

    @Autowired
    private CurriculumService curriculumService;

    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    @Operation(summary = "查询单条课程")
    public Result<Curriculum> get(@RequestParam String id) {
        return new ResultUtil<Curriculum>().setData(curriculumService.getById(id));
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @Operation(summary = "查询全部课程个数")
    public Result<Long> getCount() {
        return new ResultUtil<Long>().setData(curriculumService.count());
    }


    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @Operation(summary = "查询课程")
    public Result<IPage<Curriculum>> getByPage(@ModelAttribute Curriculum curriculum, @ModelAttribute PageVo pageVo) {
        QueryWrapper<Curriculum> queryWrapper = new QueryWrapper<>();
        if (!NullUtils.isNull(curriculum.getTitle())) {
            queryWrapper.like("title", curriculum.getTitle());
        }
        if (!NullUtils.isNull(curriculum.getStatus())) {
            queryWrapper.like("status", curriculum.getStatus());
        }
        IPage<Curriculum> data = curriculumService.page(PageUtil.initMyPage(pageVo), queryWrapper);
        return new ResultUtil<IPage<Curriculum>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @Operation(summary = "增改课程")
    public Result<Curriculum> saveOrUpdate(Curriculum curriculum){
        if(curriculumService.saveOrUpdate(curriculum)){
            return new ResultUtil<Curriculum>().setData(curriculum);
        }
        return ResultUtil.error();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @Operation(summary = "新增课程")
    public Result<Curriculum> insert(Curriculum curriculum){
        curriculumService.saveOrUpdate(curriculum);
        return new ResultUtil<Curriculum>().setData(curriculum);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Operation(summary = "编辑课程")
    public Result<Curriculum> update(Curriculum curriculum){
        curriculumService.saveOrUpdate(curriculum);
        return new ResultUtil<Curriculum>().setData(curriculum);
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @Operation(summary = "删除课程")
    public Result<Object> delByIds(@RequestParam String[] ids){
        for(String id : ids){
            curriculumService.removeById(id);
        }
        return ResultUtil.success();
    }
}
