package com.decker.essentiallib.API;

import com.decker.essentiallib.Page;
import com.decker.essentiallib.PageTitleIcon;
import com.decker.essentiallib.Reactor;

public class PageAPI {
    public static void reigsterPage(String title, String url, PageTitleIcon icon) {
	Reactor.getReactor().addSidelistItem(title, new Page(title, url, icon));
    }

    public static void addPreloadJavascript(String url) {
	Reactor.getReactor().addPreloadJavaScript(url);
    }
}
