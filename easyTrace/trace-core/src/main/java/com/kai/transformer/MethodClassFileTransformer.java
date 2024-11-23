package com.kai.transformer;
import com.kai.Impl.SpyMethodImpl;
import com.kai.adapter.ModifyClassAdapter;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @program: easyTrace
 * @ClassName: MethodClassFileTransformer
 * @description:
 * @author: kai
 * @create: 2024-08-13 14:56
 */
public class MethodClassFileTransformer implements ClassFileTransformer {
    private String targetClassName;
    private String targetMethodName;
    private final String spyJarPath;
    private final static ConcurrentMap<String, Object> MODIFY_CLASS_MAP = new ConcurrentHashMap<>();
    public MethodClassFileTransformer(String spyJarPath,String className,String methodName) {
        this.spyJarPath = spyJarPath;
        this.targetClassName=className;
        this.targetMethodName=methodName;
        SpyMethodImpl.init();
    }
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBytes) {

        if(className.equals(targetClassName))
      return ModifyClassAdapter.modify(classfileBytes,targetClassName,targetMethodName,spyJarPath);
        return classfileBytes;
    }
}
