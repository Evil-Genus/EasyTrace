package com.kai.agent;
import com.kai.constants.JarPaths;
import com.kai.loader.easyTraceClassLoader;

import com.kai.util.TransformUtil;
import java.io.File;
import java.lang.instrument.ClassFileTransformer;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.net.URL;
import java.util.jar.JarFile;
public class TraceAgent {
    private static ClassLoader collieClassLoader;
    public static void premain(String agentOps, Instrumentation instrumentation) {
       main(agentOps, instrumentation,false);
 //   instrumentation.addTransformer(new CollieClassFileTransformer());
    }
    public static void agentmain(String agentOps, Instrumentation instrumentation) {
        main(agentOps, instrumentation,true);
    }
    private static ClassLoader getClassLoader(Instrumentation inst, File agentCoreFile) throws Throwable {
        return loadOrDefineClassLoader(agentCoreFile);
    }
    private static ClassLoader loadOrDefineClassLoader(File agentCoreFile) throws Throwable {
        if (collieClassLoader == null) {
            collieClassLoader = new easyTraceClassLoader(new URL[] {agentCoreFile.toURI().toURL()});
        }
        return collieClassLoader;
    }
    private static synchronized void main(String agentOps, Instrumentation instrumentation, boolean isAgent) {
        try {
            // 使用启动类加载器load spy
            File agentSpyFile = new File(JarPaths.SPY_JAR_PATH);
            if (!agentSpyFile.exists()) {
                System.out.println("Spy jar file does not exist: " + agentSpyFile);
                return;
            }
            instrumentation.appendToBootstrapClassLoaderSearch(new JarFile(agentSpyFile));
            File agentCoreFile = new File(JarPaths.CORE_JAR_PATH);
            if (!agentCoreFile.exists()) {
                System.out.println("Agent jar file does not exist: " + agentCoreFile);
                return;
            }
            ClassLoader classLoader = getClassLoader(instrumentation, agentCoreFile);
            ClassFileTransformer transformer= TransformUtil.getClassTransformer(classLoader,agentOps);
      //      System.out.println(transformer.getClass().getName());
            if(!isAgent)
            instrumentation.addTransformer(transformer);
            else {
                instrumentation.addTransformer(transformer,true);
                Class<?>[] classs = instrumentation.getAllLoadedClasses();
                for (Class<?> cla : classs) {
                    try {
                        if(instrumentation.isModifiableClass(cla))
                            if(!cla.getName().contains("Lambda"))
                        // 重转换类，重转换类不允许给类添加或移除字段
                        instrumentation.retransformClasses(cla);
                    } catch (UnmodifiableClassException e) {
                        e.printStackTrace();
                    }
                }
                // 完成后可将转换器移除
                instrumentation.removeTransformer(transformer);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
