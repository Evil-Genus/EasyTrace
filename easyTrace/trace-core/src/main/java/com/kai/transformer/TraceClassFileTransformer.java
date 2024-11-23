package com.kai.transformer;

import com.kai.Impl.SpyMethodImpl;
import com.kai.adapter.ModifyClassAdapter;
import com.kai.adapter.restTemplate.RestTemplateRequestAdapter;
import com.kai.adapter.SpringMvcAdapter;
import com.kai.adapter.restTemplate.RestTemplateResponseAdapter;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
public class TraceClassFileTransformer implements ClassFileTransformer {
    private final String spyJarPath;
    public TraceClassFileTransformer(String spyJarPath) {
        this.spyJarPath = spyJarPath;
        SpyMethodImpl.init();
    }
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBytes) {
        if(className.equals("org/springframework/http/client/support/HttpAccessor")){
            System.out.println("rest enter");
            return RestTemplateRequestAdapter.modify(classfileBytes,className,spyJarPath);
        }
        //        if(className.equals("org/springframework/http/client/AbstractClientHttpRequest")){
//            return RestTemplateResponseAdapter.modify(classfileBytes,className,spyJarPath);
//        }
        if(className.equals("org/springframework/web/servlet/DispatcherServlet"))
            return SpringMvcAdapter.modify(classfileBytes,className,spyJarPath);
        return classfileBytes;
    }
}