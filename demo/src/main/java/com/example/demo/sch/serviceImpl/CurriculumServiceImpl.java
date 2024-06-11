package com.example.demo.sch.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.sch.entity.Curriculum;
import com.example.demo.sch.mapper.CurriculumMapper;
import com.example.demo.sch.service.CurriculumService;
import org.springframework.stereotype.Service;

@Service
public class CurriculumServiceImpl extends ServiceImpl<CurriculumMapper, Curriculum> implements CurriculumService {

}
