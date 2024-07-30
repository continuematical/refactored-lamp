package org.example.t197_back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 药品
 */
@TableName("medicine")
public class MedicineEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    public MedicineEntity(){

    }

    public MedicineEntity(T t){

    }

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    @TableField(value = "id")
    private Integer id;

    @TableField(value = "supplier_id")
    private Integer supplier_id;

    /**
     * 药品编号
     */
    @TableField(value = "uuid_number")
    private Integer uuid_number;

    /**
     * 药品名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 药品功效
     */
    @TableField(value = "effect")
    private String effect;

    /**
     * 所治疾病
     */
    @TableField(value = "illness")
    private String illness;

    /**
     * 药品照片
     */
    @TableField(value = "photo")
    private String photo;

    /**
     * 药品类型
     */
    @TableField(value = "types")
    private Integer types;

    /**
     * 药品库存
     */
    @TableField(value = "number")
    private Integer number;

    /**
     * 进价
     */
    @TableField(value = "purchasing_price")
    private Double purchasing_price;

    /**
     * 售价
     */
    @TableField(value = "selling_price")
    private Double selling_price;

    /**
     * 药品介绍
     */
    @TableField(value = "content")
    private String content;

    /**
     * 是否删除
     */
    @TableField(value = "delete")
    private Integer delete;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date create_time;
}
