package com.decker.essentiallib.util;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.decker.essentiallib.User;

public class CommandSwitcher {
    private String pluginName;

    public CommandSwitcher(String pluginName) {
	this.CommandSet = new HashMap<String, Command>();
	this.pluginName = pluginName;
    }

    private HashMap<String, Command> CommandSet;

    public void registerCommand(String url, String className,
	    String methodName, int accessLevel) {
	this.registerCommand(new Command(className, url, methodName,
		accessLevel));
    }

    public void registerCommand(Command command) {
	this.CommandSet.put(command.getUrl(), command);
    }

    public boolean processCommand(User executor, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {
	try {
	    Matcher matcher = Pattern.compile(
		    String.format("(?<=%s/).*exec", this.pluginName)).matcher(
		    request.getRequestURI());
	    // Search command name from url
	    if (!matcher.find()) {
		return false;
	    }
	    // Find command in the command set
	    Command command = this.CommandSet.get(matcher.group(0));
	    if (command == null) {
		throw new Exception("Command not found!");
	    }
	    // Identify executor permission level
	    // Block every user try to over their own permission
	    // That won't happen as normal
	    if (command.getAccessPermissionLevel() < executor.getType()
		    .getUserLevel()) {
		throw new Exception("You don't have permission to do this!");
	    }
	    // TODO:use this to execure funtion may cause bug.
	    boolean resObject = (Boolean) (Class
		    .forName(command.getClassName())
		    .getMethod(command.getMethodName(), User.class,
			    HttpServletRequest.class, HttpServletResponse.class)
		    .invoke(this, executor, request, response));
	    return resObject;
	    /*
	     * if (resObject instanceof String) { ((String)
	     * resObject).getBytes(); } else if (resObject instanceof Byte[]) {
	     * return (HttpServletRequest) resObject; } else { throw new
	     * Exception("Unknow return type"); }
	     */

	} catch (Exception e) {
	    e.printStackTrace();
	    throw new Exception("Failed access url");
	}
    }

}
