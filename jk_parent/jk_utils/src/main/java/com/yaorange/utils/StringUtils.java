package com.yaorange.utils;

public class StringUtils {
    //字符串判断是否空，计算机中空null和""都应该是空的表现
    //String自带的isEmpty只能判断长度
    public static Boolean isEmpty(String str){
        if(str == null){
            return true;
        }else if(str.trim().isEmpty()){
            return true;
        }
        return false;
    }
}
