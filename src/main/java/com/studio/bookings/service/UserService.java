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
	
	
	@ApiMethod(name = "calendar.addUser", path="calendar.addUser", httpMethod = "post")
	public User insertUser( 
			@Named("username") String username,
			@Named("password") String password,
			@Named("userType") String userType,  
			@Named("account") Long accountId) {
		
		Long oId = new Long(accountId);
		Account account =  accountDao.retrieve(oId);
		User user = new User(username, password, userType, account);
		userDao.save(user);
	    return user; 
	}
	
	@ApiMethod(name = "calendar.findUser", path="calendar.findUser", httpMethod = "post")
	public User findUser(@Named("user") Long userId, @Named("account") Long accountId) {
		Account account = accountDao.retrieve(accountId);
		return userDao.retrieveAncestor(userId, account);
	}
	
	@ApiMethod(name = "calendar.listUsers", path="calendar.listUsers", httpMethod = "get")
	public List<User> listUsers(@Named("account") Long accountId) {
		Account accountFetched = accountDao.retrieve(accountId);
		return userDao.listAncestors(accountFetched);
	}
	
	
	@ApiMethod(name = "calendar.getUserSession", path="calendar.getUserSession", httpMethod = "get")
	public List<UserSession> getUserSession(HttpServletRequest req) {
		List<UserSession> stringList = new ArrayList<UserSession>();
		HttpSession session = req.getSession(false);
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		stringList.add(userSession);
	    return stringList;
	}
	
	@ApiMethod(name = "calendar.authUserSession", path="calendar.authUserSession", httpMethod = "post")
	public User authUserSession(
			@Named("username") String username, 
			@Named("password") String password, 
			@Named("account") Long accountId) {
		
		Account accountFetched = accountDao.retrieve(accountId);
		User user = userDao.doubleFilterAncestorQuery("username", username, "password", password, accountFetched);
		
	    return user;
	}
	
	
	/// UTILITY METHODS
	
	@ApiMethod(name = "calendar.dummyUsers", path="calendar.dummyUsers", httpMethod = "get")
	public List<User> dummyUsers() {
		if (userDao.list().size() == 0) {
    		LoadDummyData ldd = new LoadDummyData();
    		ldd.initSetup();
    	}
		return userDao.list();
	}
	
	private User getUser(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		User user = userSession.getUser();
		return user;
	}
	
	private void setSession(User user, HttpServletRequest req) {
		
		UserSession userSession = new UserSession();
		if(user != null) {
            userSession.setUser(user);
            userSession.setLoginTime(new Date());
            req.getSession(true).setAttribute("userSession", userSession);
        } else {
            req.setAttribute("message", "Invalid username or password");
        }
	}
	
}
