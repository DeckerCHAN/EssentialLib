package com.decker.essentiallib.util;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class CommandSwitcher {
    public CommandSwitcher(String pluginName) {
	this.CommandSet = new HashMap<String, Object[]>();
    }

    private HashMap<String, Object[]> CommandSet;

    public void registerCommand(String url, String className,
	    String methodName, Object excutor) {
	this.CommandSet.put(url,
		new Object[] { className, methodName, excutor });
    }

    public byte[] processCommand(HttpServletRequest request) throws Exception {
	try {
	    Matcher matcher = Pattern.compile("(?<=aa).*").matcher(
		    request.getRequestURI());
	    if (!matcher.find()) {
		throw new Exception("Can't access command from url");
	    }
	    Object[] pattern = this.CommandSet.get(matcher.group(1));
	    if (pattern == null) {
		throw new Exception("Command not found!");
	    }
	    
	    Object resObject=( Class.forName((String) pattern[0])
		    .getMethod((String) pattern[1], HttpServletRequest.class)
		    .invoke(pattern[2], request));
	    if(resObject instanceof String)
	    {
		((String) resObject).getBytes();
	    }
	    else if (resObject instanceof Byte []) {
		return (byte[]) resObject;
	    }
	    else {
		throw new Exception("Unknow return type");
	    }
	    //return .getBytes();

	} catch (Exception e) {
	    throw new Exception("Failed access url");
	}
	return null;
    }

}
