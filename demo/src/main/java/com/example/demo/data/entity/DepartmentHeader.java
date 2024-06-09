package com.example.demo.data.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.basics.baseclass.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Accessors(chain = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_department_header")
@TableName("a_department_header")
@Schema(name = "部门负责人")
public class DepartmentHeader extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Schema(name = "用户ID")
    private String userId;

    @Schema(name = "部门ID")
    private String departmentId;

    @Schema(name = "领导类型")
    private Integer type = 0;
}
