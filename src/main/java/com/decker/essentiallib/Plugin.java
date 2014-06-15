package com.decker.essentiallib;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.decker.essentiallib.util.StaticFileReader;

public class Plugin extends org.sipc.se.plugin.PluginImpl {

    @Override
    public void getResponse(HttpServletRequest request,
	    HttpServletResponse response) {
	//OutputStream stream;
	try {
	     Reactor.getReactor().riseRod(request,response);
	  //  stream = response.getOutputStream();
	  //  stream.write(new StaticFileReader("Essentiallib")
	//    .readBytes(request.getRequestURI()));
	        
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    @Override
    public String getUrl() {
	return "~/Essentiallib";
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
