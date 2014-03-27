package com.studio.bookings.service;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.googlecode.objectify.Key;
import com.studio.bookings.dao.BaseDao;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.util.TestBase;

import static com.studio.bookings.util.TestObjectifyService.ofy;


public class CalendarServiceTest extends TestBase {
	
	public static AccountService accountService = new AccountService();
	CalendarService calendarService = new CalendarService();


	@Test
	public void insertCalendar() {
		Account account = accountService.insertAccount("Testing Account");
		Calendar calendar = calendarService.insertCalendar("Testing Calendar", account.getId());
		
		Calendar calendarFetched = calendarService.findCalendar(calendar.getId(), account.getId());
		assert calendar.getId().equals(calendarFetched.getId());
		assert calendar.getDescription().equals(calendarFetched.getDescription());
		assert calendar.getAccount().getId().equals(calendarFetched.getAccount().getId());
	}
	
	@Test
	public void findCalendar() {
		
		String accountName1 = "Account 1";
		String accountName2 = "Account 2";

		Account account1 = accountService.insertAccount(accountName1);
		Account account2 = accountService.insertAccount(accountName2);
		
		String calendarName1 = "Calendar 1";
		String calendarName2 = "Calendar 2";

		Calendar calendar1 = calendarService.insertCalendar(calendarName1, account1.getId());
		Calendar calendar2 = calendarService.insertCalendar(calendarName2, account2.getId());
		
		Calendar calendarFetched1 = calendarService.findCalendar(calendar1.getId(), account1.getId()); 
		Calendar calendarFetched2 = calendarService.findCalendar(calendar2.getId(), account2.getId()); 

		
		assert calendarFetched1.getId().equals(calendar1.getId());
		assert calendarFetched1.getDescription().equals(calendar1.getDescription());
		
		assert calendarFetched2.getId().equals(calendar2.getId());
		assert calendarFetched2.getDescription().equals(calendar2.getDescription());
		
		Assert.assertNotEquals(calendarFetched1.getId(), calendarFetched2.getId());
		Assert.assertNotEquals(calendarFetched1.getDescription(), calendarFetched2.getDescription());
	}
	
/*	@Test
	public void ListAccounts() {
		
		String accountName1 = "Account 1";
		String accountName2 = "Account 2";

		Account account1 = accountService.insertAccount(accountName1);
		Account account2 = accountService.insertAccount(accountName2);
		
		List<Account> objs = new ArrayList<Account>();
		objs.add(account1);
		objs.add(account2);
		
		List<Key<Account>> accounts = accountDao.save(objs);
		List<Account> accountsFetched = accountService.listAccounts();
		
		assert accountsFetched.size() == accounts.size();
		assert accountsFetched.size() == 2;
	}
	
	@Test
	public void updateAccount() {
		
		String accountName = "Account name";
		Account account = accountService.insertAccount(accountName);
		Long accountUpdatedId = accountService.updateAccount(account.getId(), "updated Account");
		Account accountUpdated = accountService.findAccount(accountUpdatedId);
		
		assert account.getName().equals("updated Account");
		assert account.getName().equals(accountUpdated.getName());
		assert account.getId().equals(accountUpdated.getId());

	}
	
	@Test
	public void deleteAccount() {
		
		String accountName = "Account name";
		Account account = accountService.insertAccount(accountName);
		ofy().clear();
		Account accountDeleted = accountDao.retrieve(account.getId());
		assert accountDeleted != null;
		accountService.deleteAccount(account.getId());
		ofy().clear();
		assert ofy().load().key(accountDeleted.getKey()).now() == null;
	}*/
	
	
}
