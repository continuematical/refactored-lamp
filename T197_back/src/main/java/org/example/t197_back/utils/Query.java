package org.example.t197_back.utils;

import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 */
public class Query<T> extends LinkedHashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    //分页参数
    private Page<T> page;

    //当前页码
    private int currentPage = 1;

    //每条页数
    private int limit = 10;

    public Query(Map<String, Object> params) {
        this.putAll(params);

        //分页参数
        if (params.get("page") != null) {
            currentPage = Integer.parseInt((String) params.get("page"));
        }
        if (params.get("limit") != null) {
            limit = Integer.parseInt((String) params.get("limit"));
        }

        this.put("offset", (currentPage - 1) * limit);
        this.put("limit", limit);
        this.put("page", currentPage);

        //防止SQL注入
        String sidx = SQLFilter.sqlInject((String)params.get("sidx"));
        String order = SQLFilter.sqlInject((String)params.get("order"));
        this.put("sidx", sidx);
        this.put("order", order);

        //mybatis-plus分页
        this.page = new Page<>(currentPage, limit);

        //排序
        if(StringUtils.isNotBlank(sidx) && StringUtils.isNotBlank(order)){
            this.page.setOrderByField(sidx);
            this.page.setAsc("ASC".equalsIgnoreCase(order));
        }
    }

    public Page<T> getPage() {
        return page;
    }

    public int getCurrPage() {
        return currentPage;
    }

    public int getLimit() {
        return limit;
    }
}
