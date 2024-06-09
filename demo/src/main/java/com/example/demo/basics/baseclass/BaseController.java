package com.example.demo.basics.baseclass;

import com.example.demo.basics.baseVo.PageVo;
import com.example.demo.basics.baseVo.Result;
import com.example.demo.basics.utils.PageUtil;
import com.example.demo.basics.utils.ResultUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.List;

@Tag(name = "数据控制层")
public abstract class BaseController<E, ID extends Serializable> {
    public abstract BaseService<E, ID> getService();

    @RequestMapping(value = "/getOne", name = "查询单个数据", method = RequestMethod.GET)
    @ResponseBody
    @Operation(summary = "查询单个数据")
    public Result<E> getOne(@RequestParam ID id) {
        return new ResultUtil<E>().setData(getService().get(id));
    }

    @RequestMapping(value = "/getAll", name = "查询所有数据", method = RequestMethod.GET)
    @ResponseBody
    @Operation(summary = "查询所有数据")
    public Result<List<E>> getAll() {
        return new ResultUtil<List<E>>().setData(getService().getAll());
    }

    @RequestMapping(value = "/getByPage", name = "查询某一页数据", method = RequestMethod.GET)
    @ResponseBody
    @Operation(summary = "查询某一页数据")
    public Result<Page<E>> getByPage(PageVo page) {
        return new ResultUtil<Page<E>>().setData(getService().findAll(PageUtil.initPage(page)));
    }

    @RequestMapping(value = "/save", name = "新增数据", method = RequestMethod.POST)
    @ResponseBody
    @Operation(summary = "新增数据")
    public Result<E> save(E entity) {
        return new ResultUtil<E>().setData(getService().save(entity));
    }

    @RequestMapping(value = "/update", name = "更新数据", method = RequestMethod.POST)
    @ResponseBody
    @Operation(summary = "编辑数据")
    public Result<E> update(E entity) {
        return new ResultUtil<E>().setData(getService().update(entity));
    }

    @RequestMapping(value = "/count", name = "查询数据条数",  method = RequestMethod.POST)
    @ResponseBody
    @Operation(summary = "查询数据条数")
    public Result<Long> count(){
        return new ResultUtil<Long>().setData(getService().count());
    }
    @RequestMapping(value = "/delOne", name = "删除数据",  method = RequestMethod.POST)
    @ResponseBody
    @Operation(summary = "删除数据")
    public Result<Object> delByIds(@RequestParam ID id){
        getService().delete(id);
        return new ResultUtil<Object>().setSuccessMsg("OK");
    }

    @RequestMapping(value = "/delByIds", name = "删除数据",  method = RequestMethod.POST)
    @ResponseBody
    @Operation(summary = "删除数据")
    public Result<Object> delByIds(@RequestParam ID[] ids){
        for(ID id:ids){
            getService().delete(id);
        }
        return new ResultUtil<Object>().setSuccessMsg("OK");
    }
}
