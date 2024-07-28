package org.example.t197_back.entity.model;

import java.io.Serializable;

/**
 * 进货
 * 接受传参的实体类
 */
public class StockModel implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private int id;

    /**
     * 进货编号
     */
    private String UuidNumber;

    /**
     * 药品
     */
    private Integer medicineId;
}
