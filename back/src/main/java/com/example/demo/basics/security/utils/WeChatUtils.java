package com.example.demo.basics.security.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

@Tag(name = "HTTP工具类")
public class WeChatUtils implements X509TrustManager {

    @Schema(name = "企业ID")
    public static final String CORPID = "";

    @Schema(name = "微信密钥")
    public static final String CORPSECRET = "";

    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }

    /**
     * 企业微信 应用token
     */
    public static String getToken() {
        String s = httpsRequest("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + CORPID + "&corpsecret=" + CORPSECRET, "GET", null);
        JSONObject err = JSON.parseObject(s);
        if (err.getString("errmsg").equals("ok")) {
            return err.getString("access_token");
        }
        return null;
    }

    /**
     * 处理https get/request请求
     */
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        StringBuffer buffer = null;
        try {
            //创建SSLContext
            SSLContext sslContext = SSLContext.getInstance("SSL");
            TrustManager[] tm = {new WeChatUtils()};
            //初始化
            sslContext.init(null, tm, new SecureRandom());
            //获取获取SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(requestMethod);
            //设置当前实例使用的SSLSocketFactory
            conn.setSSLSocketFactory(ssf);
            conn.connect();
            //往服务端写内容
            if (null != outputStr) {
                OutputStream os = conn.getOutputStream();
                os.write(outputStr.getBytes("utf-8"));
                os.close();
            }
            //读取服务器端返回的内容
            InputStream is = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            buffer = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class WeChatDepartment {
        //   部门名称
        private String name;
        //    英文名称
        private String name_en;
        //    父部门id
        private int parentid;
        //   在父部门中的次序值
        private int order;
        //    部门id
        private int id;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class WeChatUser {
        //    成员名称。长度为1~64个utf8字符
        private String name;
        //    成员别名。长度1~32个utf8字符
        private String alias;
        //    手机号码。企业内必须唯一，mobile/email二者不能同时为空
        private String mobile;
        //    用户id
        private String userid;
        //    成员所属部门id列表,不超过100个
        private String[] department;
        //    部门内的排序值，默认为0
        private String[] order;
        //    职务信息
        private String position;
        //    性别。1表示男性，2表示女性
        private String gender;
        //    邮箱
        private String email;
        //    个数必须和参数department的个数一致，
        //    表示在所在的部门内是否为上级。1表示为上级，0表示非上级。
        private String is_leader_in_dept;
        //    主部门
        private String main_department;
        //    批量删除id列表
        private List<String> useridlist;
        //    座机
        private String telephone;
        private ExternalProfile external_profile;
    }

    @Data
    private static class ExternalProfile {
        private ExternalAttr[] external_attr;
    }

    @Data
    private static class ExternalAttr {
        private String type;
        private ExternalText text;
        private String name;
    }

    @Data
    private static class ExternalText {
        private String value;
    }
}
