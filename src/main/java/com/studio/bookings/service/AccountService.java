package com.studio.bookings.service;

import java.util.List;

import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.appengine.api.users.User;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.Person;
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

public class AccountService extends BaseService {

	public static AccessControlListService aclService = new AccessControlListService();
	Permission permission = Permission.ACCOUNT;

	@ApiMethod(name = "account.addAccount", path="calendar.addAccount", httpMethod = "post")
	public Account insertAccount(
			@Named("account") Long userAccountId,
			@Named("name") String name,
			@Named("description") String description,
			@Named("username") String username, 
			@Named("userType") String userType,
			User user) {
		
		Account account = null;
		if (user != null && aclService.allowInsert(userAccountId, permission.toString(), user)) { 
			account = new Account(name);
			accountDao.save(account);
			Calendar calendar = new Calendar(description, account);
			calendarDao.save(calendar);
			Person p = new Person(username, userType, account, user.getUserId());
			personDao.save(p);
		}
		return account;
	}
	
	@ApiMethod(name = "account.findAccount", path="calendar.findAccount", httpMethod = "get")
	public Account findAccount(
			@Named("account") Long userAccountId,
			@Named("accountToFind") Long accountToFindId, 
			User user) {
		Account accountFetched = null;
		if (user != null && aclService.allowView(userAccountId, permission.toString(), user)) {
			accountFetched = accountDao.retrieve(accountToFindId);
		}
		return accountFetched;
	}
	
	@ApiMethod(name = "account.listAccounts", path="calendar.listAccounts", httpMethod = "get")
	public List<Account> listAccounts(@Named("account") Long userAccountId, User user) {
		if (user != null && aclService.allowView(userAccountId, permission.toString(), user)) {
			return accountDao.list();
		} else {
			return null;
		}
	}
	
	@ApiMethod(name = "account.updateAccount", path="calendar.updateAccount", httpMethod = "get")
	public Account updateAccount(
			@Named("account") Long userAccountId,
			@Named("accountToUpdate") Long accountToUpdateId, 
			@Named("name") String name, 
			User user) {
		Account accountFetched = null;
		if (user != null && aclService.allowUpdate(userAccountId, permission.toString(), user)) {
			accountFetched = accountDao.retrieve(accountToUpdateId);
			accountFetched.setName(name);
			accountDao.save(accountFetched);
		}
		return accountFetched;
	}
	
	@ApiMethod(name = "account.deleteAccount", path="calendar.deleteAccount", httpMethod = "get")
	public void deleteAccount(
			@Named("account") Long userAccountId, 
			@Named("accountDelete") Long accountToDeleteId, 
			User user) {
		if (user != null && aclService.allowDelete(userAccountId, permission.toString(), user)) {
			accountDao.delete(accountToDeleteId);
		}
	}
}
