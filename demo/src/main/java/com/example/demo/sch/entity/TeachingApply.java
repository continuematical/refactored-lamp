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
@Table
@DynamicInsert
@DynamicUpdate
@TableName("a_teaching_apply")
@ApiModel(value = "排课申请")
public class TeachingApply extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "排课ID")
    private String scheduleId;

    @ApiModelProperty(value = "排课名称")
    private String scheduleName;

    @ApiModelProperty(value = "申请人ID")
    private String userID;

    @ApiModelProperty(value = "申请人")
    private String username;

    @ApiModelProperty(value = "课程ID")
    private String curriculumId;

    @ApiModelProperty(value = "课程名称")
    private String curriculumName;

    @ApiModelProperty(value = "排课状态")
    private String status;
}
