package com.buf.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by andele on 2018-10-12.
 */
public class SpringUtils implements ApplicationContextAware, DisposableBean {
    private static Logger logger = LoggerFactory.getLogger(SpringUtils.class);
    private static ApplicationContext applicationContext = null;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static <T> T getBean(String name) {

        return (T)applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> requiredType) {

        return applicationContext.getBean(requiredType);
    }

    public static void clearHolder() {

        applicationContext = null;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {

        applicationContext = applicationContext;
    }

    public SpringUtils() {
    }

    public void destroy() throws Exception {
        clearHolder();
    }
}
