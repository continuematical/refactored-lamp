package com.example.demo.test.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.basics.baseclass.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_teacher")
@TableName("a_teacher")
@Schema(name = "教师")
public class Teacher extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(name = "姓名")
    private String name;

    @Schema(name = "学历")
    private String education;

    @Schema(name = "年龄")
    private BigDecimal age;

    @Schema(name = "毕业院校")
    private String graduated;

    @Schema(name = "工资")
    private BigDecimal wages;

    @Schema(name = "在职状态")
    private String status;

    @Schema(name = "签名")
    private String autograph;

    @Schema(name = "个人简历")
    private String resume;

    @Schema(name = "备注")
    private String remark;
}
