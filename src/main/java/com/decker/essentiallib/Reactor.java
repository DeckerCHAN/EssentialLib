package com.decker.essentiallib;

import java.util.HashMap;
import java.util.LinkedList;

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

    private LinkedList<String> preloadJavaScriptList;
    
    
    
    private StaticFileReader reader;
    private CommandSwitcher switcher;
    private HashMap<String , Page> sidelist;

    /**
     * Class constructor.
     */
    public Reactor() {
	this.reader = new StaticFileReader("Essential");
	this.switcher = new CommandSwitcher("Essential");
	this.preloadJavaScriptList = new LinkedList<String>();
    }

    public void addSidelistItem(String title,Page page)
    {
	this.sidelist.put(title,page);
    }
    
    public void addPreloadJavaScript(String url) {
	// Add Js url to list when url not already exists in list
	for (String existJavascripteUrl : this.preloadJavaScriptList) {
	    if (url.equals(existJavascripteUrl)) {
		return;
	    }
	}
	// Add Js to url list
	this.preloadJavaScriptList.add(url);
    }
}
