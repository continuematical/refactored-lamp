package com.example.demo.data.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Tag(name = "角色VO类")
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    @Schema(name = "角色名称")
    private String name;

    @Schema(name = "角色ID")
    private String id;

    @Schema(name = "角色备注")
    private String description;
}
