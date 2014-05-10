package com.studio.bookings.service;

import static com.studio.bookings.util.TestObjectifyService.ofy;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

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
import com.studio.bookings.enums.UserType;
import com.studio.bookings.util.TestBase;


public class PersonServiceTest extends TestBase  {
	
	AccountService accountService = new AccountService();
	BaseDao<Account> accountDao = new BaseDao<Account>(Account.class);
	
	CalendarService calendarService = new CalendarService();
	BaseDao<Calendar> calendarDao = new BaseDao<Calendar>(Calendar.class);
	
	ChildBaseDao<Person, Account> personDao = new ChildBaseDao<Person, Account>(Person.class, Account.class);
	PersonService personService = new PersonService();
	
	ChildBaseDao<AccessControlList, Account> aclDao = new ChildBaseDao<AccessControlList, Account>(AccessControlList.class, Account.class);
	
	Permission permission = Permission.USER;
	
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
	
	public Account setUpAccount(User user) {
		Account account = new Account();
		accountDao.save(account);
		AccessControlList acl = new AccessControlList(permission.toString(), "true", "true", "true", "true", "true", "SUPERADMIN");
		aclDao.save(acl);

		Person p = new Person(account, user.getUserId(), "username", "email", 
	    		"family_name", "given_name", "SUPERADMIN");
		personDao.save(p);
		return account;
	}

	@Test
	public void insertPerson() {
		User user = this.setUpUser();
		Account account = new Account();
		accountDao.save(account);
		AccessControlList acl = new AccessControlList(permission.toString(), "true", "true", "true", "true", "true", "SUPERADMIN");
		aclDao.save(acl);

		assert personDao.oneFilterAncestorQuery("userId", user.getUserId(), account) == null;
		Person p = personService.insertPerson("username", "email", "family_name", "given_name", "SUPERADMIN", account.getId(), user);
		
		Person personFetched = personDao.retrieveAncestor(p.getId(), account); 
		assert personDao.oneFilterAncestorQuery("userId", user.getUserId(), account) != null;
		assert "username".equals(personFetched.getUsername());
		assert account.getId().equals(personFetched.getAccount().getId());
	}
	
	@Test
	public void findPerson() {
		User user = this.setUpUser();
		Account account = this.setUpAccount(user);
		Person p = new Person(account, user.getUserId(), "username", "email", "family_name", "given_name", "SUPERADMIN");
		personDao.save(p);
		Person userFetched = personService.findPerson(p.getId(), account.getId(), user); 
		
		assert "username".equals(userFetched.getUsername());
		assert account.getId().equals(userFetched.getAccount().getId());
	}
	
	@Test
	public void ListPersons() {
		
		User user = this.setUpUser();
		Account account = this.setUpAccount(user);
		List<Person> peopleList = personService.listPersons(account.getId(),user);
		assert peopleList.size() == 1;
		
		Person peop1 = new Person(account, "1", "test1", "email", "family_name", "given_name", "SUPERADMIN");
		Person peop2 = new Person(account, "1", "test1", "email", "family_name", "given_name", "SUPERADMIN"); 

		peopleList.add(peop1);
		peopleList.add(peop2);
		personDao.save(peopleList);
		
		peopleList = personService.listPersons(account.getId(),user);
		assert peopleList.size() == 3;

		assert peopleList.get(0).getAccount().getId().equals(account.getId());
		Assert.assertNotNull(peopleList);
	}
	
	@Test
	public void updatePerson() {
		
		
		User user = this.setUpUser();
		Account account = this.setUpAccount(user);
		Person p = new Person(account, user.getUserId(), "username", "email", "family_name", "given_name", "SUPERADMIN");
		personDao.save(p);

		Person personUpdated = personService.updatePerson(p.getId(),  
				account.getId(), 
				"username updated",
				"email updated",
				"family_name updated",
				"given_name updated",
				"ADMIN", 
				user);
		
		Person personFetched = personDao.retrieveAncestor(personUpdated.getId(), account);
		
		assert personUpdated.getAccount().equals(personFetched.getAccount());
		assert personUpdated.getId().equals(personFetched.getId());
		assert personUpdated.getUsername().equals("username updated");
		assert personUpdated.getEmail().equals("email updated");
		assert personUpdated.getFamilyName().equals("family_name updated");
		assert personUpdated.getGivenName().equals("given_name updated");
		assert personUpdated.getUserType().equals(UserType.ADMIN);
	}
	
	@Test
	public void deletePersons() {
		
		User user = this.setUpUser();
		Account account = this.setUpAccount(user);
		
		Person person1 = new Person(account, "1", "test1", "email", "family_name", "given_name", "SUPERADMIN");
		personDao.save(person1);
		
		Person person2 = new Person(account, "2", "test2", "email", "family_name", "given_name", "SUPERADMIN");
		personDao.save(person2);
		
		ofy().clear();
		Person personToDelete1 = personDao.retrieveAncestor(person1.getId(), account);
		Person personToDelete2 = personDao.retrieveAncestor(person2.getId(), account);
		
		assert personToDelete1 != null;
		assert personToDelete2 != null;
		
		List<Long> personList = new ArrayList<Long>();
		personList.add(personToDelete1.getId());
		personList.add(personToDelete2.getId());
		
		personService.deletePersons(personList, account.getId(), user);
		ofy().clear();
		
		assert ofy().load().key(personToDelete1.getKey()).now() == null;
		assert ofy().load().key(personToDelete2.getKey()).now() == null;
	}
}
