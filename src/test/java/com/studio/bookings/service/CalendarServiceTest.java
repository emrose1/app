package com.studio.bookings.service;

import static com.studio.bookings.util.TestObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.oauth.OAuthService;
import com.google.appengine.api.oauth.OAuthServiceFactory;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import com.studio.bookings.dao.BaseDao;
import com.studio.bookings.dao.ChildBaseDao;
import com.studio.bookings.entity.AccessControlList;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.Person;
import com.studio.bookings.enums.Permission;
import com.studio.bookings.util.TestBase;


public class CalendarServiceTest extends TestBase {
	
	public static AccountService accountService = new AccountService();
	public static BaseDao<Account> accountDao = new BaseDao<Account>(Account.class);
	
	CalendarService calendarService = new CalendarService();
	ChildBaseDao<Calendar, Account> calendarDao = new ChildBaseDao<Calendar, Account>(Calendar.class, Account.class);
	AccessControlListService aclService = new AccessControlListService();
	BaseDao<AccessControlList> aclDao = new BaseDao<AccessControlList>(AccessControlList.class);
	ChildBaseDao<Person, Account> personDao = new ChildBaseDao<Person, Account>(Person.class, Account.class);
	
	static Permission permission = Permission.CALENDAR;
	
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
		Person p = new Person(userAccount, user.getUserId(), "test1", "email", "family_name", "given_name", "SUPERADMIN");
		personDao.save(p);
	}

	@Test
	public void insertCalendar() {
		
		User user = this.setUpUser();
		
		Account account1 = new Account("Account1");
		Account account2 = new Account("Account2");
		accountDao.save(account1);
		accountDao.save(account2);
		
		this.setUp(account1, user);
		this.setUp(account2, user);
		
		Calendar calendar1 = calendarService.insertCalendar("Testing Calendar1", account1.getId(), user);
		Calendar calendar2 = calendarService.insertCalendar("Testing Calendar2", account2.getId(), user);
		
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
		User user = this.setUpUser();
		this.setUp(account1, user);
		this.setUp(account2, user);
		Calendar cal1 = new Calendar ("calendar1", account1); 
		Calendar cal2 = new Calendar ("calendar2", account2);  
		
		calendarDao.save(cal1);
		calendarDao.save(cal2);
		
		Calendar calendarFetched1 = calendarService.findCalendar(cal1.getId(), account1.getId(), user); 
		Calendar calendarFetched2 = calendarService.findCalendar(cal2.getId(), account2.getId(), user); 
		
		assert calendarFetched1.getId().equals(cal1.getId());
		assert calendarFetched1.getDescription().equals(cal1.getDescription());
		
		assert calendarFetched2.getId().equals(cal2.getId());
		assert calendarFetched2.getDescription().equals(cal2.getDescription());
		
		Assert.assertNotEquals(calendarFetched1.getId(), calendarFetched2.getId());
		Assert.assertNotEquals(calendarFetched1.getDescription(), calendarFetched2.getDescription());
	}
	
	@Test
	public void listCalendars() {
		
		Account account = new Account("account");
		accountDao.save(account);
		User user = this.setUpUser();
		this.setUp(account, user);
		
		Calendar cal1 = new Calendar ("calendar1", account); 
		Calendar cal2 = new Calendar ("calendar2", account);  
		List<Calendar> calList = new ArrayList<Calendar>();
		calList.add(cal1);
		calList.add(cal2);
		calendarDao.save(calList);
		
		List <Calendar> cals = calendarService.listCalendars(account.getId(), user);
		
		assert cals.get(0).getAccount().getId().equals(account.getId());
		Assert.assertNotNull(cals);
		assert cals.size() == 2;
	}
		
	@Test
	public void updateCalendar() {
		
		Account account = new Account("account");
		accountDao.save(account);
		
		User user = this.setUpUser();
		this.setUp(account, user);
		
		Calendar cal = new Calendar ("calendar1", account);
		calendarDao.save(cal);

		Calendar calendarUpdated = calendarService.updateCalendar(cal.getId(), account.getId(), "Updated Calendar", user);
		Calendar calendarFetched = calendarDao.retrieveAncestor(calendarUpdated.getId(), account);
		
		assert calendarUpdated.getAccount().equals(calendarFetched.getAccount());
		assert calendarUpdated.getDescription().equals("Updated Calendar");
		assert calendarUpdated.getId().equals(calendarFetched.getId());
	}
	
	@Test
	public void deleteCalendar() {
		
		Account account = new Account("account");
		Long accountId = accountDao.save(account);
		
		User user = this.setUpUser();
		this.setUp(account, user);
		
		Calendar cal1 = new Calendar ("calendar1", account);
		calendarDao.save(cal1);
		
		Calendar cal2 = new Calendar ("calendar2", account);
		calendarDao.save(cal2);
		
		ofy().clear();
		Calendar calendarToDelete1 = calendarDao.retrieveAncestor(cal1.getId(), account);
		Calendar calendarToDelete2 = calendarDao.retrieveAncestor(cal2.getId(), account);
		
		assert calendarToDelete1 != null;
		assert calendarToDelete2 != null;
		
		List<Long> calendarList = new ArrayList<Long>();
		calendarList.add(calendarToDelete1.getId());
		calendarList.add(calendarToDelete2.getId());
		
		calendarService.deleteCalendars(calendarList, accountId, user);
		ofy().clear();
		
		assert ofy().load().key(calendarToDelete1.getKey()).now() == null;
		assert ofy().load().key(calendarToDelete2.getKey()).now() == null;
	}
	
	
}
