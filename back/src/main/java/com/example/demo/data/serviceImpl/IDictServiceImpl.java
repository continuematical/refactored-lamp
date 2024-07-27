package com.example.demo.data.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.data.dao.mapper.DictMapper;
import com.example.demo.data.entity.Dict;
import com.example.demo.data.service.IDictService;
import org.springframework.stereotype.Service;

@Service
public class IDictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {
}
