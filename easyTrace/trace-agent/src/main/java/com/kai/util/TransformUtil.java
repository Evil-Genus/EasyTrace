package com.kai.util;
import com.kai.constants.JarPaths;
import java.io.File;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;


/**
 * @program: easyTrace
 * @ClassName: TransformUtil
 * @description:
 * @author: kai
 * @create: 2024-08-13 16:34
 */
public class TransformUtil {
    public static ClassFileTransformer getClassTransformer(ClassLoader classLoader,String agentOps){
        ClassFileTransformer traceTransformer= null;
        try {
            if(CommandHandler.isTrace(agentOps)){
               return  getTransformer(classLoader, JarPaths.TRACE_TRANSFORMER_PATH,null,null);
            }
            if(CommandHandler.isMethod(agentOps)){
                System.out.println("isMethod");
               return getTransformer(classLoader,JarPaths.METHOD_TRANSFORMER_PATH,"com/kaik/UserService","queryUser");}
            if(CommandHandler.isReTransform(agentOps)) {
                String path=CommandHandler.extractClassPath(agentOps);
                File file=new File(path);
                byte[] classBytes= Files.readAllBytes(file.toPath());
                return (loader, className, classBeingRedefined, protectionDomain, classfileBuffer) -> {
             String clazzname = className.replace("/", ".");
             if(clazzname.equals(CommandHandler.extractClassName(agentOps)))
                 return classBytes;
             return classfileBuffer;
         };
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return traceTransformer;
    }
    private static ClassFileTransformer getTransformer(ClassLoader classLoader,String path,String className,String methodName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> transformClass = classLoader.loadClass(path);
        Constructor<?> transform=null;
        if(className==null&&methodName==null){
        transform = transformClass.getDeclaredConstructor(String.class);
        return (ClassFileTransformer) transform.newInstance(JarPaths.SPY_JAR_PATH);
        }
        transform=transformClass.getDeclaredConstructor(String.class,String.class,String.class);
        return (ClassFileTransformer) transform.newInstance(JarPaths.SPY_JAR_PATH,className,methodName);
    }


}
