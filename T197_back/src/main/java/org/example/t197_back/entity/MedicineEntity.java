package org.example.t197_back.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 药品
 */
public class MedicineEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableField(value = "id")
    private int id;

    /**
     * 药品编号
     */
    private int uuid_number;

    /**
     * 药品名称
     */
    private String name;

    private String effect;

    private String illness;

    private String photo;

    private Integer types;

    private Integer number;

    private Double purchasing_price;

    private Double selling_price;

    private String content;

    private Integer delete;

    private Date create_time;
}
