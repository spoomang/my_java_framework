package com.san.mapping;

import java.lang.reflect.Method;

/**
 * @author sandeeppoomanglaath
 *
 */
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
