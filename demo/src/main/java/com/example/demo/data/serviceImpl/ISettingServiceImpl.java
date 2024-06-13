package com.example.demo.data.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.data.dao.mapper.SettingMapper;
import com.example.demo.data.entity.Setting;
import com.example.demo.data.service.ISettingService;
import org.springframework.stereotype.Service;

@Service
public class ISettingServiceImpl extends ServiceImpl<SettingMapper, Setting> implements ISettingService {
}
