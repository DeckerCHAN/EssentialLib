package com.decker.essentiallib;

public class UserType {
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    private String typeName;
    private int userLevel;

    public UserType(String typeName, int userLevel) {
	super();
	this.typeName = typeName;
	this.userLevel = userLevel;
    }
}
