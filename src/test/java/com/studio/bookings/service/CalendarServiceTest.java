package com.studio.bookings.service;

import static com.studio.bookings.util.TestObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.googlecode.objectify.Key;
import com.studio.bookings.dao.BaseDao;
import com.studio.bookings.dao.ChildBaseDao;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.User;
import com.studio.bookings.util.TestBase;


public class CalendarServiceTest extends TestBase {
	
	public static AccountService accountService = new AccountService();
	CalendarService calendarService = new CalendarService();
	public static BaseDao<Account> accountDao = new BaseDao<Account>(Account.class);
	public static ChildBaseDao<Calendar, Account> calendarDao = new ChildBaseDao<Calendar, Account>(Calendar.class, Account.class);
	public static ChildBaseDao<User, Account> userDao = new ChildBaseDao<User, Account>(User.class, Account.class);
	

	@Test
	public void insertCalendar() {
		Account account1 = new Account("Account1");
		Account account2 = new Account("Account2");
		accountDao.save(account1);
		accountDao.save(account2);
		
		Calendar calendar1 = calendarService.insertCalendar("Testing Calendar1", account1.getId());
		Calendar calendar2 = calendarService.insertCalendar("Testing Calendar2", account2.getId());
		
		Calendar calendarFetched1 = calendarDao.retrieveAncestor(calendar1.getId(), account1);
		Calendar calendarFetched2 = calendarDao.retrieveAncestor(calendar2.getId(), account2);
		
		assert calendar1.getId().equals(calendarFetched1.getId());
		assert calendar1.getDescription().equals(calendarFetched1.getDescription());
		assert calendar1.getAccount().getId().equals(calendarFetched1.getAccount().getId());
		
		assert calendar2.getId().equals(calendarFetched2.getId());
		assert calendar2.getDescription().equals(calendarFetched2.getDescription());
		assert calendar2.getAccount().getId().equals(calendarFetched2.getAccount().getId());
		
		Assert.assertNotEquals(calendarFetched1.getId(), calendarFetched2.getId());
		Assert.assertNotEquals(calendarFetched1.getDescription(), calendarFetched2.getDescription());
	}
	
	@Test
	public void findCalendar() {
		
		Account account1 = new Account("account1");
		Account account2 = new Account("account2");
		accountDao.save(account1);
		accountDao.save(account2);
		Calendar cal1 = new Calendar ("calendar1", account1); 
		Calendar cal2 = new Calendar ("calendar2", account2);  
		
		calendarDao.save(cal1);
		calendarDao.save(cal2);
		
		Calendar calendarFetched1 = calendarService.findCalendar(cal1.getId(), account1.getId()); 
		Calendar calendarFetched2 = calendarService.findCalendar(cal2.getId(), account2.getId()); 
		
		assert calendarFetched1.getId().equals(cal1.getId());
		assert calendarFetched1.getDescription().equals(cal1.getDescription());
		
		assert calendarFetched2.getId().equals(cal2.getId());
		assert calendarFetched2.getDescription().equals(cal2.getDescription());
		
		Assert.assertNotEquals(calendarFetched1.getId(), calendarFetched2.getId());
		Assert.assertNotEquals(calendarFetched1.getDescription(), calendarFetched2.getDescription());
	}
	
	@Test
	public void ListCalendars() {
		Account account = new Account("account");
		accountDao.save(account);
		Calendar cal1 = new Calendar ("calendar1", account); 
		Calendar cal2 = new Calendar ("calendar2", account);  
		List<Calendar> calList = new ArrayList<Calendar>();
		calList.add(cal1);
		calList.add(cal2);
		calendarDao.save(calList);
		
		List <Calendar> cals = calendarService.listCalendars(account.getId());
		
		assert cals.get(0).getAccount().getId().equals(account.getId());
		Assert.assertNotNull(cals);
		assert cals.size() == 2;
	}
	
/*
	
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
