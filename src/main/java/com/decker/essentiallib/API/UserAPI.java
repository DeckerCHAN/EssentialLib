package com.decker.essentiallib.API;

import java.util.Collections;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.decker.essentiallib.Reactor;
import com.decker.essentiallib.UserType;

public class UserAPI {

    public static void registerUserType(String typeName, int level) {
	Reactor.getReactor().getRodTypeSet()
		.put(level, new UserType(typeName, level));
    }

    public static UserType getUserType(HttpServletRequest request) {

	for (Cookie cookie : request.getCookies()) {
	    if ((cookie.getName().equals("identification"))
		    && Reactor.getReactor().getRodSet()
			    .get(UUID.fromString(cookie.getValue())) != null) {
		return Reactor.getReactor().getRodSet()
			.get(UUID.fromString(cookie.getValue())).getType();
	    }
	}

	return Reactor
		.getReactor()
		.getRodTypeSet()
		.get(Collections.max(Reactor.getReactor().getRodTypeSet()
			.keySet()));

    }
}
