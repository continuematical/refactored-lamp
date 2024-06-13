package com.example.demo.data.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.data.dao.mapper.RolePermissionMapper;
import com.example.demo.data.entity.RolePermission;
import com.example.demo.data.service.IRolePermissionService;
import org.springframework.stereotype.Service;

@Service
public class IRolePermissionImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements IRolePermissionService {
}
