package org.example.t197_back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.t197_back.entity.MedicineEntity;
import org.example.t197_back.utils.PageUtils;

import java.util.Map;

public interface MedicineService extends IService<MedicineEntity> {
    /**
     * 查询参数
     */
    PageUtils queryPage(Map<String, Object> params);
}
