package com.buf.common.utils;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.Random;
import java.util.UUID;

/**
 * 公共UUID
 */
public class UUIDUtils {
    public static String uuid (){
        String uuid = "";
        for (int i = 0; i < 4; i++) {
            uuid = UUID.randomUUID().toString();
            uuid  = uuid.replaceAll("-", "");
        }
        return uuid;
    }


}
