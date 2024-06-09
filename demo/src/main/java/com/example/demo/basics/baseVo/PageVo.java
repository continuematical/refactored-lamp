package com.example.demo.basics.baseVo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Tag(name = "分页VO类")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(name = "排序名称", description = "排序的字段名")
    private String sort;

    @Schema(name = "页码编号", description = "展示第几页")
    private int pageNumber;

    @Schema(name = "排序类型", description = "升序asc，降序desc")
    private String order;

    @Schema(name = "每页个数", description = "建议设置为15")
    private int pageSize;
}
