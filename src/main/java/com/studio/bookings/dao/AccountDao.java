package com.studio.bookings.dao;

import static com.studio.bookings.util.OfyService.ofy;

import java.security.acl.Owner;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.ObjectifyService;
import com.studio.bookings.entity.Account;

public class AccountDao {

	static{
		ObjectifyService.register(Account.class);
		
	}

	public Key<Account> save(Account account) { 
		Key<Account> accountKey = ofy().save().entity(account).now();
		return accountKey;
	}
	
	public Account getAccountById(Long accountId) {
		return ofy().load().type(Account.class).id(accountId).now();
	}

	
	public Account getAccountByKey(Key<Account> accountKey) throws NotFoundException {
		Account account = ofy().load().key(accountKey).safe();
		return account;
	}
		
	public List<Account> findAll() {
		List<Account> accounts = ofy().load().type(Account.class).list();
		return accounts;
	}

}