package com.kai.adapter;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import static com.kai.util.JavassistUtils.*;
/**
 * @program: Trace
 * @ClassName: ModifyClassAdapter
 * @description:
 * @author: kai
 * @create: 2024-07-25 17:07
 */
public class ModifyClassAdapter {

    public static byte[] modify(byte[] classfileBytes, String targetClassName, String targetMethodName, String spyJarPath) {
        try {
            System.out.println("demo");
            ClassPool classPool = ClassPool.getDefault();
            // 必须要有这个，否则会报point找不到，搞了大半天 https://my.oschina.net/xiaominmin/blog/3153685
               classPool.appendClassPath(spyJarPath);
            //   String clazzname = className.replace("/", ".");
            String clazzname = targetClassName.replace("/", ".");
            System.out.println(targetClassName);
            CtClass ctClass = classPool.get(clazzname);

            // 排除掉注解，接口，枚举
            if (!ctClass.isAnnotation() && !ctClass.isInterface() && !ctClass.isEnum()) {
                // 针对所有函数操作
                for (CtBehavior ctBehavior : ctClass.getDeclaredMethods()) {
                    if(ctBehavior.getName().equals(targetMethodName))
                    addMethodAspect(clazzname, ctBehavior, false);
                }
                // 所有构造函数
                // for (CtBehavior ctBehavior : ctClass.getDeclaredConstructors()) {
                //     addMethodAspect(clazzname, ctBehavior, true);
                // }
                ctClass.writeFile(
                        "xx");
            }

            return ctClass.toBytecode();
        } catch (Exception e) {
            e.printStackTrace();
            return classfileBytes;
        }
    }

    private static void addMethodAspect(String clazzname, CtBehavior ctBehavior, boolean isConstructor)
            throws Exception {
        if (isNative(ctBehavior)
                //  ||isStatic(ctBehavior)
                || isAbstract(ctBehavior)
                //   || is
                || "main".equals(ctBehavior.getName())
                || "toString".equals(ctBehavior.getName())
                || "getClass".equals(ctBehavior.getName())
                || "equals".equals(ctBehavior.getName())
                || "hashCode".equals(ctBehavior.getName())) {
            return;
        }
        // 方法前增强
        // 如果是基本数据类型的话，传参为Object是不对的，需要转成封装类型
        // 转成封装类型的话非常方便，使用$w就可以，不影响其他的Object类型
        String methodName = isConstructor ? ctBehavior.getName() + "#" : ctBehavior.getName();
        String methodInfo = methodName;
        String target = isStatic(ctBehavior) ? "null" : "this";
//        ctBehavior.addLocalVariable("startTime",CtClass.longType);
        String format = String.format("System.out.println(\"methodName=%s\");\n", "($w)$args");
        ctBehavior.insertBefore(

                String.format("{com.kai.spy.InvokeSpy.before(\"%s\", \"%s\", \"%s\",($w)$args);}",
                        clazzname, methodName,methodInfo)
        );
        // 方法后增强
        ctBehavior.insertAfter(
                String.format("{com.kai.spy.InvokeSpy.after(($w)$_, \"%s\", \"%s\",\"%s\");}",
                        clazzname, methodName,methodInfo)
        );
//        // 异常出增强
        ctBehavior.addCatch(
                String.format("{com.kai.spy.InvokeSpy.error(\"%s\", \"%s\", \"%s\",%s);"
                                + "throw $e;}",
                        clazzname, methodName,methodInfo,"$e"),
                ClassPool.getDefault().get("java.lang.Throwable")
        );

    }
}

