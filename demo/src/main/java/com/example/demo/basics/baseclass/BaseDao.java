package com.example.demo.basics.baseclass;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@Tag(name = "模板数据链路层")
@NoRepositoryBean
// 创建仓库接口
public interface BaseDao<E,ID extends Serializable> extends JpaRepository<E,ID>, JpaSpecificationExecutor<E> {
    @Override
    E getById(ID id);
}
