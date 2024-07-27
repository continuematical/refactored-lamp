package com.example.demo.data.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.data.dao.mapper.PermissionMapper;
import com.example.demo.data.entity.Permission;
import com.example.demo.data.service.IPermissionService;
import org.springframework.stereotype.Service;

@Service
public class IPermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {
}
