package com.example.demo.basics.utils;

import io.swagger.annotations.ApiOperation;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

@ApiOperation(value = "公共工具类")
public class CommonUtil {
    // 用于生成加密数据随机数
    private static SecureRandom random = new SecureRandom();

    @ApiOperation(value = "生成随机文件名称")
    public static String renamePic(String filename) {
        return UUID.randomUUID().toString().replace("-", "")
                + filename.substring(filename.lastIndexOf("."));
    }

    @ApiOperation(value = "生成随机七位验证码")
    public static String getRandomTwoNum() {
        int num = random.nextInt(99);
        //不足六位前面补零
        String str = String.format("%02d", num);
        return str;
    }

    @ApiOperation(value = "随即删除DFS死循环")
    public static Boolean judgeIds(String tempString, String[] list) {
        boolean flag = true;
        for (String id : list) {
            if (Objects.equals(tempString, id)) {
                flag = false;
                break;
            }
        }
        return !flag;
    }

    @ApiOperation(value = "随机生成六位验证码")
    public static String getRandomNum() {
        Random random = new Random();
        int num = random.nextInt(999999);
        return String.format("%06d", num);
    }
}
