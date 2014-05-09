package com.studio.bookings.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.appengine.api.users.User;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.Person;
import com.studio.bookings.entity.UserSession;
import com.studio.bookings.enums.Permission;
import com.studio.bookings.util.Constants;
import com.studio.bookings.util.LoadDummyData;

@Api(
	    name = "booking",
	    version = "v1",
	    scopes = {Constants.EMAIL_SCOPE},
	    clientIds = {Constants.WEB_CLIENT_ID},
	    audiences = {Constants.ANDROID_AUDIENCE}
	)

public class PersonService extends BaseService {
	
	public static AccessControlListService aclService = new AccessControlListService();
	static Permission permission = Permission.USER;
	
	// TODO add Update and Delete
	
	@ApiMethod(name = "calendar.addPerson", path="calendar.addPerson", httpMethod = "post")
	public Person insertPerson( 
			@Named("username") String username,
			@Named("userType") String userType,  
			@Named("userId") String userId, 
			@Named("account") Long accountId,
			User user) {
		Person p = null;
		if(user != null) { 
    		// TODO THROW UNAUTHORIZED EXCEPTION
			
			// check if person with user_id already exists for this account, if not add
    		if (aclService.allowInsert(accountId, permission.toString(), user)) {  // <- this won't work as user/person doesn't exist yet
				Account account =  accountDao.retrieve(accountId);
				p = new Person(username, userType, account, userId);
				personDao.save(p);
    		}
		}
		return p; 
	}
	
	@ApiMethod(name = "calendar.findPerson", path="calendar.findPerson", httpMethod = "post")
	public Person findPerson(
			@Named("person") Long personId, 
			@Named("account") Long accountId, 
			User user) {
		Person p = null;
		if(user != null) { 
    		// TODO THROW UNAUTHORIZED EXCEPTION
    		if (aclService.allowView(accountId, permission.toString(), user)) {
				Account account = accountDao.retrieve(accountId);
				p = personDao.retrieveAncestor(personId, account);
    		}
		}
		return p;
	}

	
	@ApiMethod(name = "calendar.listPersons", path="calendar.listPersons", httpMethod = "get")
	public List<Person> listPersons (
			@Named("account") Long accountId,
			User user) {
		List<Person> persons = null;
		if(user != null) { 
    		// TODO THROW UNAUTHORIZED EXCEPTION
    		if (aclService.allowViewAll(accountId, permission.toString(), user)) {
    			Account accountFetched = accountDao.retrieve(accountId);
    			persons = personDao.listAncestors(accountFetched);
    		}
		}
		return persons;
	}

	
	/// UTILITY METHODS
	
	@ApiMethod(name = "calendar.dummyUsers", path="calendar.dummyUsers", httpMethod = "get")
	public List<Person> dummyUsers() {
		if (personDao.list().size() == 0) {
    		LoadDummyData ldd = new LoadDummyData();
    		ldd.initSetup();
    	}
		return personDao.list();
	}
	
	private List<UserSession> getUserSession(HttpServletRequest req) {
		List<UserSession> stringList = new ArrayList<UserSession>();
		HttpSession session = req.getSession(false);
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		stringList.add(userSession);
	    return stringList;
	}
	
	private Person getUser(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		Person user = userSession.getUser();
		return user;
	}
	
	private void setSession(Person user, HttpServletRequest req) {
		
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
