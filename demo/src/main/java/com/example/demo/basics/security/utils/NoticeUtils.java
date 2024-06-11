package com.example.demo.basics.security.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "发送消息工具类")
public class NoticeUtils {
    private static final String BASE_URL = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=";

    private static final String USER_ID_ERR = "81013";

    /**
     * 视频消息A类
     */
    @Data
    @AllArgsConstructor
    private static class WeChatNoticeVideoItem {
        private String media_id;
        private String title;
        private String description;
    }

    /**
     * 视频消息B类
     */
    @Data
    @AllArgsConstructor
    private static class ZwzWeiChatNoticeVideo {
        private String touser;
        private String msgtype;
        private String agentid;
        private WeChatNoticeVideoItem video;
        private int safe;
        private int enable_duplicate_check;
    }

    /**
     * 文件消息A类
     */
    @Data
    @AllArgsConstructor
    private static class WeChatNoticeFileItem {
        private String media_id;
    }

    /**
     * 文件消息B类
     */
    @Data
    @AllArgsConstructor
    private static class WeiChatNoticeFile {
        private String touser;
        private String msgtype;
        private String agentid;
        private WeChatNoticeFileItem file;
        private int safe;
        private int enable_duplicate_check;
    }

    /**
     * 图片消息A类
     */
    @Data
    @AllArgsConstructor
    private static class WeChatNoticeImageItem {
        private String media_id;
    }

    /**
     * 图片消息B类
     */
    @Data
    @AllArgsConstructor
    private static class ZwzWeiChatNoticeImage {
        private String touser;
        private String msgtype;
        private String agentid;
        private WeChatNoticeImageItem image;
        private int safe;
        private int enable_duplicate_check;
    }

    /**
     * 普通文本A类
     */
    @Data
    @AllArgsConstructor
    private static class WeChatNoticeInputItem {
        private String content;
    }

    /**
     * 普通文本B类
     */
    @Data
    @AllArgsConstructor
    private static class WeiChatNoticeInput {
        private String touser;
        private String msgtype;
        private String agentid;
        private WeChatNoticeInputItem text;
        private int safe;
        private int enable_duplicate_check;
    }

    /**
     * 图文消息A类
     */
    @Data
    @AllArgsConstructor
    private static class WeChatNoticeTuWenItemValue {
        private String title;
        private String description;
        private String url;
        private String picurl;
    }

    /**
     * 图文消息B类
     */
    @Data
    @AllArgsConstructor
    private static class WeChatNoticeTuWenItem {
        private List<WeChatNoticeTuWenItemValue> articles;
    }

    /**
     * 图文消息C类
     */
    @Data
    @AllArgsConstructor
    private static class WeChatNoticeTuWen {
        private String touser;
        private String msgtype;
        private String agentid;
        private WeChatNoticeTuWenItem news;
        private int safe;
        private int enable_duplicate_check;
    }

    /**
     * Markdown消息A类
     */
    @Data
    @AllArgsConstructor
    private static class WeChatNoticeMarkdownItem {
        private String content;
    }

    /**
     * Markdown消息B类
     */
    @Data
    @AllArgsConstructor
    private static class WeChatNoticeMarkdown {
        private String touser;
        private String msgtype;
        private String agentid;
        private WeChatNoticeMarkdownItem markdown;
        private int safe;
        private int enable_duplicate_check;
    }

    @Operation(summary = "发送图文消息")
    public static String sendTuWenMessage(String userId, String title, String description, String url, String picUrl, String token) {
        List<WeChatNoticeTuWenItemValue> tuwenList = new ArrayList<>();
        tuwenList.add(new WeChatNoticeTuWenItemValue(title, description, url, picUrl));
        WeChatNoticeTuWen file = new WeChatNoticeTuWen(userId, "news", "1000002", new WeChatNoticeTuWenItem(tuwenList), 0, 1);
        String json = JSON.toJSONString(file);
        String s = WeChatUtils.httpsRequest(BASE_URL + token, "POST", json);
        JSONObject ans1 = JSONObject.parseObject(s);
        String errcode = ans1.getString("errcode");
        if(errcode.equals("0")){
            return ans1.getString("msgid");
        } else if(errcode.equals("81013")) {
            return USER_ID_ERR;
        }
        return "FAIL";
    }
}
