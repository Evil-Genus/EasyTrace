package com.kai.model;

/**
 * @program: easyTrace
 * @ClassName: LogRecord
 * @description:
 * @author: kai
 * @create: 2024-08-07 18:50
 */
public class LogRecord extends Record {

    private String className;
    private String methodName;
    private String descriptor;
    private boolean error = false;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}