package com.example.demo.sch.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.basics.baseclass.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_teaching_schedule")
@TableName("a_teaching_schedule")
@ApiModel
public class TeachingSchedule extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    private String title;

    @ApiModelProperty(value = "X坐标")
    private Integer x;

    @ApiModelProperty(value = "Y坐标")
    private Integer y;

    @ApiModelProperty(value = "课程ID")
    private String curID;

    @ApiModelProperty(value = "课程名称")
    private String curName;

    @ApiModelProperty(value = "教师姓名")
    private String teacherName;
}
