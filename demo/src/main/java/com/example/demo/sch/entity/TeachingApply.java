package com.example.demo.sch.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.basics.baseclass.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


@Data
@Entity
@Table
@DynamicInsert
@DynamicUpdate
@TableName("a_teaching_apply")
@Schema(name = "排课申请")
public class TeachingApply extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Schema(name = "排课ID")
    private String scheduleId;

    @Schema(name = "排课名称")
    private String scheduleName;

    @Schema(name = "申请人ID")
    private String userID;

    @Schema(name = "申请人")
    private String username;

    @Schema(name = "课程ID")
    private String curriculumId;

    @Schema(name = "课程名称")
    private String curriculumName;

    @Schema(name = "排课状态")
    private String status;
}
