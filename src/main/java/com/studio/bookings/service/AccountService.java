package com.studio.bookings.service;

import java.util.List;

import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.googlecode.objectify.Key;
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

	@ApiMethod(name = "account.addAccount", path="calendar.addAccount", httpMethod = "post")
	public Account insertAccount(@Named("name") String name) {
		Account account = new Account(name);
		accountDao.save(account);
		return account;
	}
	
	@ApiMethod(name = "account.findAccount", path="calendar.findAccount", httpMethod = "get")
	public Account findAccount(@Named("account") Long accountId) {
		return accountDao.retrieve(accountId);
	}
	
	@ApiMethod(name = "account.listAccounts", path="calendar.listAccounts", httpMethod = "get")
	public List<Account> listAccounts() {
		return accountDao.list();
	}
	
	@ApiMethod(name = "account.updateAccount", path="calendar.updateAccount", httpMethod = "get")
	public Long updateAccount(@Named("account") Long accountId, @Named("name") String name) {
		Account accountFetched = accountDao.retrieve(accountId);
		accountFetched.setName(name);
		return accountDao.save(accountFetched);
	}
	
	@ApiMethod(name = "account.deletAccount", path="calendar.deleteAccount", httpMethod = "get")
	public void deleteAccount(@Named("account") Long accountId) {
		accountDao.delete(accountId);
	}
}
