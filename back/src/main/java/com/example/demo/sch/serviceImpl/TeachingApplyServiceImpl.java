package com.example.demo.sch.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.sch.entity.TeachingApply;
import com.example.demo.sch.mapper.TeachingApplyMapper;
import com.example.demo.sch.service.TeachingApplyService;
import org.springframework.stereotype.Service;

@Service
public class TeachingApplyServiceImpl extends ServiceImpl<TeachingApplyMapper, TeachingApply> implements TeachingApplyService {
}
