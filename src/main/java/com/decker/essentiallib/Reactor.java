package com.decker.essentiallib;

import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.sipc.se.dao.factory.OperateFactory;

import com.decker.essentiallib.util.CommandSwitcher;
import com.decker.essentiallib.util.StaticFileReader;

public final class Reactor {
    private static Reactor instance;

    public static Reactor getReactor() {
	if (instance == null) {
	    instance = new Reactor();
	}
	return instance;

    }

    private LinkedList<String> neutronModeratorContainer;

    public LinkedList<String> getNeutronModeratorContainer() {
	return neutronModeratorContainer;
    }

    private HashMap<String, Page> reactBundle;
    private StaticFileReader resourceLocator;
    private HashMap<UUID, User> rodSet;
    private CommandSwitcher rodSwitcher;
    private HashMap<Integer, UserType> rodTypeSet;
    private Thread wasteCollect;

    /**
     * Class constructor.
     */
    public Reactor() {
	// Initialize
	this.resourceLocator = new StaticFileReader("Essentiallib");
	this.rodSwitcher = new CommandSwitcher("Essentiallib");
	this.reactBundle = new HashMap<String, Page>();
	this.neutronModeratorContainer = new LinkedList<String>();
	this.rodSet = new HashMap<UUID, User>();
	this.rodTypeSet = new HashMap<Integer, UserType>();
	this.wasteCollect = new Thread(new Runnable() {

	    public void run() {
		wasteCollectWork();

	    }
	});
	this.wasteCollect.start();
	// Generate Usertype list from database
	try {
	    String sqlString = String.format("SELECT * FROM Essentials.Role");
	    ResultSet roleResultSet = OperateFactory.getInstance()
		    .getDAOInstance().doQuery(sqlString);
	    while (roleResultSet.next()) {
		this.rodTypeSet.put(roleResultSet.getInt("RoleID"),
			new UserType(roleResultSet.getString("RoleName"),
				roleResultSet.getInt("RoleID")));

	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}
	// Add fixed command
	this.rodSwitcher.registerCommand("Sidebar.exec", Page.class.getName(),
		"getSeidebar", 3);
	this.rodSwitcher.registerCommand("Login.exec", User.class.getName(),
		"indetifyUser", 3);
	this.rodSwitcher.registerCommand("ReceiveUserData.exec",
		User.class.getName(), "receiveUserData", 3);
	this.rodSwitcher.registerCommand("PreloadJs.exec",
		Page.class.getName(), "getPreloadJS", 3);
	// Add fixed page
	this.reactBundle.put("Home", new Page("Home",
		"./Template/Homepage.html", PageTitleIcon.HOME, new Integer[] {
			0, 1, 2, 3 }));
	// Add fixed preloadJS
	this.neutronModeratorContainer
		.add("http://code.highcharts.com/highcharts.js");
    }

    public HashMap<String, Page> getReactBundle() {
	return reactBundle;
    }

    public void addFuelRod(String title, Page page) {
	this.reactBundle.put(title, page);
    }

    public boolean addNewRodType(int rodLevel, String rodTypeName) {
	try {
	    if (this.rodTypeSet.get(rodLevel) != null) {
		return false;
	    }
	    this.rodTypeSet.put(rodLevel, new UserType(rodTypeName, rodLevel));
	    return true;

	} catch (Exception e) {
	    e.printStackTrace();
	    return false;
	}

    }

    public HashMap<Integer, UserType> getRodTypeSet() {
	return rodTypeSet;
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

    public void riseRod(HttpServletRequest request, HttpServletResponse response)
	    throws Exception {
	System.out.println("A10");
	User currentRod = null;
	if (request.getCookies() != null) {
	    for (Cookie cookie : request.getCookies()) {
		if (cookie.getName().equals("identification")) {
		    if (this.rodSet.get(UUID.fromString(cookie.getValue())) != null) {
			currentRod = this.rodSet.get(UUID.fromString(cookie
				.getValue()));
		    }

		}
	    }
	}

	// If unrecognized uer have the request
	// generate new user and save to rot set
	if (currentRod == null) {
	    currentRod = new User();
	    this.rodSet.put(currentRod.getIdentification(), currentRod);
	    Cookie cookie = new Cookie("identification",
		    String.valueOf(currentRod.getIdentification()));
	    response.addCookie(cookie);
	}
	currentRod.refeshLastActive();
	// Try to execute command
	boolean executeCommandResult = false;
	try {
	    executeCommandResult = this.rodSwitcher.processCommand(currentRod,
		    request, response);
	} catch (Exception e) {
	    e.printStackTrace();
	    return;
	}
	// If no command found then read file
	if (!executeCommandResult) {
	    OutputStream stream;
	    try {
		stream = response.getOutputStream();
		stream.write(this.resourceLocator.readBytes(request
			.getRequestURI()));
	    } catch (Exception e) {
		response.setStatus(400);
	    }
	}

    }

    private void wasteCollectWork() {

	while (true) {
	    try {
		Thread.sleep(60000);
		DateTime current = new DateTime(new Date().getTime());
		LinkedList<UUID> depletedRod = new LinkedList<UUID>();
		for (User rod : this.rodSet.values()) {
		    if (rod.getLastActive().getMillis() < current.minusMinutes(
			    5).getMillis()) {
			depletedRod.add(rod.getIdentification());
		    }
		}
		for (UUID rodid : depletedRod) {
		    this.rodSet.remove(rodid);
		}
	    } catch (IllegalArgumentException e) {

		e.printStackTrace();
	    } catch (InterruptedException e) {
		return;
	    }
	}

	// Recollect expere user

    }

}
