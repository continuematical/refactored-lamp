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
import com.example.demo.test.entity.Student;
import com.example.demo.test.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/student")
@Transactional
@Tag(name = "学生管理接口")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    @SystemLog(about = "查询单条学生", type = LogType.MORE_MODULE, dotype = "STUDENT-01")
    @Operation(summary = "查询单条学生")
    public Result<Student> getOne(@RequestParam String id) {
        return new ResultUtil<Student>().setData(studentService.getById(id));
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @SystemLog(about = "查询学生个数", type = LogType.MORE_MODULE, dotype = "STUDENT-02")
    @Operation(summary = "查询学生个数")
    public Result<Long> count() {
        return new ResultUtil<Long>().setData(studentService.count());
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @SystemLog(about = "查询全部学生", type = LogType.MORE_MODULE, dotype = "STUDENT-03")
    @Operation(summary = "查询全部学生")
    public Result<List<Student>> getAll() {
        return new ResultUtil<List<Student>>().setData(studentService.list());
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @SystemLog(about = "查询学生", type = LogType.MORE_MODULE, dotype = "STUDENT-04")
    @Operation(summary = "查询学生")
    public Result<IPage<Student>> getByPage(@ModelAttribute Student student, @ModelAttribute PageVo pageVo) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        if (!NullUtils.isNull(student.getName())) {
            queryWrapper.like("name", student.getName());
        }
        if (!NullUtils.isNull(student.getNumber())) {
            queryWrapper.like("number", student.getNumber());
        }
        if (!NullUtils.isNull(student.getSex())) {
            queryWrapper.like("sex", student.getSex());
        }
        if (!NullUtils.isNull(student.getSchool())) {
            queryWrapper.like("school", student.getSchool());
        }
        IPage<Student> data = studentService.page(PageUtil.initMyPage(pageVo));
        return new ResultUtil<IPage<Student>>().setData(data);
    }

    @SystemLog(about = "增改学生", type = LogType.MORE_MODULE, dotype = "STUDENT-05")
    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @Operation(summary = "增改学生")
    public Result<Student> saveOrUpdate(Student student) {
        if (studentService.saveOrUpdate(student)) {
            return new ResultUtil<Student>().setData(student);
        }
        return ResultUtil.error();
    }

    @SystemLog(about = "新增学生", type = LogType.MORE_MODULE, dotype = "STUDENT-06")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @Operation(summary = "新增学生")
    public Result<Student> insert(Student student) {
        studentService.saveOrUpdate(student);
        return new ResultUtil<Student>().setData(student);
    }

    @SystemLog(about = "编辑学生", type = LogType.MORE_MODULE, dotype = "STUDENT-07")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Operation(summary = "编辑学生")
    public Result<Student> update(Student student) {
        studentService.saveOrUpdate(student);
        return new ResultUtil<Student>().setData(student);
    }

    @SystemLog(about = "删除学生", type = LogType.MORE_MODULE, dotype = "STUDENT-08")
    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @Operation(summary = "删除学生")
    public Result<Object> delByIds(@RequestParam String[] ids) {
        for (String id : ids) {
            studentService.removeById(id);
        }
        return ResultUtil.success();
    }
}
