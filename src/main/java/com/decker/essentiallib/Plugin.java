package com.decker.essentiallib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Plugin extends org.sipc.se.plugin.PluginImpl {

    @Override
    public void getResponse(HttpServletRequest request,
	    HttpServletResponse response) {

	try {

	} catch (Exception e) {

	}
    }

    @Override
    public String getUrl() {
	return "~/Essential";
    }

    @Override
    public boolean onEnable() {
	// TODO:Generate database
	try {
	    return true;
	} catch (Exception e) {
	    return false;
	}

    }

}
