package com.kai.spy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/**
 * @program: easyTrace
 * @ClassName: InvokeSpy
 * @description:
 * @author: kai
 * @create: 2024-07-27 16:21
 */
public  class InvokeSpy {
    public static Method doBefore;
    public static Method doAfter;
    public static Method doError;
    public static void before(String className, String methodName, String descriptor, Object[] params) {
        if (doBefore != null) {
            try {
                if(doBefore!=null)
                doBefore.invoke(null,className, methodName, descriptor, params);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
    public static void after(Object returnValue, String className, String methodName, String descriptor) {
        if (doAfter!= null) {
            try {
                doAfter.invoke( null,returnValue, className, methodName, descriptor);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
    public static void error(String className, String methodName, String descriptor,Throwable returnValue ) {
        if (doError!= null) {
            try {
                doError.invoke(null, returnValue, className, methodName, descriptor);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }



}
