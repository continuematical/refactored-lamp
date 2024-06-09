package com.example.demo.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.basics.baseclass.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_log")
@TableName("a_log")
@Schema(name = "日志")
public class Log extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Schema(name = "日志标题")
    private String name;

    @Schema(name = "日志类型")
    private Integer logType;

    @Schema(name = "日志代码")
    private String code;

    @Schema(name = "设备")
    private String device;

    @Schema(name = "请求URL")
    private String requestUrl;

    @Schema(name = "请求方式")
    private String requestType;

    @Column(columnDefinition ="TEXT")
    @Schema(name = "参数")
    private String requestParam;

    @Schema(name = "触发者")
    private String username;

    @Schema(name = "IP地址")
    private String ip;

    @Schema(name = "IP定位")
    private String ipInfo;

    @Schema(name = "消耗毫秒数")
    private Integer costTime;

    @Transient
    @TableField(exist=false)
    @Schema(name = "搜索开始时间")
    private String startDate;

    @Transient
    @TableField(exist=false)
    @Schema(name = "搜索结束时间")
    private String endDate;
}
