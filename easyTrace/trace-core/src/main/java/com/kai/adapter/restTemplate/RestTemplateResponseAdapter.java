package com.kai.adapter.restTemplate;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

/**
 * @program: easyTrace
 * @ClassName: RestTemplateResponseAdapter
 * @description:
 * @author: kai
 * @create: 2024-08-10 17:02
 */
public class RestTemplateResponseAdapter {
    public static byte[] modify(byte[] classfileBytes, String className,String spyJarPath) {
        String clazzname = className.replace("/", ".");
        try {
            ClassPool classPool = ClassPool.getDefault();
            CtClass ctClass=classPool.get(clazzname);
            classPool.appendClassPath(spyJarPath);
            CtMethod doExecuteMethod = ctClass.getDeclaredMethod("execute");
            String injectCode= "com.kai.spy.ContextSpy.enter(\"RestTemplate\");";
            doExecuteMethod.insertAfter(injectCode);
            ctClass.writeFile("xx");
            return ctClass.toBytecode();
        } catch (Exception e) {
            e.printStackTrace();
            return classfileBytes;
        }
    }

}
