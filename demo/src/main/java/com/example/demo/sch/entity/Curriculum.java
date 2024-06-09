package com.example.demo.sch.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.basics.baseclass.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_curriculum")
@TableName("a_curriculum")
@ApiModel(value = "课程")
public class Curriculum extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程介绍")
    private String content;

    @ApiModelProperty(value = "课程状态")
    private String status;

    @ApiModelProperty(value = "备注")
    private String remark;
}
