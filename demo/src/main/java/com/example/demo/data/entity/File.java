package com.example.demo.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.basics.baseclass.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


@Data
@Accessors(chain = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_file")
@TableName("a_file")
@ApiModel(value = "文件")
public class File extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文件名")
    private String name;

    @ApiModelProperty(value = "存储硬盘")
    private int location;

    @ApiModelProperty(value = "文件存储路径")
    private String url;

    @ApiModelProperty(value = "文件大小")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long size;

    @ApiModelProperty(value = "实际文件名")
    private String fKey;

    @ApiModelProperty(value = "文件类型")
    private String type;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "上传人")
    private String nickname;
}
