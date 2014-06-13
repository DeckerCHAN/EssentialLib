package com.decker.essentiallib;

public class Page {
    public Page(String title, String url, PageTitleIcon icon,int pageLevel) {
	this.title = title;
	this.url = url;
	this.icon = icon;
	this.pageLevel=pageLevel;
    }

    public String getUrl() {
	return this.url;
    }

    public String getTitle() {
	return this.title;
    }

    public PageTitleIcon getIcon() {
	return this.icon;
    }
    
    
    private String url;
    private String title;
    private PageTitleIcon icon;
    private int pageLevel;
}
