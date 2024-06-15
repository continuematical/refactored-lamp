package com.example.demo.data.serviceImpl;

import com.example.demo.basics.baseclass.BaseDao;
import com.example.demo.data.dao.LogDao;
import com.example.demo.data.entity.Log;
import com.example.demo.data.service.LogService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class LogServiceImpl implements LogService {
    @Autowired
    private LogDao logDao;

    @Override
    public BaseDao<Log, String> getRepository() {
        return logDao;
    }
}
