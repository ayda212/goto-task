package com.task.inventory.common;

import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

public class CustomKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        return generateKey(params);
    }
    private String generateKey(Object... params){
        StringBuilder keyBuilder = new StringBuilder();
        for(Object param:params){
            if(param != null ){
                keyBuilder.append(param.toString());
            }
        }
        return keyBuilder.toString();
    }
}
