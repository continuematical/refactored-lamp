package com.example.demo.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.test.entity.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
}
