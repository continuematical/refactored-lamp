package com.example.demo.sch.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.basics.baseclass.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import io.swagger.v3.oas.annotations.media.Schema;


@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_teaching_schedule")
@TableName("a_teaching_schedule")
@Schema(name = "排课")
public class TeachingSchedule extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Schema(name = "名称")
    private String title;

    @Schema(name = "X坐标")
    private Integer x;

    @Schema(name = "Y坐标")
    private Integer y;

    @Schema(name = "课程ID")
    private String curId;

    @Schema(name = "课程名称")
    private String curName;

    @Schema(name = "教师姓名")
    private String teacherName;

    @Schema(name = "上课地点")
    private String place;
}
