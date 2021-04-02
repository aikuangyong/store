package com.store.utils;

public class StringUtils  extends org.apache.commons.lang.StringUtils {
    public static String valueOf(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }

}