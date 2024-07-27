package com.example.demo.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.basics.baseclass.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "文件")
public class File extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Schema(name = "文件名")
    private String name;

    @Schema(name = "存储硬盘")
    private int location;

    @Schema(name = "文件存储路径")
    private String url;

    @Schema(name = "文件大小")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long size;

    @Schema(name = "实际文件名")
    private String fKey;

    @Schema(name = "文件类型")
    private String type;

    @Transient
    @TableField(exist=false)
    @Schema(name = "上传人")
    private String nickname;
}
