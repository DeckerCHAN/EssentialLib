package com.decker.essentiallib.API;

import com.decker.essentiallib.Page;
import com.decker.essentiallib.PageTitleIcon;
import com.decker.essentiallib.Reactor;

public class PageAPI {
    public static void reigsterPage(String title, String url, PageTitleIcon icon,int pageLevel) {
	Reactor.getReactor().addFuelRod(title, new Page(title, url, icon,pageLevel));
    }

    public static void addPreloadJavascript(String url) {
	Reactor.getReactor().injectNeutronModerator(url);
    }
}
