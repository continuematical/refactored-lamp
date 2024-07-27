package com.example.demo.basics.baseVo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Tag(name = "临时用户类")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(name = "用户名")
    private String username;

    @Schema(name = "拥有的菜单权限")
    private List<String> permissions;

    @Schema(name = "是否自动登录")
    private Boolean saveLogin;
}
