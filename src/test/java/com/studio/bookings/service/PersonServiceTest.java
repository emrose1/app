package com.studio.bookings.service;

import static com.studio.bookings.util.OfyService.ofy;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

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
import com.studio.bookings.entity.Booking;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.Person;
import com.studio.bookings.enums.Permission;
import com.studio.bookings.util.TestBase;


public class PersonServiceTest extends TestBase  {
	
	public static AccountService accountService = new AccountService();
	BaseDao<Account> accountDao = new BaseDao<Account>(Account.class);
	public static CalendarService calendarService = new CalendarService();
	BaseDao<Calendar> calendarDao = new BaseDao<Calendar>(Calendar.class);
	public static ChildBaseDao<Person, Account> personDao = new ChildBaseDao<Person, Account>(Person.class, Account.class);
	public static PersonService personService = new PersonService();
	
	static Permission permission = Permission.ACCOUNT;
	public static ChildBaseDao<AccessControlList, Account> aclDao = new ChildBaseDao<AccessControlList, Account>(AccessControlList.class, Account.class);
	

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
	
	public Account setUpAccount() {
		Account account = new Account();
		accountDao.save(account);
		AccessControlList acl = new AccessControlList(permission.toString(), "true", "true", "true", "true", "ADMIN");
		aclDao.save(acl);
		return account;
	}
	
	@Test
	public void insertPerson() {
		User user = this.setUpUser();
		Account account = this.setUpAccount();
		Person p = personService.insertPerson("username", "password", "ADMIN", account.getId(), user);

		// TODO change to using DAO 
		Person userFetched = personService.findUser(p.getId(), account.getId()); 
		
		assert "username".equals(userFetched.getUsername());
		assert "password".equals(userFetched.getPassword());
		assert account.getId().equals(userFetched.getAccount().getId());
	}
	

	
	@Test
	public void findUser() {
		User user = this.setUpUser();
		Account account = this.setUpAccount();
		
		// TODO change to using DAO 
		Person p = personService.insertPerson("username", "password", "ADMIN",  account.getId(), user); 
		
		Person userFetched = personService.findUser(p.getId(), account.getId()); 
		assert "username".equals(userFetched.getUsername());
		assert "password".equals(userFetched.getPassword());
		assert account.getId().equals(userFetched.getAccount().getId());	
	}
	
	/*@Test
	public void ListUsers() {
		
		Account userAccount = new Account();
		Long userAccountId = accountDao.save(userAccount);
		User user = this.setUpUser();
		this.setUp(userAccount, user);
		
		Account account = accountService.insertAccount(userAccountId, "Account", "test", "admin", "123", "ADMIN", user);
		//User user1 = personDao.insertUser("username1", "password1", "ADMIN",  account.getId()); 
		Long user2 = personDao.save(new Person("username2", "password2", "ADMIN",  account, user)); 
		//List<User> usersFetched = userService.listUsers(account.getId());
		//assert usersFetched.size() == 0;
		
		List<User> calList =  personDao.list();
		assert calList.size() == 1;
		
		List <Person> users = com.studio.bookings.util.TestObjectifyService.ofy().
				load().type(Person.class).ancestor(account).list();
		
		Account account = accountService.insertAccount("Account", "test", "admin", "123", "ADMIN");
		User user = userService.insertUser("username", "password", "ADMIN",  account.getId()); 
		User userFetched = userService.findUser(user.getId(), account.getId()); 
		assert "username".equals(userFetched.getUsername());
		assert "password".equals(userFetched.getPassword());
		//assert account.getId().equals(userFetched.getAccount().getId());	
		Assert.assertNotNull(users);
	}*/
	
	/*	@Test
	public void authUserSession() {
		
		Account userAccount = new Account();
		Long userAccountId = accountDao.save(userAccount);
		User user = this.setUpUser();
		this.setUp(userAccount, user);
		
		Account account = accountService.insertAccount(userAccountId, "Account", "test", "admin", "123", "ADMIN", user);
		Person p = personService.insertPerson("username", "password", "ADMIN",  account.getId(), user); 
		Person userFetched = personService.authUserSession("username", "password", account.getId());
		assert "username".equals(userFetched.getUsername());
		assert "password".equals(userFetched.getPassword());
		assert account.getId().equals(userFetched.getAccount().getId());
	}*/

}
