package org.example.t197_back.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 分页工具类
 */
public class PageUtils implements Serializable {
    private static final long serialVersionUID = 1L;
    //总记录数
    private long total;
    //每页记录数
    private long pageSize;
    //当前页数
    private long currentPage;
    //总页数
    private long totalPages;
    //列表数据
    private List<?> list;

    public PageUtils(List<?> list, int totalCount, int pageSize, int currentPage) {
        this.list = list;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.total = totalCount;
        this.totalPages = (int) Math.ceil((double) totalCount / pageSize);
    }

    public PageUtils(Page<?> page){
        this.list = page.getRecords();
        this.total = page.getTotal();
        this.pageSize = page.getSize();
        this.currentPage = page.getCurrent();
        this.totalPages = page.getPages();
    }

    /*
     * 空数据的分页
     */
    public PageUtils(Map<String, Object> params) {

    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }
}
