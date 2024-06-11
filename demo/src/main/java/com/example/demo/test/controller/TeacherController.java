package com.example.demo.test.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.basics.baseVo.PageVo;
import com.example.demo.basics.baseVo.Result;
import com.example.demo.basics.log.LogType;
import com.example.demo.basics.log.SystemLog;
import com.example.demo.basics.utils.PageUtil;
import com.example.demo.basics.utils.ResultUtil;
import com.example.demo.data.utils.NullUtils;
import com.example.demo.data.vo.AntvVo;
import com.example.demo.test.entity.Teacher;
import com.example.demo.test.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController()
@Tag(name = "教师管理接口")
@Transactional
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @SystemLog(about = "查询单条教师", type = LogType.MORE_MODULE, dotype = "TEACHER-01")
    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    @Operation(summary = "查询单条教师")
    public Result<Teacher> get(@RequestParam String id) {
        return new ResultUtil<Teacher>().setData(teacherService.getById(id));
    }

    @SystemLog(about = "查询全部教师个数", type = LogType.MORE_MODULE, dotype = "TEACHER-02")
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @Operation(summary = "查询全部教师个数")
    public Result<Long> getCount() {
        return new ResultUtil<Long>().setData(teacherService.count());
    }

    @SystemLog(about = "查询全部教师", type = LogType.MORE_MODULE, dotype = "TEACHER-03")
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @Operation(summary = "查询全部教师")
    public Result<List<Teacher>> getAll() {
        return new ResultUtil<List<Teacher>>().setData(teacherService.list());
    }

    @SystemLog(about = "查询教师", type = LogType.MORE_MODULE, dotype = "TEACHER-04")
    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @Operation(summary = "查询教师")
    public Result<IPage<Teacher>> getByPage(@ModelAttribute Teacher teacher, @ModelAttribute PageVo page) {
        QueryWrapper<Teacher> qw = new QueryWrapper<>();
        if (!NullUtils.isNull(teacher.getName())) {
            qw.like("name", teacher.getName());
        }
        if (!NullUtils.isNull(teacher.getEducation())) {
            qw.eq("education", teacher.getEducation());
        }
        if (!NullUtils.isNull(teacher.getGraduated())) {
            qw.like("graduated", teacher.getGraduated());
        }
        IPage<Teacher> data = teacherService.page(PageUtil.initMyPage(page), qw);
        return new ResultUtil<IPage<Teacher>>().setData(data);
    }

    @SystemLog(about = "增改教师", type = LogType.MORE_MODULE, dotype = "TEACHER-05")
    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @Operation(summary = "增改教师")
    public Result<Teacher> saveOrUpdate(Teacher teacher) {
        if (teacherService.saveOrUpdate(teacher)) {
            return new ResultUtil<Teacher>().setData(teacher);
        }
        return ResultUtil.error();
    }

    @SystemLog(about = "新增教师", type = LogType.MORE_MODULE, dotype = "TEACHER-06")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @Operation(summary = "新增教师")
    public Result<Teacher> insert(Teacher teacher) {
        teacherService.saveOrUpdate(teacher);
        return new ResultUtil<Teacher>().setData(teacher);
    }

    @SystemLog(about = "编辑教师", type = LogType.MORE_MODULE, dotype = "TEACHER-07")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Operation(summary = "编辑教师")
    public Result<Teacher> update(Teacher teacher) {
        teacherService.saveOrUpdate(teacher);
        return new ResultUtil<Teacher>().setData(teacher);
    }

    @SystemLog(about = "删除教师", type = LogType.MORE_MODULE, dotype = "TEACHER-08")
    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @Operation(summary = "删除教师")
    public Result<Object> delByIds(@RequestParam String[] ids) {
        for (String id : ids) {
            teacherService.removeById(id);
        }
        return ResultUtil.success();
    }

    @SystemLog(about = "查询图表数据", type = LogType.CHART, dotype = "CHART-01")
    @RequestMapping(value = "/getAntvVoList", method = RequestMethod.GET)
    @Operation(summary = "查询图表数据")
    public Result<List<AntvVo>> getAntvVoList() {
        List<AntvVo> ansList = new ArrayList<>();
        List<Teacher> teacherList = teacherService.list();
        for (Teacher o : teacherList) {
            boolean flag = false;
            for (AntvVo vo : ansList) {
                if (Objects.equals(vo.getTitle(), o.getName())) {
                    flag = true;
                    vo.setValue(vo.getValue().add(o.getWages()));
                    break;
                }
            }
            if (!flag) {
                AntvVo vo = new AntvVo();
                vo.setTitle(o.getName());
                vo.setType("工资金额");
                vo.setValue(o.getWages());
                ansList.add(vo);
            }
        }
        return new ResultUtil<List<AntvVo>>().setData(ansList);
    }
}
