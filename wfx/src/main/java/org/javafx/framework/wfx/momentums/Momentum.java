package org.javafx.framework.wfx.momentums;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.javafx.framework.wfx.controllers.PackControlled;

public enum Momentum {
	AFTER_LOAD("afterLoad"), 
	BEFORE_LOAD("beforeLoad"), 
	AFTER_SHOW("afterShow"), 
	BEFORE_SHOW("beforeShow"), 
	AFTER_UNSHOW("afterUnshow"), 
	BEFORE_UNSHOW("beforeUnshow"),
	AFTER_UNLOAD("afterUnload"),
	BEFORE_UNLOAD("beforeUnload");
	
	private String methodName;
	
	private Momentum(String methodName) {
		this.methodName = methodName;
	}
	
	public String getMethodName() {
		return methodName;
	}
	
	public static Momentum searchForMomentum(Method method) { 
		method.setAccessible(true);
		
		for (Annotation annotation : method.getAnnotations()) {
			if (annotation instanceof WFXM) { 
				return checkNameForMomentum(method.getName());
			}
		}
		
		return null;
	}
	
	public static void invokeMomentum(PackControlled target, Momentum momentum) {
		try {
			Method invoking = target.getClass().getDeclaredMethod(momentum.getMethodName());
			invoking.setAccessible(true);
			invoking.invoke(target);
		} catch(Exception e) {
			throw new RuntimeException("An error occured while invoking " + WFXM.class.getName() + " annotatated methods. - " + e.getMessage());
		}
	}
	
	private static Momentum checkNameForMomentum(String methodName) {
		for (Momentum momentum : Momentum.values()) {
			if (momentum.getMethodName().equals(methodName)) {
				return momentum;
			}
		}
		
		return null;
	}
}
