package com.example.demo.data.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import java.math.BigDecimal;

@Schema(name = "图表VO类")
@Data
public class AntvVo {
    private String title;
    private String type;
    private BigDecimal value;
}
