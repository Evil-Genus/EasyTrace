package com.kai.aspect;
import com.alibaba.fastjson.JSON;
public class CallLogAspect implements Aspect{

    // 方法执行之前调用的埋点方法
    public  void before(String className, String methodName,String descriptor, Object[] params) {
        System.out.println("方法开始执行时间：" + System.currentTimeMillis());
        System.out.println("类名：" + className);
        System.out.println("方法名：" + methodName);
        System.out.println("方法描述符：" + descriptor);
       System.out.println("参数：" + params!=null?checkSerialize(params):"参数为空");
    }
    // 方法执行异常时调用的埋点方法
    public void error(String className, String methodName,
                                 String descriptor, Throwable throwable) {
        System.out.println("方法执行出现异常时间："
+ System.currentTimeMillis());
        System.out.println("类名：" + className);
        System.out.println("方法名：" + methodName);
        System.out.println("方法描述符：" + descriptor);
        System.out.println("异常信息：" + throwable.getMessage());
}
    // 方法返回之前调用的埋点方法
    public  void after(String className, String methodName,
                             String descriptor, Object returnValue) {

        System.out.println("方法执行完成时间：" + System.currentTimeMillis());
        System.out.println("类名：" + className);
        System.out.println("方法名：" + methodName);
        System.out.println("方法描述符：" + descriptor);
        System.out.println("返回值：" + returnValue!=null?checkSerialize(new Object[]{returnValue}):"返回值为空");
    }
    /**
     * 检查参数/返回值是否可以序列化
    * @param params
     */
    public String checkSerialize(Object[] params){
        if(params==null||params.length==0)
            return "参数为空" ;
        for(int i=0;i<params.length;i++){
            if(params[i]!=null)
            if(params[i].getClass().getSimpleName().contains("HeaderWriterRequest")||params[i].getClass().getSimpleName().contains("HeaderWriterResponse"))
                return "存在不可序列化参数" ;
        }
        return JSON.toJSONString(params);
    }
}