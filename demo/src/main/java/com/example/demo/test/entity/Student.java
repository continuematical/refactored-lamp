package com.example.demo.test.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.basics.baseclass.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@TableName("a_student")
@Schema(name = "学生")
public class Student extends BaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    @Schema(name = "姓名")
    private String name;

    @Schema(name = "性别")
    private String sex;

    @Schema(name = "年龄")
    private BigDecimal age;

    @Schema(name = "学号")
    private String number;

    @Schema(name = "学校")
    private String school;
}
