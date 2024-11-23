package com.kai.spy;

/**
 * @program: easyTrace
 * @ClassName: Context
 * @description:
 * @author: kai
 * @create: 2024-08-02 18:33
 */
public class Context {
    private String traceId;
    private String spanId;
    private String parentId;

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getSpanId() {
        return spanId;
    }

    public void setSpanId(String spanId) {
        this.spanId = spanId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentIdl) {
        this.parentId = parentIdl;
    }

    public Context() { }

    public Context(String traceId) {
        this.traceId = traceId;
    }
}
