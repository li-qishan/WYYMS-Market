package com.buf.common.config;

import com.buf.common.utils.CollMapUtils;
import com.buf.common.utils.PropertiesUtils;
import com.buf.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by andele on 2018-10-15.
 */
public class Global {
    public static final String OP_AUTH = "auth";
    private static final Global INSTANCE = new Global();
    public static final String OP_EDIT = "edit";
    public static final String FALSE = "false";
    public static final String USERFILES_BASE_URL = "/userfiles/";
    public static final String TRUE = "true";
    public static final String NO = "0";
    public static final String SHOW = "1";
    private static Logger logger = LoggerFactory.getLogger(Global.class);
    public static final String YES = "1";
    public static final String HIDE = "0";
    private static Map<String, String> props = CollMapUtils.newHashMap();
    public static final String OP_ADD = "add";
    public static final String ROLESYSTEMMANAGER = "systemadmin";

    public Global() {
    }

    public static void clearCache() {
        PropertiesUtils.releadInstance();
        props.clear();
    }

    public static Global getInstance() {
        return INSTANCE;
    }

    public static String getProperty(String key, String defValue) {
        String value = getProperty(key);
        return StringUtils.isBlank(value)?defValue:value;
    }

    public static String getProperty(String key) {
        String value = props.get(key);
        if (value == null){
            value = PropertiesUtils.getInstance().getProperty(key);
            props.put(key, value != null ? value : StringUtils.EMPTY);
        }
        return value;
    }



}
