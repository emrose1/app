package com.studio.bookings.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.User;
import com.studio.bookings.entity.UserSession;
import com.studio.bookings.enums.UserType;
import com.studio.bookings.util.Constants;
import com.studio.bookings.util.LoadDummyData;

@Api(
	    name = "booking",
	    version = "v1",
	    scopes = {Constants.EMAIL_SCOPE},
	    clientIds = {Constants.WEB_CLIENT_ID},
	    audiences = {Constants.ANDROID_AUDIENCE}
	)

public class UserService extends BaseService {
	
	@ApiMethod(name = "calendar.getUser", path="calendar.getUser", httpMethod = "get")
	public List<User> getUser(HttpServletRequest req) {
		List<User> stringList = new ArrayList<User>();
		HttpSession session = req.getSession(false);
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		User user = userSession.getUser();
		stringList.add(user);
	    return stringList;
	}
	
	@ApiMethod(name = "calendar.getUserSession", path="calendar.getUserSession", httpMethod = "get")
	public List<UserSession> getUserSession(HttpServletRequest req) {
		List<UserSession> stringList = new ArrayList<UserSession>();
		HttpSession session = req.getSession(false);
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		stringList.add(userSession);
	    return stringList;
	}
	
	//TODO return permission role
	// to test var message = {'email' : 'admin', 'password': 'password'}; console.log(message); 
	// gapi.client.booking.calendar.authUserSession(message).execute(function(resp) { console.log(resp);});
	
	@ApiMethod(name = "calendar.authUserSession", path="calendar.authUserSession", httpMethod = "post")
	public List<UserSession> authUserSession(
			@Named("email") String email, 
			@Named("password") String password, 
			@Named("account") Long accountId,
			HttpServletRequest request) {
		
		//TODO remove
		if (userDao.list().size() == 0) {
    		LoadDummyData ldd = new LoadDummyData();
    		ldd.initSetup();
    	}
		Account accountFetched = accountDao.retrieve(accountId);
		User user = userDao.doubleQuery("email", email, "password", password, accountFetched.getKey());
		List<UserSession> stringList = new ArrayList<UserSession>();
		UserSession userSession = new UserSession();
		
		if(user != null) {
            userSession.setUser(user);
            userSession.setLoginTime(new Date());
            request.getSession(true).setAttribute("userSession", userSession);

        } else {
            request.setAttribute("message", "Invalid username or password");
        }
		stringList.add(userSession);
	    return stringList;
	}
}
