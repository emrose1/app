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

import com.googlecode.objectify.Key;
import com.studio.bookings.dao.BaseDao;
import com.studio.bookings.dao.ChildBaseDao;
import com.studio.bookings.entity.AccessControlList;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.Booking;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.User;
import com.studio.bookings.util.TestBase;


public class UserServiceTest extends TestBase  {
	
	public static AccountService accountService = new AccountService();
	BaseDao<Account> accountDao = new BaseDao<Account>(Account.class);
	public static CalendarService calendarService = new CalendarService();
	BaseDao<Calendar> calendarDao = new BaseDao<Calendar>(Calendar.class);
	public static ChildBaseDao<User, Account> userDao = new ChildBaseDao<User, Account>(User.class, Account.class);
	public static UserService userService = new UserService();
	
	@Test
	public void insertUser() {
		Account account = accountService.insertAccount("Account", "test", "admin", "123", "ADMIN");
		User user = userService.insertUser("username", "password", "ADMIN",  account.getId()); 
		User userFetched = userService.findUser(user.getId(), account.getId()); 
		assert "username".equals(userFetched.getUsername());
		assert "password".equals(userFetched.getPassword());
		assert account.getId().equals(userFetched.getAccount().getId());
	}
	
	@Test
	public void authUserSession() {
		Account account = accountService.insertAccount("Account", "test", "admin", "123", "ADMIN");
		User user = userService.insertUser("username", "password", "ADMIN",  account.getId()); 
		User userFetched = userService.authUserSession("username", "password", account.getId());
		assert "username".equals(userFetched.getUsername());
		assert "password".equals(userFetched.getPassword());
		assert account.getId().equals(userFetched.getAccount().getId());
	}
	
	@Test
	public void findUser() {
		Account account = accountService.insertAccount("Account", "test", "admin", "123", "ADMIN");
		User user = userService.insertUser("username", "password", "ADMIN",  account.getId()); 
		User userFetched = userService.findUser(user.getId(), account.getId()); 
		assert "username".equals(userFetched.getUsername());
		assert "password".equals(userFetched.getPassword());
		assert account.getId().equals(userFetched.getAccount().getId());	
	}
	
	@Test
	public void ListUsers() {
		Account account = accountService.insertAccount("Account", "test", "admin", "123", "ADMIN");
		//User user1 = userDao.insertUser("username1", "password1", "ADMIN",  account.getId()); 
		Long user2 = userDao.save(new User("username2", "password2", "ADMIN",  account)); 
		//List<User> usersFetched = userService.listUsers(account.getId());
		//assert usersFetched.size() == 0;
		
		/*List<User> calList =  userDao.list();
		assert calList.size() == 1;*/
		
		List <User> users = com.studio.bookings.util.TestObjectifyService.ofy().
				load().type(User.class).ancestor(account).list();
		
		/*Account account = accountService.insertAccount("Account", "test", "admin", "123", "ADMIN");
		User user = userService.insertUser("username", "password", "ADMIN",  account.getId()); 
		User userFetched = userService.findUser(user.getId(), account.getId()); 
		assert "username".equals(userFetched.getUsername());
		assert "password".equals(userFetched.getPassword());*/
		//assert account.getId().equals(userFetched.getAccount().getId());	
		Assert.assertNotNull(users);
	}

}
