package com.decker.essentiallib.util;

public class Command {
    private Integer accessPermissionLevel;
    private String className;
    private String methodName;
    private String url;

    public Command(String className, String url, String methodName,
	    Integer accessPermissionLevel) {
	super();
	this.className = className;
	this.url = url;
	this.methodName = methodName;
	this.accessPermissionLevel = accessPermissionLevel;
    }

    public Integer getAccessPermissionLevel() {
	return accessPermissionLevel;
    }

    public String getClassName() {
	return className;
    }

    public String getMethodName() {
	return methodName;
    }

    public String getUrl() {
	return url;
    }
}
