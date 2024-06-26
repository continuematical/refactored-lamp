package com.example.demo.basics.baseclass;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

@Tag(name = "模板服务层")
@FunctionalInterface
public interface BaseService<E, ID extends Serializable> {
    BaseDao<E, ID> getRepository();

    @Operation(summary = "查询")
    default E get(ID id) {
        return getRepository().findById(id).orElse(null);
    }

    @Operation(summary = "查询")
    default List<E> getAll(){
        return getRepository().findAll();
    }

    @Operation(summary = "查询")
    default Page<E> findAll(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    @Operation(summary = "新增")
    default E save(E entity) {
        return getRepository().save(entity);
    }

    @Operation(summary = "编辑")
    default E update(E entity) {
        return getRepository().saveAndFlush(entity);
    }

    @Operation(summary = "批量保存")
    default Iterable<E> saveOrUpdateAll(Iterable<E> entities) {
        return getRepository().saveAllAndFlush(entities);
    }

    @Operation(summary = "删除")
    default void delete(E entity) {
        getRepository().delete(entity);
    }

    @Operation(summary = "删除")
    default void delete(ID id) {
        getRepository().deleteById(id);
    }

    @Operation(summary = "删除")
    default void delete(Iterable<E> entities) {
        getRepository().deleteAllInBatch(entities);
    }

    @Operation(summary = "计数")
    default Long count(){
        return getRepository().count();
    }
}
