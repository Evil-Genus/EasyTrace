package com.kai.adapter.restTemplate;

import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtMethod;

/**
 * @program: easyTrace
 * @ClassName: RestTemplateRequestAdapter
 * @description:
 * @author: kai
 * @create: 2024-08-02 23:11
 */
public class RestTemplateRequestAdapter {
    public static byte[] modify(byte[] classfileBytes, String className,String spyJarPath) {
        String clazzname = className.replace("/", ".");
        try {
            ClassPool classPool = ClassPool.getDefault();
            CtClass ctClass=classPool.get(clazzname);
            classPool.appendClassPath(spyJarPath);
            CtMethod doExecuteMethod = ctClass.getDeclaredMethod("createRequest");
            String injectCode="{$_.getHeaders().add(\"traceId\",com.kai.spy.ContextSpy.getTraceId());" +
                    "$_.getHeaders().add(\"spanId\",com.kai.spy.ContextSpy.getSpanId());}";
               //     "com.kai.spy.ContextSpy.exit(\"RestTemplate\");}";
            doExecuteMethod.insertAfter(injectCode);
            ctClass.writeFile("xx");
            return ctClass.toBytecode();
        } catch (Exception e) {
            e.printStackTrace();
            return classfileBytes;
        }
    }
}
