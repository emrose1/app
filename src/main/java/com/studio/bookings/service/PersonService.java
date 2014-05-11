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
import com.studio.bookings.entity.AccessControlList;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.Person;
import com.studio.bookings.entity.UserSession;
import com.studio.bookings.enums.Permission;
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

public class PersonService extends BaseService {
	
	public static AccessControlListService aclService = new AccessControlListService();
	static Permission permission = Permission.USER;
	
	@ApiMethod(name = "calendar.addPerson", path="calendar.addPerson", httpMethod = "post")
	public Person insertPerson( 
			@Named("username") String username,
			@Named("email") String email,
			@Named("familyname") String family_name,
			@Named("givenname") String given_name,
			@Named("userType") String userType,
			@Named("account") Long accountId,
			User user) {
		Person p = null;
		if(user != null) { 
			Account account =  accountDao.retrieve(accountId);
    		// TODO THROW UNAUTHORIZED EXCEPTION
			if(personDao.oneFilterAncestorQuery("userId", user.getUserId(), account) == null) {
				p = new Person( account, user.getUserId(), username, email, family_name, given_name, userType);
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
    		if (aclService.allowView(accountId, permission.toString(), user).get(0)) {
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
    		if (aclService.allowViewAll(accountId, permission.toString(), user).get(0)) {
    			Account accountFetched = accountDao.retrieve(accountId);
    			persons = personDao.listAncestors(accountFetched);
    		}
		}
		return persons;
	}
	
	
	@ApiMethod(name = "calendar.updatePerson", path="calendar.updatePerson", httpMethod = "post")
	public Person updatePerson(
			@Named("person") Long personId,  
			@Named("account") Long accountId, 
			@Named("username") String username,
			@Named("email") String email,
			@Named("familyname") String family_name,
			@Named("givenname") String given_name,
			@Named("userType") String userType, 
			User user) {
		Person person = null;
		if(user != null) {	
			if (aclService.allowUpdate(accountId, permission.toString(), user).get(0)) {
				Account accountFetched = accountDao.retrieve(accountId);
				person = personDao.retrieveAncestor(personId, accountFetched);
				person.setUsername(username);
				person.setEmail(email);
				person.setFamilyName(family_name);
				person.setGivenName(given_name);
				person.setUserType(UserType.valueOf(userType));
				personDao.save(person);
			}
		}
		return person;
	}
	
	@ApiMethod(name = "calendar.deletePerson", path="calendar.deletePerson", httpMethod = "post")
	public void deletePersons(
			@Named("person") List<Long> personIds,
			@Named("account") Long accountId,
			User user) {
		
		if(user != null) {	
			if (aclService.allowDelete(accountId, permission.toString(), user).get(0)) {
				Account accountFetched = accountDao.retrieve(accountId);
				calendarDao.deleteAncestors(personIds, accountFetched);
			}
		}
	}

	
	/// UTILITY METHODS
	
	public Account setUpAccount(@Named("person") String accountName) {
		Account account = new Account(accountName);
		accountDao.save(account);
		return account;
	}
	
	public void setUpPerson(
			@Named("username") String username, 
			@Named("user") Integer userId, 
			Account account){
	
		List<String> userTypeList = new ArrayList<String>();
		userTypeList.add("SUPERADMIN"); 
		userTypeList.add("ADMIN");
		userTypeList.add("OWNER");
		userTypeList.add("INSTRUCTOR");
		userTypeList.add("ATTENDEE");

		Person p = new Person(account, userId.toString(), username, "email", 
	    		"family_name", "given_name", userTypeList.get(0));
		personDao.save(p);
		
	}
	
	public void setUpAcl() {
		
		List<String> permissionList = new ArrayList<String>();
		permissionList.add("ACCOUNT"); 
		permissionList.add("CALENDAR");
		permissionList.add("EVENT");
		permissionList.add("BOOKING");
		permissionList.add("USER");
		permissionList.add("ACL");
	
		List<String> userTypeList = new ArrayList<String>();
		userTypeList.add("SUPERADMIN"); 
		userTypeList.add("ADMIN");
		userTypeList.add("OWNER");
		userTypeList.add("INSTRUCTOR");
		userTypeList.add("ATTENDEE");

		
		for (String p : permissionList) {
			for (String ut : userTypeList) {
				AccessControlList acl = new AccessControlList(p, "true", "true", "true", "true", "true", ut);
				aclDao.save(acl);
			}
		}
	}
	
	@ApiMethod(name = "calendar.dummyUsers", path="calendar.dummyUsers", httpMethod = "get")
	public List<Account> dummyUsers() {
		
		setUpAcl();
		
		List<Account> accountList = new ArrayList<Account>();
		List<String> accounts = new ArrayList<String>();
		List<String> persons = new ArrayList<String>();
		
		accounts.add("Testing Account1");
		accounts.add("Testing Accounts2");
		accounts.add("Testing Accounts3");
		
		persons.add("Person 1");
		persons.add("Person 2");
		persons.add("Person 3");
		
		int index = 0;
		for (String accountName : accounts) {
			Account account = setUpAccount(accountName);
			accountList.add(account);
			for (String personName : persons) {
				setUpPerson(personName, index++, account);
			}
		}
		return accountList;
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
