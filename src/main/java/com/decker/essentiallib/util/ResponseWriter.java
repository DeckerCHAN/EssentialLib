package com.decker.essentiallib.util;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

public class ResponseWriter {
    private HttpServletResponse response;

    public HttpServletResponse getResponse() {
	return response;
    }

    public boolean writeToResponse(String text) {

	try {
	    OutputStream stream = this.response.getOutputStream();
	    stream.write(text.getBytes());
	    return true;
	} catch (Exception e) {
	    e.printStackTrace();
	    return false;
	}

    }

    public boolean writeToResponse(byte [] bytes) {

	try {
	    OutputStream stream = this.response.getOutputStream();
	    stream.write(bytes);
	    return true;
	} catch (Exception e) {
	    e.printStackTrace();
	    return false;
	}

    }

    public ResponseWriter(HttpServletResponse response) {
	super();
	this.response = response;
    }
}
