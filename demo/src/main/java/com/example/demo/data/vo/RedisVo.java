package com.example.demo.data.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;

@Tag(name = "缓存VO类")
@Data
@AllArgsConstructor
public class RedisVo {
    @Schema(name = "Redis键")
    private String key;

    @Schema(name = "Redis值")
    private String value;

    @Schema(name = "保存秒数")
    private Long expireTime;
}
