package com.example.demo.data.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.data.dao.mapper.UserRoleMapper;
import com.example.demo.data.entity.UserRole;
import com.example.demo.data.service.IUserRoleService;
import org.springframework.stereotype.Service;

@Service
public class IUserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {
}
