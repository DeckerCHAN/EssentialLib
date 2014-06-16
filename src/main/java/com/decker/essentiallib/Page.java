package com.decker.essentiallib;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.decker.essentiallib.util.ResponseWriter;

public class Page {
    public Page(String title, String url, PageTitleIcon icon,
	    Integer[] acceptLevel) {
	this.title = title;
	this.url = url;
	this.icon = icon;
	this.acceptLevel = acceptLevel;
    }

    public String getUrl() {
	return this.url;
    }

    public Integer[] getAcceptLevel() {
	return acceptLevel;
    }

    public String getTitle() {
	return this.title;
    }

    public String getIcon() {
	return this.icon.value;
    }

    private String url;
    private String title;
    private PageTitleIcon icon;
    private Integer[] acceptLevel;

    public static boolean getSeidebar(User execotor,
	    HttpServletRequest request, HttpServletResponse response) {
	try {
	    StringBuilder responseString = new StringBuilder();
	    String templateParent = "[%s]";
	    String childTemplate = "{\"iconName\":\"%s\",\"title\":\"%s\",\"url\":\"%s\"},";
	    for (Page page : Reactor.getReactor().getReactBundle()) {
		for (int accept : page.getAcceptLevel()) {
		    if (execotor.getType().getUserLevel() == accept) {

			responseString
				.append(String.format(childTemplate,
					page.getIcon(), page.getTitle(),
					page.getUrl()));
			break;
		    }
		}
	    }

	    if (responseString.length() > 1) {
		String res = String.format(templateParent, responseString
			.substring(0, responseString.length() - 1));
		new ResponseWriter(response).writeToResponse(res);
	    }

	    return true;
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return false;
	// TODO:return sidebar

    }

    public static boolean getPreloadJS(User execotor,
	    HttpServletRequest request, HttpServletResponse response) {
	try {
	    // System.out.println("Execure preload js");
	    StringBuilder responseString = new StringBuilder();
	    String templete = "<script type='text/javascript' src='%s'></script>";
	    for (String url : Reactor.getReactor()
		    .getNeutronModeratorContainer()) {
		responseString.append(String.format(templete, url));
	    }

	    new ResponseWriter(response).writeToResponse(responseString
		    .toString());
	    return true;
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return false;
    }
}
