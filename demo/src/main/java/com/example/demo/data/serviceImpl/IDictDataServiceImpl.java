package com.example.demo.data.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.data.dao.mapper.DictDataMapper;
import com.example.demo.data.entity.DictData;
import com.example.demo.data.service.IDictDataService;
import org.springframework.stereotype.Service;

@Service
public class IDictDataServiceImpl extends ServiceImpl<DictDataMapper, DictData> implements IDictDataService {
}
