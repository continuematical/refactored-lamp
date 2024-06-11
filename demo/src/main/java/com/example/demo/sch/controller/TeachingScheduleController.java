package com.example.demo.sch.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.basics.baseVo.PageVo;
import com.example.demo.basics.baseVo.Result;
import com.example.demo.basics.utils.PageUtil;
import com.example.demo.basics.utils.ResultUtil;
import com.example.demo.data.utils.NullUtils;
import com.example.demo.sch.entity.TeachingSchedule;
import com.example.demo.sch.service.TeachingScheduleService;
import com.example.demo.sch.vo.TeachingScheduleVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(name = "排课管理接口")
@RequestMapping("/teachingSchedule")
@Transactional
public class TeachingScheduleController {

    @Autowired
    private TeachingScheduleService teachingScheduleService;

    @RequestMapping(value = "/getOne",method = RequestMethod.GET)
    @Operation(summary = "查询单条排课")
    public Result<TeachingSchedule> get(@RequestParam String id) {
        return new ResultUtil<TeachingSchedule>().setData(teachingScheduleService.getById(id));
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @Operation(summary = "查询全部排课个数")
    public Result<Long> getCount() {
        return new ResultUtil<Long>().setData(teachingScheduleService.count());
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @Operation(summary = "查询全部排课")
    public Result<List<TeachingSchedule>> getAll() {
        return new ResultUtil<List<TeachingSchedule>>().setData(teachingScheduleService.list());
    }

    @RequestMapping(value = "/getCardList", method = RequestMethod.GET)
    @Operation(summary = "查询全部排课")
    public Result<List<TeachingScheduleVo>> getCardList(){
        List<TeachingScheduleVo> ans = new ArrayList<>();
        QueryWrapper<TeachingSchedule> qw1 = new QueryWrapper<>();
        qw1.eq("x",1);
        qw1.orderByAsc("y");
        ans.add(new TeachingScheduleVo(1,teachingScheduleService.list(qw1)));
        QueryWrapper<TeachingSchedule> qw2 = new QueryWrapper<>();
        qw2.eq("x",2);
        qw2.orderByAsc("y");
        ans.add(new TeachingScheduleVo(2,teachingScheduleService.list(qw2)));
        QueryWrapper<TeachingSchedule> qw3 = new QueryWrapper<>();
        qw3.eq("x",3);
        qw3.orderByAsc("y");
        ans.add(new TeachingScheduleVo(3,teachingScheduleService.list(qw3)));
        QueryWrapper<TeachingSchedule> qw4 = new QueryWrapper<>();
        qw4.eq("x",4);
        qw4.orderByAsc("y");
        ans.add(new TeachingScheduleVo(4,teachingScheduleService.list(qw4)));
        QueryWrapper<TeachingSchedule> qw5 = new QueryWrapper<>();
        qw5.eq("x",5);
        qw5.orderByAsc("y");
        ans.add(new TeachingScheduleVo(5,teachingScheduleService.list(qw5)));
        QueryWrapper<TeachingSchedule> qw6 = new QueryWrapper<>();
        qw6.eq("x",6);
        qw6.orderByAsc("y");
        ans.add(new TeachingScheduleVo(6,teachingScheduleService.list(qw6)));
        QueryWrapper<TeachingSchedule> qw7 = new QueryWrapper<>();
        qw7.eq("x",7);
        qw7.orderByAsc("y");
        ans.add(new TeachingScheduleVo(7,teachingScheduleService.list(qw7)));
        return new ResultUtil<List<TeachingScheduleVo>>().setData(ans);
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @Operation(summary = "查询排课")
    public Result<IPage<TeachingSchedule>> getByPage(@ModelAttribute TeachingSchedule teachingSchedule , @ModelAttribute PageVo page){
        QueryWrapper<TeachingSchedule> qw = new QueryWrapper<>();
        if(!NullUtils.isNull(teachingSchedule.getTitle())) {
            qw.like("title",teachingSchedule.getTitle());
        }
        IPage<TeachingSchedule> data = teachingScheduleService.page(PageUtil.initMyPage(page),qw);
        return new ResultUtil<IPage<TeachingSchedule>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @Operation(summary = "增改排课")
    public Result<TeachingSchedule> saveOrUpdate(TeachingSchedule teachingSchedule) {
        if (teachingScheduleService.saveOrUpdate(teachingSchedule)) {
            return new ResultUtil<TeachingSchedule>().setData(teachingSchedule);
        }
        return ResultUtil.error();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @Operation(summary = "新增排课")
    public Result<TeachingSchedule> insert(TeachingSchedule teachingSchedule) {
        teachingScheduleService.saveOrUpdate(teachingSchedule);
        return new ResultUtil<TeachingSchedule>().setData(teachingSchedule);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Operation(summary = "编辑排课")
    public Result<TeachingSchedule> update(TeachingSchedule teachingSchedule) {
        teachingScheduleService.saveOrUpdate(teachingSchedule);
        return new ResultUtil<TeachingSchedule>().setData(teachingSchedule);
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @Operation(summary = "删除排课")
    public Result<Object> delByIds(@RequestParam String[] ids) {
        for (String id : ids) {
            teachingScheduleService.removeById(id);
        }
        return ResultUtil.success();
    }
}
