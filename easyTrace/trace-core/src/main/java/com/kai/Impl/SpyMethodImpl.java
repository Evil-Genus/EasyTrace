package com.kai.Impl;

import com.kai.aspect.Aspect;
import com.kai.aspect.CallLogAspect;
import com.kai.aspect.Frame;
import com.kai.aspect.FrameLogAspect;
import com.kai.spy.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: easyTrace
 * @ClassName: SpyMethodImpl
 * @description:
 * @author: kai
 * @create: 2024-07-28 17:51
 */
public class SpyMethodImpl {
    public static List<Aspect> aspectList;
    public static List<Frame> frameList;
   public  static void init(){
        aspectList=new ArrayList<>();
        frameList=new ArrayList<>();
        frameList.add(new FrameLogAspect());
        aspectList.add(new CallLogAspect());
       System.out.println("init");
       try {
           InvokeSpy.doBefore=SpyMethodImpl.class.getMethod("before",String.class,String.class,String.class,Object[].class);
           InvokeSpy.doAfter=SpyMethodImpl.class.getMethod("after",Object.class,String.class,String.class,String.class);
           InvokeSpy.doError=SpyMethodImpl.class.getMethod("error",Throwable.class,String.class,String.class,String.class);
           ContextSpy.doEnter=SpyMethodImpl.class.getMethod("enter",Context.class,String.class);
           ContextSpy.doExit=SpyMethodImpl.class.getMethod("exit",Context.class,String.class);
       } catch (NoSuchMethodException e) {
           e.printStackTrace();
       }
   }
    public static void before(String className, String methodName, String descriptor, Object[] params){
        for (Aspect aspect : aspectList) {

            aspect.before(className,methodName,descriptor,params);
        }
    }
    public static void after(Object returnValue, String className, String methodName, String descriptor){
        for (Aspect aspect : aspectList) {

            aspect.after(className,methodName,descriptor,returnValue);
        }
    }
    public static void error(Throwable e, String className, String methodName, String descriptor){
        for (Aspect aspect : aspectList) {

            aspect.error(className,methodName,descriptor,e);
        }
    }
    public static  void enter(Context context,String description){
        for (Frame frame : frameList) {
            frame.enter(context,description);
        }
    }
    public static  void exit(Context context,String description){
        for (Frame frame : frameList) {
            frame.exit(context,description);
        }
    }

}
