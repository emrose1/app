package com.studio.bookings.service;

import java.util.List;

import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.User;
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

public class AccountService extends BaseService {

	@ApiMethod(name = "account.addAccount", path="calendar.addAccount", httpMethod = "post")
	public Account insertAccount(
			@Named("name") String name,
			@Named("description") String description,
			@Named("username") String username, 
			@Named("password") String password, 
			@Named("userType") String userType
			) {
		Account account = new Account(name);
		accountDao.save(account);
		
		Calendar calendar = new Calendar(description, account);
		calendarDao.save(calendar);
		
		User user = new User(username, password, userType, account);
		userDao.save(user);
		
		return account;
	}
	
	@ApiMethod(name = "account.findAccount", path="calendar.findAccount", httpMethod = "get")
	public Account findAccount(@Named("account") Long accountId) {
		return accountDao.retrieve(accountId);
	}
	
	@ApiMethod(name = "account.listAccounts", path="calendar.listAccounts", httpMethod = "get")
	public List<Account> listAccounts() {
		
		//TODO remove
		if (accountDao.list().size() == 0) {
    		LoadDummyData ldd = new LoadDummyData();
    		ldd.initSetup();
    	}
		return accountDao.list();
	}
	
	@ApiMethod(name = "account.updateAccount", path="calendar.updateAccount", httpMethod = "get")
	public Account updateAccount(@Named("account") Long accountId, @Named("name") String name) {
		Account accountFetched = accountDao.retrieve(accountId);
		accountFetched.setName(name);
		accountDao.save(accountFetched);
		return accountFetched;
	}
	
	@ApiMethod(name = "account.deletAccount", path="calendar.deleteAccount", httpMethod = "get")
	public void deleteAccount(@Named("account") Long accountId) {
		accountDao.delete(accountId);
	}
}
