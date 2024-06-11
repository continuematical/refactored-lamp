package com.example.demo.data.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.data.dao.mapper.DepartmentMapper;
import com.example.demo.data.entity.Department;
import com.example.demo.data.service.IDepartmentService;
import org.springframework.stereotype.Service;

@Service
public class IDepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {
}
