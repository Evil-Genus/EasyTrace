package com.kai.aspect;


public interface Aspect {

     void before(String className, String methodName,
                        String descriptor, Object[] params);

     void error(String className, String methodName,
                      String descriptor, Throwable throwable);

     void after(String className, String methodName,
                       String descriptor, Object returnValue);

}
