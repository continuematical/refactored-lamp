package com.example.demo.data.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.data.dao.mapper.DepartmentHeaderMapper;
import com.example.demo.data.entity.DepartmentHeader;
import com.example.demo.data.service.IDepartmentHeaderService;
import org.springframework.stereotype.Service;

@Service
public class IDepartmentHeaderServiceImpl extends ServiceImpl<DepartmentHeaderMapper, DepartmentHeader> implements IDepartmentHeaderService {
}
