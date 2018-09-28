package com.guye.utils;

import java.util.Properties;

/**
 * Created by SHANGQIAN on 2017/9/14.
 */
public class SystemTools {
    private static Properties props=System.getProperties();
    private static String DB_INIT_SIZE="2";

    public static boolean isWindows(){
       String os= props.getProperty("os.name");
       if (os!=null&&os.contains("Windows")){
           return true;
       }
       return false;
    }

    public static String getDbInitSize(){
        return  DB_INIT_SIZE;
    }

}
