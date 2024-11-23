package com.kai.aspect;

import com.kai.spy.Context;

/**
 * @program: easyTrace
 * @ClassName: FrameLogAspect
 * @description:
 * @author: kai
 * @create: 2024-08-09 16:03
 */
public class FrameLogAspect implements Frame {
    public void enter(Context context,String frameDescription){
        System.out.println("traceId：" + context.getTraceId());
        System.out.println("spanId：" + context.getSpanId());
        System.out.println("parentId：" + context.getParentId());
        System.out.println("跨进程接收返回值时间：" + System.currentTimeMillis());
        System.out.println("frame："+frameDescription);
    }

    public void exit(Context context,String  frameDescription){
        System.out.println("traceId：" + context.getTraceId());
        System.out.println("spanId：" + context.getSpanId());
        System.out.println("parentId：" + context.getParentId());
        System.out.println("跨进程发送请求时间：" + System.currentTimeMillis());
        System.out.println("frame："+frameDescription);
    }
}
