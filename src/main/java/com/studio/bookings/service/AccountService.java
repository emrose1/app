package com.studio.bookings.service;

import java.util.List;

import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.studio.bookings.entity.Account;
import com.studio.bookings.util.Constants;

@Api(
	    name = "booking",
	    version = "v1",
	    scopes = {Constants.EMAIL_SCOPE},
	    clientIds = {Constants.WEB_CLIENT_ID},
	    audiences = {Constants.ANDROID_AUDIENCE}
	)

public class AccountService extends BaseService {

	@ApiMethod(name = "calendar.addAccount", path="calendar.addAccount", httpMethod = "post")
	public Account insertAccount(@Named("account") String accountName) {
		Account account = new Account(accountName);
		accountDao.save(account);
		return account;
	}
	
	@ApiMethod(name = "calendar.findAccount", path="calendar.getAccountById", httpMethod = "get")
	public Account findAccount(@Named("account") Long accountId) {
		return accountDao.retrieve(accountId);
	}
	
	@ApiMethod(name = "calendar.listAccounts", path="calendar.listAccounts", httpMethod = "get")
	public List<Account> listAccounts() {
		return accountDao.list();
	}
}
