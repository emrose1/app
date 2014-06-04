package com.studio.bookings.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.users.User;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.Application;
import com.studio.bookings.entity.Person;
import com.studio.bookings.entity.UserSession;
import com.studio.bookings.enums.Permission;
import com.studio.bookings.enums.UserType;
import com.studio.bookings.util.Constants;

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
	
	
	
	@ApiMethod(name = "person.insertPerson", path="account/{account_id}/person",  httpMethod = HttpMethod.POST)
	public Person insertPerson( 
			@Named("username") String username,
			@Named("email") String email,
			@Named("account_id") Long accountId,
			User user) {
		Person p = null;
		//if(user != null) { 
			Account account =  accountDao.retrieve(accountId);
			// if userType == OWNER check to make sure sole owner on account
    		// TODO THROW UNAUTHORIZED EXCEPTION
			//if(personDao.oneFilterAncestorQuery("userId", user.getUserId(), account) == null) {
				p = new Person( account, user.getUserId(), username, email, "ATTENDEE");
				personDao.save(p);
			//}
		//}
		return p; 
	}
	
	@ApiMethod(name = "person.getPerson", path="account/{account_id}/person/{id}",  httpMethod = HttpMethod.GET)
	public Person getPersonInAccount (
			@Named("id") Long personId, 
			@Named("account_id") Long accountId, 
			User user) {
		Person p = null;
		//if(user != null) { 
    		// TODO THROW UNAUTHORIZED EXCEPTION
    		//if (aclService.allowView(accountId, permission.toString(), user).get(0)) {
				Account account = accountDao.retrieve(accountId);
				p = personDao.retrieveAncestor(personId, account);
    		//}
		//}
		return p;
	}
	
	@ApiMethod(name = "person.listPersons", path="account/{account_id}/person",  httpMethod = HttpMethod.GET)
	public List<Person> listPersons (
			@Named("account_id") String accountId,
			User user) {
		List<Person> persons = null;
		//if(user != null) { 
    		// TODO THROW UNAUTHORIZED EXCEPTION
    		//if (aclService.allowViewAll(new Long(accountId), permission.toString(), user).get(0)) {
    			Account accountFetched = accountDao.retrieve(new Long(accountId));
    			persons = personDao.listAncestors(accountFetched);
    		//}
		//}
		return persons;
	}
	
	@ApiMethod(name = "person.updatePerson", path="account/{account_id}/person/{id}", httpMethod = HttpMethod.PUT)
	public Person updatePerson(
			@Named("id") Long personId,  
			@Named("account_id") Long accountId, 
			@Named("username") String username,
			@Named("email") String email,
			User user) {
		Person person = null;
		// userType can't be SUPERADMIN
		//if(user != null) {	
			//if (aclService.allowUpdate(accountId, permission.toString(), user).get(0)) {
				Account accountFetched = accountDao.retrieve(accountId);
				person = personDao.retrieveAncestor(personId, accountFetched);
				person.setUsername(username);
				person.setEmail(email);
				personDao.save(person);
			//}
		//}
		return person;
	}
	
	@ApiMethod(name="person.remove", path="account/{account_id}/person/{id}", httpMethod = HttpMethod.DELETE)
	public void removePerson(
			@Named("id") String personId,
			@Named("account_id") String accountId,
			User user) {
		
		//if(user != null) {	
			//if (aclService.allowDelete(Long.valueOf(accountId), permission.toString(), user).get(0)) {
				Account accountFetched = accountDao.retrieve(Long.valueOf(accountId));
				calendarDao.deleteAncestors(Long.valueOf(personId), accountFetched);
			//}
		//}
	}

	/// UTILITY METHODS
	
	@ApiMethod(name = "person.authorizePerson", path="person.authorizePerson", httpMethod = HttpMethod.POST)
	public Person authorizePerson( 
			@Named("name") String name,
			@Named("email") String email,
			@Named("account") String accountId,
			User user) {
		Person p = null;
		if(user != null) { 
			Account account =  accountDao.retrieve(Long.valueOf(accountId));
			p = personDao.oneFilterAncestorQuery("userId", user.getUserId(), account);
    		// TODO THROW UNAUTHORIZED EXCEPTION
			if(p == null) {
				// MAKE THIS OWNER IF 1st Person on account
				p = new Person( account, user.getUserId(), name, email, "ATTENDEE");
				personDao.save(p);
			}
		}
		return p; 
	}
	
	@ApiMethod(name = "person.getPersonInApplication", path="person.getPersonInApplication")
	public Person getPersonInApplication (
			@Named("person") Long personId,
			@Named("account") Long accountId,
			@Named("user") String userId,
			@Named("application") Long applicationId, 
			User user) {
		Person p = null;
		if(user != null) { 
			
    		// TODO THROW UNAUTHORIZED EXCEPTION
    		if (aclService.allowViewAll(accountId, permission.toString(), user).get(0)) {
    			Application application = applicationDao.retrieve(applicationId);
    			p = personAppDao.twoFilterAncestorQuery("userId", userId, "userType", "OWNER", application);
    		}
		}
		return p;
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
