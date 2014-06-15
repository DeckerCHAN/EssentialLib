package com.decker.essentiallib;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.sipc.se.dao.factory.OperateFactory;

import com.decker.essentiallib.util.ResponseWriter;

public class User {

    private DateTime lastActive;
    private String userName;

    public String getUserName() {
	return userName;
    }

    private UUID identification;

    public UUID getIdentification() {
	return identification;
    }

    private UserType type;

    public UserType getType() {
	return type;
    }

    public void setType(UserType type) {
	this.type = type;
    }

    public User() {
	this.identification = UUID.randomUUID();
	this.lastActive = new DateTime(new Date().getTime());
	// this.type=Arrays.sort( )
	// Use lowest premissiong
	ArrayList<Integer> typeIndexList = new ArrayList<Integer>(Reactor
		.getReactor().getRodTypeSet().keySet());
	this.type = Reactor.getReactor().getRodTypeSet()
		.get(typeIndexList.get(typeIndexList.size() - 1));
    }

    public boolean identify(String userName, String password) {
	String sqlString = String
		.format("SELECT * FROM Essentials.User WHERE Name = '%s' and Password = '%s' ",
			userName, this.convertMD5(password));
	ResultSet resultSet;
	try {
	    resultSet = OperateFactory.getInstance().getDAOInstance()
		    .doQuery(sqlString);
	    if (!resultSet.first()) {

		return false;
	    } else {

		this.userName = resultSet.getString("Name");
		this.type = Reactor.getReactor().getRodTypeSet()
			.get(resultSet.getInt("InRole"));
		return true;
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    return true;
	}

    }

    public boolean identify(HttpServletRequest request) {
	try {
	    if ((request.getParameter("username") != null)
		    && (request.getParameter("password") != null)) {
		return this.identify(request.getParameter("username"),
			request.getParameter("password"));
	    }
	    return false;
	} catch (Exception e) {
	    e.printStackTrace();
	    return false;
	}

    }

    public static boolean indetifyUser(User executor,
	    HttpServletRequest request, HttpServletResponse response) {
	try {

	    boolean successful = executor.identify(request);
	    if (successful) {
		new ResponseWriter(response)
			.writeToResponse("{\"status\":0,\"text\":\"Login Success\"}");

	    } else {
		new ResponseWriter(response)
			.writeToResponse("{\"status\":2,\"text\":\"User name or password invalid\"}");
	    }
	    return true;
	} catch (Exception e) {
	    e.printStackTrace();
	    return true;
	}
    }

    public static boolean receiveUserData(User executor,
	    HttpServletRequest request, HttpServletResponse response) {
	try {
	    if (executor.getType().getUserLevel() != Collections
		    .max(new LinkedList<Integer>(Reactor.getReactor()
			    .getRodTypeSet().keySet()))) {
		new ResponseWriter(response).writeToResponse(String.format(
			"{\"username\":\"%s\",\"category\":\"\"}",
			executor.getUserName()));

	    }

	    return true;
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return false;
    }

    public DateTime getLastActive() {
	return this.lastActive;
    }

    public void refeshLastActive() {
	this.lastActive = new DateTime();
    }

    private String convertMD5(String string) {
	try {
	    MessageDigest digest = MessageDigest.getInstance("MD5");
	    digest.update(string.getBytes(), 0, string.length());
	    return new BigInteger(1, digest.digest()).toString(16)
		    .toUpperCase();

	} catch (NoSuchAlgorithmException e) {
	    e.printStackTrace();
	    return null;
	}
    }

}
