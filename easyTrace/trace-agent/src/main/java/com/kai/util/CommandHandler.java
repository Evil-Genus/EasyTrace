package com.kai.util;

/**
 * @program: easyTrace
 * @ClassName: CommandHandler
 * @description:
 * @author: kai
 * @create: 2024-08-13 15:43
 */
public class CommandHandler {
    public static boolean isTrace(String command){
        return true;
    }
    public static boolean isReTransform(String command){
        return false;
    }
    public static boolean isMethod(String command){
        return true;
    }
    public static String extractClassPath(String command){
        return "";
    }
    public static String extractClassName(String command){
        return "";
    }
}
