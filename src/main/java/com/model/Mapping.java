package com.model;

import java.lang.reflect.Method;

public class Mapping {
    private Method method;
    private Object object;

    public Mapping(Method method, Object object) {
        this.method = method;
        this.object = object;
    }

    public Method getMethod() {
        return method;
    }

    public Object getObject() {
        return object;
    }
}
