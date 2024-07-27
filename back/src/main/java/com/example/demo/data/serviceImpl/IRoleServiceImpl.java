package com.example.demo.data.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.data.dao.mapper.RoleMapper;
import com.example.demo.data.entity.Role;
import com.example.demo.data.service.IRoleService;
import org.springframework.stereotype.Service;

@Service
public class IRoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
}
