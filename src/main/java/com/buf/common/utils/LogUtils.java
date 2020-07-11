package com.buf.common.utils;

/**
 * Created by andele on 2018-10-12.
 */

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import com.buf.data.entity.SysLog;
import com.buf.sys.entity.Log;
import com.buf.sys.entity.SysUserEntity;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.method.HandlerMethod;


import eu.bitwalker.useragentutils.UserAgent;

/**
 * 日志工具类
 */
public class LogUtils {

    /**
     * 静态内部类，延迟加载，懒汉式，线程安全的单例模式
     */
    private static final class Static {
        //private static LogService logService = SpringUtils.getBean(LogService.class);
        //private static MenuService menuService = SpringUtils.getBean(MenuService.class);
    }

    // 参数名获取工具（尝试获取标注为@ModelAttribute注解的方法，第一个参数名一般为主键名）
    private static ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();

    /**
     * 保存日志
     */
    public static void saveLog(SysUserEntity user, HttpServletRequest request, String logTitle, String logType){
        saveLog(user, request, null, null, logTitle, logType, 0);
    }

    /**
     * 保存日志
     * @param executeTime
     */
    public static void saveLog(SysUserEntity user, HttpServletRequest request, Object handler, Exception ex, String logTitle, String logType, long executeTime){
        if (user == null || user.getUserId()==null || request == null){
            return;
        }
        Log log = new Log();
        log.setLogTitle(logTitle);
        log.setLogType(logType);
        if (StringUtils.isBlank(log.getLogType())){
            String sqlCommandTypes = ObjectUtils.toString(request.getAttribute(SqlCommandType.class.getName()));
            if (StringUtils.containsAny(","+sqlCommandTypes+",", ",INSERT,", ",UPDATE,", ",DELETE,")){
                log.setLogType(Log.TYPE_UPDATE);
            }else if (StringUtils.contains(","+sqlCommandTypes+",", ",SELECT,")){
                log.setLogType(Log.TYPE_SELECT);
            }else{
                log.setLogType(Log.TYPE_ACCESS);
            }
        }
        log.setServerAddr(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort());
        log.setRemoteAddr(IPUtils.getRemoteAddr(request));
        UserAgent userAgent = UserAgentUtils.getUserAgent(request);
        log.setDeviceName(userAgent.getOperatingSystem().getName());
        log.setBrowserName(userAgent.getBrowser().getName());
        log.setUserAgent(request.getHeader("User-Agent"));
        log.setRequestUri(StringUtils.abbr(request.getRequestURI(), 255));
        log.setRequestParams(request.getParameterMap());
        log.setRequestMethod(request.getMethod());

        log.setExecuteTime(executeTime);


        // 获取异常对象
        Throwable throwable = null;
        if (ex != null){
            throwable = ExceptionUtils.getThrowable(request);
        }

        // 异步保存日志
        new SaveLogThread(log, handler, request.getContextPath(), throwable).start();
    }
    /**
     * 保存日志线程
     */
    public static class SaveLogThread extends Thread{

        private Log log;
        private Object handler;
        private String contextPath;
        private Throwable throwable;

        public SaveLogThread(Log log, Object handler, String contextPath, Throwable throwable){
            super(SaveLogThread.class.getSimpleName());
            this.log = log;
            this.handler = handler;
            this.contextPath = contextPath;
            this.throwable = throwable;
        }

        @Override
        public void run() {
            // 获取日志标题
            if (StringUtils.isBlank(log.getLogTitle())){
                String permission = "";
                if (handler instanceof HandlerMethod){
                    HandlerMethod hm = ((HandlerMethod)handler);
                    Method m = hm.getMethod();
                    // 获取权限字符串
                    RequiresPermissions rp = m.getAnnotation(RequiresPermissions.class);
                    permission = (rp != null ? StringUtils.join(rp.value(), ",") : "");


                    // 尝试获取标注为@ModelAttribute注解的方法，第一个参数名一般为主键名
                    if (StringUtils.isBlank(log.getBizKey())){
                        for (Method me : hm.getBeanType().getMethods()){
                            ModelAttribute ma = AnnotationUtils.findAnnotation(me, ModelAttribute.class);
                            if(ma != null){
                                String[] ps = pnd.getParameterNames(me);
                                if(ps != null && ps.length > 0){
                                    log.setBizKey(StringUtils.abbr(log.getRequestParam(ps[0]), 64));
                                    log.setBizType(me.getReturnType().getSimpleName());
                                    break;
                                }
                            }
                        }
                    }
                }
                String href = log.getRequestUri();
                if (StringUtils.startsWith(href, contextPath)){
                    href = StringUtils.substringAfter(href, contextPath);
                }

                //log.setLogTitle(Static.menuService.getMenuNamePath(href, permission));
            }
            if (StringUtils.isBlank(log.getLogTitle())){
                log.setLogTitle("未知操作");
            }
            // 如果有异常，设置异常信息（将异常对象转换为字符串）
            //log.setIsException(throwable != null ? Global.YES : Global.NO);
            log.setExceptionInfo(ExceptionUtils.getStackTraceAsString(throwable));
            // 如果无地址并无异常日志，则不保存信息
            if (StringUtils.isBlank(log.getRequestUri()) && StringUtils.isBlank(log.getExceptionInfo())){
                return;
            }
            // 保存日志信息
            //log.setIsNewRecord(true);
            //Static.logService.insertLog(log);

        }
    }

    /**
     * @Author mawenguang
     * @Description //TODO 插入数据库日志
     * @Date 13:51 2019/5/30
     * @Param [userName, searchTime, operateContext, inputParam, searchType]
     * @return com.buf.data.entity.SysLog
     **/
    public static SysLog insertLog(String userName, String searchTime, String operateContext, String inputParam, String searchType){
        SysLog log = new SysLog();
        log.setOperator(userName);
        log.setSearch_time(searchTime);
        log.setOperate_context(operateContext);
        log.setInput_param(inputParam);
        log.setSearch_type(searchType);
        return log;
    }

}

