package com.example.demo.data.utils;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Objects;

@Tag(name = "判断为空工具类")
public class NullUtils {
    public static boolean isNull(String str) {
        if (str == null || Objects.equals("", str) || Objects.equals("null", str)) {
            return true;
        }
        return false;
    }
}
