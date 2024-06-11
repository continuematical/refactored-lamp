package com.example.demo.sch.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.sch.entity.Curriculum;
import com.example.demo.sch.entity.TeachingSchedule;
import com.example.demo.sch.mapper.CurriculumMapper;
import com.example.demo.sch.mapper.TeachingScheduleMapper;
import com.example.demo.sch.service.CurriculumService;
import com.example.demo.sch.service.TeachingScheduleService;
import org.springframework.stereotype.Service;

@Service
public class TeachingScheduleServiceImpl extends ServiceImpl<TeachingScheduleMapper, TeachingSchedule> implements TeachingScheduleService {

}