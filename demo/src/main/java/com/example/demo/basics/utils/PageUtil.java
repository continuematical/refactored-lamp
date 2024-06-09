package com.example.demo.basics.utils;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.example.demo.basics.baseVo.PageVo;
import com.example.demo.basics.exceptions.MyException;
import com.example.demo.data.utils.NullUtils;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ApiOperation(value = "分页工具类")
public class PageUtil {

    private final static String[] NO_CAN_USE_WORDS
            = {"drop", "select", "master", "insert", "truncate", "declare", "delete", "sleep", "update", "alter"};

    private static final String SORT_BY_ASC = "asc";

    private static final String SORT_BY_DESC = "desc";

    private static final String CAMEL_STEP_STR = "_";

    private static final String NULL_STR = "";

    @ApiModelProperty(value = "JPA分页方法")
    public static Pageable initPage(PageVo page) {
        Pageable able = null;
        int pageNumber = page.getPageNumber();
        int pageSize = page.getPageSize();
        String sort = page.getSort();
        String order = page.getOrder();

        pageNumber = pageNumber < 1 ? 1 : pageNumber;
        pageSize = pageSize < 1 ? 1 : pageSize;
        pageSize = pageSize > 100 ? 100 : pageSize;

        if (!NullUtils.isNull(sort)) {
            Sort.Direction direction = NullUtils.isNull(order) ? Sort.Direction.DESC
                    : Sort.Direction.ASC;
            Sort sortBy = Sort.by(direction, sort);
            able = PageRequest.of(pageNumber - 1, pageSize, sortBy);
        } else {
            able = PageRequest.of(pageNumber - 1, pageSize);
        }
        return able;
    }


    @ApiModelProperty(value = "MybatisPlus分页法")
    public static Page initMyPage(PageVo page) {
        Page newPage = null;
        int pageNumber = page.getPageNumber();
        int pageSize = page.getPageSize();
        String sort = page.getSort();
        String order = page.getOrder();
        SQLInject(sort);

        pageNumber = pageNumber < 1 ? 1 : pageNumber;
        pageSize = pageSize < 1 ? 1 : pageSize;
        pageSize = pageSize > 100 ? 100 : pageSize;

        if (!NullUtils.isNull(sort)) {
            Boolean isAsc = false;
            if (NullUtils.isNull(order)) {
                isAsc = false;
            } else {
                if (Objects.equals(order.toLowerCase(), SORT_BY_ASC.toLowerCase())) {
                    isAsc = true;
                } else if (Objects.equals(order.toLowerCase(), SORT_BY_DESC.toLowerCase())) {
                    isAsc = false;
                }
            }
            newPage = new Page(pageNumber, pageSize);
            newPage.addOrder(isAsc ? OrderItem.asc(camelToUnderline(sort)) : OrderItem.desc(camelToUnderline(sort)));
        } else {
            newPage = new Page<>(pageNumber, pageSize);
        }
        return newPage;
    }

    @ApiModelProperty(value = "防止Mybatis Plus的注入攻击")
    public static void SQLInject(String sqlStr) {
        if (NullUtils.isNull(sqlStr)) {
            return;
        }
        sqlStr = sqlStr.toLowerCase();
        for (int i = 0; i < NO_CAN_USE_WORDS.length; i++) {
            if (sqlStr.contains(NO_CAN_USE_WORDS[i])) {
                throw new MyException(sqlStr + "单词不合法");
            }
        }
    }

    @ApiModelProperty(value = "自定义分页方法")
    public static List listPage(PageVo page, List list) {
        int pageNumber = page.getPageNumber() - 1;
        int pageSize = page.getPageSize();

        pageNumber = pageNumber < 1 ? 1 : pageNumber;
        pageSize = pageSize < 1 ? 1 : pageSize;
        pageSize = pageSize > 100 ? 100 : pageSize;

        int startIndex = pageNumber * pageSize;
        int endIndex = pageNumber * pageSize + pageSize;

        if(startIndex > list.size()){
            return new ArrayList();
        } else if(endIndex > list.size() - 1) {
            return list.subList(startIndex, list.size());
        } else {
            return list.subList(startIndex, endIndex);
        }
    }

    @ApiModelProperty(value = "驼峰转下划线")
    public static String camelToUnderline(String param) {
        if (NullUtils.isNull(param)) {
            return NULL_STR;
        }
        if (param.length() < 2) {
            return param.toLowerCase();
        }
        StringBuffer camelStr = new StringBuffer();
        for (int i = 1; i < param.length(); i++) {
            camelStr.append(Character.isUpperCase(param.charAt(i)) ? CAMEL_STEP_STR + Character.toLowerCase(param.charAt(i)) : param.charAt(i));
        }
        String ans = param.charAt(0) + camelStr.toString();
        return ans.toLowerCase();
    }
}
