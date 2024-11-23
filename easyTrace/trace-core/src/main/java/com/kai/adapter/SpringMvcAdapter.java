package com.kai.adapter;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

/**
 * @program: easyTrace
 * @ClassName: SpringMvcAdapter
 * @description:
 * @author: kai
 * @create: 2024-08-06 21:12
 */
public class SpringMvcAdapter {
    public static byte[] modify(byte[] classfileBytes, String className,String spyJarPath) {
        String clazzname = className.replace("/", ".");
        try {
            ClassPool classPool = ClassPool.getDefault();
            CtClass ctClass=classPool.get(clazzname);
            classPool.appendClassPath(spyJarPath);
            CtMethod doExecuteMethod = ctClass.getDeclaredMethod("doDispatch");
            CtMethod renderMethod=ctClass.getDeclaredMethod("processDispatchResult");
            String injectCode=("{com.kai.spy.ContextSpy.setTraceId($1.getHeader(\"traceId\"),$1.getHeader(\"spanId\"));" +
                    "com.kai.spy.ContextSpy.enter(\"Spring\");}");
            String injectResponseCode="com.kai.spy.ContextSpy.exit(\"Spring\");";
            doExecuteMethod.insertBefore(injectCode);
            renderMethod.insertBefore(injectResponseCode);
            ctClass.writeFile("xx");
            return ctClass.toBytecode();
        } catch (Exception e) {
            e.printStackTrace();
            return classfileBytes;
        }
    }
}
