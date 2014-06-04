package com.studio.bookings.service;

import static com.studio.bookings.util.TestObjectifyService.ofy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.oauth.OAuthService;
import com.google.appengine.api.oauth.OAuthServiceFactory;
import com.google.appengine.api.users.User;
import com.studio.bookings.entity.AccessControlList;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.Person;
import com.studio.bookings.enums.Permission;
import com.studio.bookings.util.TestBase;


public class AccountServiceTest extends TestBase {
	
	static Permission permission = Permission.ACCOUNT;

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
		
	@Test
	public void insertAccount() {
		
		Account userAccount = new Account();
		accountDao.save(userAccount);
		
		User user = this.setUpUser();
		this.setUp(userAccount, user);
		
		Account account = accountService.insertAccount("Testing Account", user);
		Account accountFetched = accountDao.retrieve(account.getId());
				
		/*Calendar calendarFetched = calendarDao.listAncestors(account).get(0);
		assert "Testing Calendar".equals(calendarFetched.getDescription());
		assert account.getId().equals(calendarFetched.getAccount().getId());
		
		Person usersFetched = personDao.oneFilterAncestorQuery("userId", user.getUserId() , account);
		
		assert "username".equals(usersFetched.getUsername());
		assert account.getId().equals(usersFetched.getAccount().getId());*/
		
		assert account.getId().equals(accountFetched.getId());
		assert account.getName().equals(accountFetched.getName());
		Assert.assertNotNull(account.getAccountSettings().getRepeatEventFinalDate());
		
		Date finalRepeatDate = new Date();
		accountFetched.getAccountSettings().setRepeatEventFinalDate(finalRepeatDate);
		account.getAccountSettings().setRepeatEventFinalDate(finalRepeatDate);
		
		assert account.getAccountSettings().getRepeatEventFinalDate()
			.compareTo(accountFetched.getAccountSettings().getRepeatEventFinalDate()) == 0;
	}
	
	@Test
	public void getAccount() {
		
		Account userAccount = new Account();
		Long userAccountId = accountDao.save(userAccount);
		User user = this.setUpUser();
		this.setUp(userAccount, user);
		
		Account account1 = new Account("Account 1");
		Account account2 = new Account("Account 2");

		accountDao.save(account1);
		accountDao.save(account2);

		Account accountFetched1 = accountService.getAccount(account1.getId(), user);
		Account accountFetched2 = accountService.getAccount(account2.getId(), user);
		
		assert accountFetched1.getId().equals(account1.getId());
		assert accountFetched1.getName().equals(account1.getName());
		
		assert accountFetched2.getId().equals(account2.getId());
		assert accountFetched2.getName().equals(account2.getName());
		
		Assert.assertNotEquals(accountFetched1.getId(), accountFetched2.getId());
		Assert.assertNotEquals(accountFetched1.getName(), accountFetched2.getName());
	}
	
	@Test
	public void ListAccounts() {
		
		Account userAccount = new Account();
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
		List<Account> accountsFetched = accountService.listAccounts(user);
		
		Assert.assertNotNull(accountsFetched);
		assert accountsFetched.size() == accountList.size();
		assert accountsFetched.size() == 3;
	}
	
	@Test
	public void updateAccount() {
		
		Account userAccount = new Account();
		Long userAccountId = accountDao.save(userAccount);
		User user = this.setUpUser();
		this.setUp(userAccount, user);
		
		Account account = new Account("Account 1");
		accountDao.save(account);
		
		Account accountUpdated = accountService.updateAccount(account.getId().toString(), "updated Account", user);
		assert account.getName().equals("updated Account");
		assert account.getName().equals(accountUpdated.getName());
		assert account.getId().equals(accountUpdated.getId());
	}
	
	@Test
	public void removeAccount() {
		
		Account userAccount = new Account();
		Long userAccountId = accountDao.save(userAccount);
		User user = this.setUpUser();
		this.setUp(userAccount, user);
		
		Account account = new Account("Account 1");
		accountDao.save(account);
		
		ofy().clear();
		Account accountDeleted = accountDao.retrieve(account.getId());
		assert accountDeleted != null;
		try {
			accountService.removeAccount(account.getId().toString(), user);
		} catch (UnauthorizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ofy().clear();
		assert ofy().load().key(accountDeleted.getKey()).now() == null;
	}
}
