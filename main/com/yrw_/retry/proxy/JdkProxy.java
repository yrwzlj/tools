package com.yrw_.retry.proxy;

import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxy {

    @Transactional
    public Object createProxy() {
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), this.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                method.invoke(proxy,args);
                return null;
            }
        });
    }
}
