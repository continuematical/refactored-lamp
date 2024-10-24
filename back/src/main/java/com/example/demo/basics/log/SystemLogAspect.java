package com.example.demo.basics.log;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.alibaba.fastjson2.JSONObject;
import com.example.demo.basics.redis.RedisTemplateHelper;
import com.example.demo.basics.utils.IPInfoUtil;
import com.example.demo.basics.utils.ThreadPoolUtil;
import com.example.demo.data.entity.Log;
import com.example.demo.data.service.LogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Tag(name = "日志实现类")
@Aspect
@Component
@Slf4j
public class SystemLogAspect {
    private static final ThreadLocal<Date> beginTimeThreadLocal = new NamedThreadLocal<>("ThreadLocal beginTime");

    @Autowired
    private LogService logService;

    @Autowired(required = false)
    private HttpServletRequest request;

    @Autowired
    private IPInfoUtil ipInfoUtil;
    @Autowired
    private RedisTemplateHelper redisTemplate;

    private static final String REDIS_USER_PRE = "USER:";

    private static final String LOG_DES_PRE = "description";

    private static final String LOG_TYPE_PRE = "type";

    private static final String LOG_DO_PRE = "doType";

    @Operation(summary = "控制层切点")
    @Pointcut("@annotation(com.example.demo.basics.log.SystemLog)")
    public void controllerAspect() {

    }

    @Operation(summary = "后置通知")
    @AfterReturning("controllerAspect()")
    public void after(JoinPoint joinPoint) {
        try {
            String username = "";
            String description = getControllerMethodInfo(joinPoint).get("description").toString();
            int type = (int) getControllerMethodInfo(joinPoint).get("type");
            String doType = getControllerMethodInfo(joinPoint).get("doType").toString();
            Map<String, String[]> logParams = request.getParameterMap();
            JSONObject logJo = new JSONObject();
            for (Map.Entry<String, String[]> keyInMap : logParams.entrySet()) {
                String keyItemInMap = keyInMap.getKey();
                String paramValue = (keyInMap.getValue() != null && keyInMap.getValue().length > 0 ? keyInMap.getValue()[0] : "");
                logJo.put(keyItemInMap, StrUtil.endWithIgnoreCase(keyInMap.getKey(), "password") ? "" : paramValue);
            }
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(Objects.equals("anonymousUser",principal.toString())){
                return;
            }
            String device = "", isMobile = "PC端";
            UserAgent ua = UserAgentUtil.parse(request.getHeader("user-agent"));
            if (ua != null) {
                if (ua.isMobile()) {
                    isMobile = "移动端";
                }
                device = isMobile + " | " + ua.getBrowser().toString() + " " + ua.getVersion() + " | " + ua.getPlatform().toString() + " " + ua.getOs().toString();
            }
            UserDetails user = (UserDetails) principal;
            String str = redisTemplate.get(REDIS_USER_PRE + user.getUsername());
            if(str != null) {
                username = str;
            } else {
                username = user.getUsername();
            }
            Log log = new Log();
            log.setUsername(username);
            log.setName(description);
            log.setLogType(type);
            log.setCode(doType);
            log.setRequestUrl(request.getRequestURI());
            log.setRequestType(request.getMethod());
            log.setRequestParam(logJo.toJSONString());
            log.setIp(ipInfoUtil.getRequestIpAddress(request));
            log.setDevice(device);
            log.setIpInfo(ipInfoUtil.getIPCity(request));
            Long logElapsedTime = System.currentTimeMillis() - beginTimeThreadLocal.get().getTime();
            log.setCostTime(logElapsedTime.intValue());
            ThreadPoolUtil.getPool().execute(new SaveSystemLogThread(log, logService));
        } catch (Exception e) {
            log.error("日志异常", e);
        }
    }

    //保存日志
    private static class SaveSystemLogThread implements Runnable {
        private Log log;
        private LogService logService;

        public SaveSystemLogThread(Log eslog, LogService logService) {
            this.log = eslog;
            this.logService = logService;
        }

        @Override
        public void run() {
            logService.save(log);
        }
    }

    public static Map<String, Object> getControllerMethodInfo(JoinPoint aopLogPoint) throws ClassNotFoundException {
        Map<String, Object> map = new HashMap<>();
        Method[] methods = Class.forName(aopLogPoint.getTarget().getClass().getName()).getMethods();
        for (Method method : methods) {
            if (!Objects.equals(aopLogPoint.getSignature().getName(), method.getName())) {
                continue;
            }
            Class[] aopLogClass = method.getParameterTypes();
            if (aopLogClass.length != aopLogPoint.getArgs().length) {
                continue;
            }
            map.put(LOG_DO_PRE, method.getAnnotation(SystemLog.class).dotype());
            map.put(LOG_DES_PRE, method.getAnnotation(SystemLog.class).about());
            map.put(LOG_TYPE_PRE, method.getAnnotation(SystemLog.class).type().ordinal());
        }
        return map;
    }
}
