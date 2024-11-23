package com.kai.loader;
import java.net.URL;
import java.net.URLClassLoader;

public class easyTraceClassLoader extends URLClassLoader {
    public easyTraceClassLoader(URL[] urls) {
        super(urls, getSystemClassLoader().getParent());
    }
    @Override
    protected synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        final Class<?> loadedC = findLoadedClass(name);
        if (loadedC != null) {
            return loadedC;
        }
        if (name != null && (name.startsWith("sun.") || name.startsWith("java."))) {
            return super.loadClass(name, resolve);
        }
        if (name != null && name.contains("com.kai.spy")) {
            return super.loadClass(name, resolve);
        }
        try {
            Class<?> loadedClass = findClass(name);
            if (loadedClass != null) {
                if (resolve) {
                    resolveClass(loadedClass);
                }
                return loadedClass;
            }
        } catch (ClassNotFoundException ignored) {
        }
        return super.loadClass(name, resolve);
    }
}