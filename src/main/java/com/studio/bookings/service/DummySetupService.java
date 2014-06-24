package com.studio.bookings.service;

import java.util.ArrayList;
import java.util.List;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.oauth.OAuthService;
import com.google.appengine.api.oauth.OAuthServiceFactory;
import com.google.appengine.api.users.User;
import com.studio.bookings.entity.AccessControlList;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.EventAttribute;
import com.studio.bookings.entity.EventCategory;
import com.studio.bookings.entity.Person;
import com.studio.bookings.enums.Permission;
import com.studio.bookings.util.Constants;

@Api(
    name = "booking",
    version = "v1",
    scopes = {Constants.EMAIL_SCOPE},
    clientIds = {Constants.WEB_CLIENT_ID},
    audiences = {Constants.ANDROID_AUDIENCE}
)

public class DummySetupService  extends BaseService {
	
	public static AccessControlListService aclService = new AccessControlListService();
	Permission permission = Permission.ACCOUNT;
	
	@ApiMethod(name = "account.listDummyAccounts", path="calendar.listDummyAccounts", httpMethod = "get")
	public List<Account> listDummyAccounts() {
		return accountDao.list();
	}
	
	public Account setUpAccount(@Named("person") String accountName) {
		Account account = new Account(accountName);
		accountDao.save(account);
		return account;
	}
	
	public void setUpPerson(
			@Named("username") String username, 
			@Named("userId") String userId, 
			Account account){
	
		List<String> userTypeList = new ArrayList<String>();
		userTypeList.add("SUPERADMIN"); 
		userTypeList.add("ADMIN");
		userTypeList.add("OWNER");
		userTypeList.add("INSTRUCTOR");
		userTypeList.add("ATTENDEE");

		Person p = new Person(account, userId, username, "email", "SUPERADMIN");
		personDao.save(p);
	}
	
	public void setUpCalendar(
			@Named("calendar") String calendarname, 
			Account account) {
		Calendar c = new Calendar (calendarname, account);
		calendarDao.save(c);
	}
	
	public void setUpEventAttribute(
			@Named("attribute") String attributename, 
			Account account) {
		EventAttribute c = new EventAttribute (attributename, account);
		eventAttributeDao.save(c);
	}
	
	public void setUpEventCategory(
			@Named("category") String categoryname, 
			Account account) {
		EventCategory c = new EventCategory (categoryname, account);
		eventCategoryDao.save(c);
	}
	
	public void setUpAcl() {
		if(aclDao.list().size() == 0) { 
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
	}
	
	public User setUpUser() {
		OAuthService oauth = OAuthServiceFactory.getOAuthService();
		User user = null;
		try {
			user = oauth.getCurrentUser();
		} catch (OAuthRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	public void setUp(Account userAccount, User user) {
		AccessControlList acl = new AccessControlList(permission.toString(), "true", "true", "true", "true", "true", "SUPERADMIN");
		aclDao.save(acl);
		Person p = new Person(userAccount, user.getUserId(), "test1", "email", "SUPERADMIN");
		personDao.save(p);
	}
	
	
	@ApiMethod(name = "calendar.dummyUsers", path="calendar.dummyUsers", httpMethod = "get")
	public List<Account> dummyUsers() {
		
		setUpAcl();
	
		// from test
		/*Account userAccount = new Account();
		Long userAccountId = accountDao.save(userAccount);
		User user = this.setUpUser();
		this.setUp(userAccount, user);
		
		Account account1 = new Account("Account 1");
		Account account2 = new Account("Account 2");
				
		List<Account> accountList = new ArrayList<Account>();
		accountList.add(userAccount);
		accountList.add(account1);
		accountList.add(account2);
		
		accountDao.save(accountList);
		//
*/		
		
		List<Account> accountList2 = new ArrayList<Account>();
		List<String> accounts = new ArrayList<String>();
		List<String> persons = new ArrayList<String>();
		List<String> calendars = new ArrayList<String>();
		List<String> attributes = new ArrayList<String>();
		List<String> categories = new ArrayList<String>();
		
		accounts.add("Testing Account1");
		accounts.add("Testing Accounts2");
		accounts.add("Testing Accounts3");
		
		persons.add("Person 1");
		persons.add("Person 2");
		persons.add("Person 3");
		
		calendars.add("Calendar 1");
		calendars.add("Calendar 2");
		calendars.add("Calendar 3");
		
		attributes.add("Attribute 1");
		attributes.add("Attribute 2");
		attributes.add("Attribute 3");
		
		categories.add("Category 1");
		categories.add("Category 2");
		categories.add("Category 3");

		
		
		int index = 0;
		for (String accountName : accounts) {
			if (accountDao.list().size() < 3) {
				Account account = new Account(accountName);
				accountDao.save(account);
				accountList2.add(account);
				for (String personName : persons) {
					setUpPerson(personName + " " +  account, "105854312734748005380", account);
					setUpPerson(personName + " " +  account, "0", account);
				}
				
				for (String calendarName : calendars) {
					setUpCalendar(calendarName, account);
					setUpCalendar(calendarName, account);
				}
				
				for (String attributeName : attributes) {
					setUpEventAttribute(attributeName, account);
					setUpEventAttribute(attributeName, account);
				}
				
				for (String categoryName : attributes) {
					setUpEventCategory(categoryName, account);
					setUpEventCategory(categoryName, account);
				}
			}
		}
		return accountList2;
	}
	
}
