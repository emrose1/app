package com.studio.bookings.service;

import static com.studio.bookings.util.TestObjectifyService.ofy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.googlecode.objectify.Key;
import com.studio.bookings.dao.BaseDao;
import com.studio.bookings.dao.ChildBaseDao;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.User;
import com.studio.bookings.entity.UserSession;
import com.studio.bookings.util.TestBase;


public class AccountServiceTest extends TestBase {
	
	public static AccountService accountService = new AccountService();
	BaseDao<Account> accountDao = new BaseDao<Account>(Account.class);
	public static CalendarService calendarService = new CalendarService();
	BaseDao<Calendar> calendarDao = new BaseDao<Calendar>(Calendar.class);
	public static UserService userService = new UserService();
	public static ChildBaseDao<User, Account> userDao = new ChildBaseDao<User, Account>(User.class, Account.class);
	
	
	@Test
	public void insertAccount() {
		
		Account account = accountService.insertAccount("Testing Account", "Testing Calendar", "username", "password", "ADMIN");
		Account accountFetched = accountService.findAccount(account.getId());
		
		Calendar calendarFetched = calendarService.listCalendars(account.getId()).get(0);
		assert "Testing Calendar".equals(calendarFetched.getDescription());
		assert account.getId().equals(calendarFetched.getAccount().getId());
		
		User usersFetched = userService.authUserSession("username", "password", account.getId());
		
		assert "username".equals(usersFetched.getUsername());
		assert account.getId().equals(usersFetched.getAccount().getId());
		
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
	public void findAccount() {
		
		Account account1 = new Account("Account 1");
		Account account2 = new Account("Account 2");

		accountDao.save(account1);
		accountDao.save(account2);

		Account accountFetched1 = accountService.findAccount(account1.getId());
		Account accountFetched2 = accountService.findAccount(account2.getId());
		
		assert accountFetched1.getId().equals(account1.getId());
		assert accountFetched1.getName().equals(account1.getName());
		
		assert accountFetched2.getId().equals(account2.getId());
		assert accountFetched2.getName().equals(account2.getName());
		
		Assert.assertNotEquals(accountFetched1.getId(), accountFetched2.getId());
		Assert.assertNotEquals(accountFetched1.getName(), accountFetched2.getName());
	}
	
	@Test
	public void ListAccounts() {
		
		Account account1 = new Account("Account 1");
		Account account2 = new Account("Account 2");
				
		List<Account> accountList = new ArrayList<Account>();
		accountList.add(account1);
		accountList.add(account2);
		
		accountDao.save(accountList);
		List<Account> accountsFetched = accountService.listAccounts();
		
		Assert.assertNotNull(accountsFetched);
		assert accountsFetched.size() == accountList.size();
		assert accountsFetched.size() == 2;
	}
	
	@Test
	public void updateAccount() {
		
		Account account = new Account("Account 1");
		accountDao.save(account);
		
		Account accountUpdated = accountService.updateAccount(account.getId(), "updated Account");
		assert account.getName().equals("updated Account");
		assert account.getName().equals(accountUpdated.getName());
		assert account.getId().equals(accountUpdated.getId());
	}
	
	@Test
	public void deleteAccount() {
		
		Account account = new Account("Account 1");
		accountDao.save(account);
		
		ofy().clear();
		Account accountDeleted = accountDao.retrieve(account.getId());
		assert accountDeleted != null;
		accountService.deleteAccount(account.getId());
		ofy().clear();
		assert ofy().load().key(accountDeleted.getKey()).now() == null;
	}
}
