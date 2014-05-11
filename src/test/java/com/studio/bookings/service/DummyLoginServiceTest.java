package com.studio.bookings.service;

import java.util.List;

import org.testng.annotations.Test;

import com.studio.bookings.dao.BaseDao;
import com.studio.bookings.dao.ChildBaseDao;
import com.studio.bookings.entity.AccessControlList;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.Person;
import com.studio.bookings.enums.Permission;
import com.studio.bookings.util.TestBase;

public class DummyLoginServiceTest extends TestBase {
	
	AccountService accountService = new AccountService();
	BaseDao<Account> accountDao = new BaseDao<Account>(Account.class);
	
	CalendarService calendarService = new CalendarService();
	BaseDao<Calendar> calendarDao = new BaseDao<Calendar>(Calendar.class);
	
	ChildBaseDao<Person, Account> personDao = new ChildBaseDao<Person, Account>(Person.class, Account.class);
	PersonService personService = new PersonService();
	
	DummyLoginService dummyService = new DummyLoginService();
	ChildBaseDao<AccessControlList, Account> aclDao = new ChildBaseDao<AccessControlList, Account>(AccessControlList.class, Account.class);
	Permission permission = Permission.USER;
		
	@Test
	public void dummyUsers(){
		List<Account> accounts = dummyService.dummyUsers();
		List<Account> accountsFetched = accountDao.list();
		//List<Person> persons = personDao.list();
		assert accounts.size() == 3;
		assert accountsFetched.size() == 3;
		
	}

	@Test
	public void listDummyAccounts() {
		List<Account> accounts = dummyService.dummyUsers();
		List<Account> accountsFetched = dummyService.listDummyAccounts();
		assert accountsFetched.size() == 3;
	}
}
