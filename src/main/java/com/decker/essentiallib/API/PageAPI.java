package com.decker.essentiallib.API;

import com.decker.essentiallib.Page;
import com.decker.essentiallib.PageTitleIcon;
import com.decker.essentiallib.Reactor;

public class PageAPI {
    public static void reigsterPage(String title, String url,
	    PageTitleIcon icon, Integer[] acceptLevels) {
	Reactor.getReactor().addFuelRod(
		new Page(title, url, icon, acceptLevels));
    }

    public static void addPreloadJavascript(String url) {
	Reactor.getReactor().injectNeutronModerator(url);
    }
}
