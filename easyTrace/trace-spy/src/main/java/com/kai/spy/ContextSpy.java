package com.kai.spy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @program: easyTrace
 * @ClassName: ContextSpy
 * @description:
 * @author: kai
 * @create: 2024-08-01 16:39
 */
public class ContextSpy {
    public static  Method doEnter;
    public static Method doExit;
    private final static ThreadLocal<Context> TRACE_DATA=new ThreadLocal<>();
    private final static String DEFAULT_SPAN_ID="0";
    private final static String DEFAULT_PARENT_ID="-1";
    public static void enter(String frameDescription) throws InvocationTargetException, IllegalAccessException {
        Context context = TRACE_DATA.get();
        if(doEnter!=null&&context!=null)
            doEnter.invoke(null,context,frameDescription);
        else System.out.println("Invoke trace error");
    }
    public static void exit(String frameDescription) throws InvocationTargetException, IllegalAccessException {
        Context context = TRACE_DATA.get();
        if(doExit!=null&&context!=null)
            doExit.invoke(null,context,frameDescription);
        else System.out.println("Invoke trace error");
    }
    private static  Context generateContext(){
        String tid=UUID.randomUUID().toString();
        Context context=new Context(tid);
        context.setSpanId(DEFAULT_SPAN_ID);
        context.setParentId(DEFAULT_PARENT_ID);
        TRACE_DATA.set(context);
        return context;
    }
    public static Context getContext(){
        Context context=null;
        if(TRACE_DATA.get()!=null)
            context=TRACE_DATA.get();
        if(context==null||context.getTraceId().trim().length()==0){
          context=generateContext();
        }
        return context;
    }
    public static String getTraceId(){
        return getContext().getTraceId();
    }
    public static String getSpanId(){
        return getContext().getSpanId();
    }
    public static void setTraceId(String traceId,String spanId){
        Context context=null;
        if(traceId==null||traceId.trim().length()==0||spanId==null||spanId.trim().length()==0){
            context=generateContext();
        }
        else {
            context = new Context();
            context.setTraceId(traceId);
            context.setSpanId(UUID.randomUUID().toString().replace("-", "").substring(0, 16));
            context.setParentId(spanId);
        }
        TRACE_DATA.set(context);
    }
}
