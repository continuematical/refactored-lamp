package com.example.demo.data.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.basics.baseclass.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_setting")
@TableName("a_setting")
@Schema(name = "配置")
@NoArgsConstructor
public class Setting extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Schema(name = "设置内容")
    private String value;

    public Setting(String id){
        super.setId(id);
    }
}
