package com.example.demo.data.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.data.dao.mapper.FileMapper;
import com.example.demo.data.entity.File;
import com.example.demo.data.service.IFileService;
import org.springframework.stereotype.Service;

@Service
public class IFileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {
}
