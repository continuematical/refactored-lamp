package com.example.demo.basics.baseclass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

// 创建仓库接口
public interface BaseDao<E,ID extends Serializable> extends JpaRepository<E,ID>, JpaSpecificationExecutor<E> {
    @Override
    E getById(ID id);
}
