package com.decker.essentiallib;

import java.util.HashMap;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;

import com.decker.essentiallib.util.CommandSwitcher;
import com.decker.essentiallib.util.StaticFileReader;

public class Reactor {
    private static Reactor instance;

    public static Reactor getReactor() {
	if (instance == null) {
	    instance = new Reactor();
	}
	return instance;

    }

    private LinkedList<String> neutronModeratorContainer;
    private StaticFileReader reader;
    private HashMap<String, Page> rodBundle;
    private CommandSwitcher rodSwitcher;

    /**
     * Class constructor.
     */
    public Reactor() {
	this.reader = new StaticFileReader("Essential");
	this.rodSwitcher = new CommandSwitcher("Essential");
	this.neutronModeratorContainer = new LinkedList<String>();
    }
    
    public byte [] riseRod(HttpServletRequest request)
    {
	return null;
    }
    public void injectNeutronModerator(String url) {
	// Add Js url to list when url not already exists in list
	for (String existModerator : this.neutronModeratorContainer) {
	    if (url.equals(existModerator)) {
		return;
	    }
	}
	// Add Js to url list
	this.neutronModeratorContainer.add(url);
    }

    public void addFuelRod(String title, Page page) {
	this.rodBundle.put(title, page);
    }
}
