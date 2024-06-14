package com.example.demo.data.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.data.dao.mapper.LogMapper;
import com.example.demo.data.entity.Log;
import com.example.demo.data.service.ILogService;
import org.springframework.stereotype.Service;

@Service
public class ILogServiceImpl extends ServiceImpl<LogMapper, Log> implements ILogService {
}
