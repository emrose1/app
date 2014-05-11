package com.studio.bookings.service;

import java.util.ArrayList;
import java.util.List;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.studio.bookings.entity.AccessControlList;
import com.studio.bookings.entity.Account;
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

public class DummyLoginService  extends BaseService {
	
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
	
}
