package com.studio.bookings.service;

import static com.studio.bookings.util.TestObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.oauth.OAuthService;
import com.google.appengine.api.oauth.OAuthServiceFactory;
import com.google.appengine.api.users.User;
import com.studio.bookings.entity.AccessControlList;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.Application;
import com.studio.bookings.entity.Person;
import com.studio.bookings.enums.Permission;
import com.studio.bookings.enums.UserType;
import com.studio.bookings.util.TestBase;


public class PersonServiceTest extends TestBase  {
	
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

		Person p = new Person(account, user.getUserId(), "username", "email", "SUPERADMIN");
		personDao.save(p);
		return account;
	}
	
	@Test
	public void authorizePerson() {
		
		User user = this.setUpUser();
		Account account = new Account();
		accountDao.save(account);
		AccessControlList acl = new AccessControlList(permission.toString(), "true", "true", "true", "true", "true", "SUPERADMIN");
		aclDao.save(acl);

		assert personDao.oneFilterAncestorQuery("userId", user.getUserId(), account) == null;
		Person p = personService.authorizePerson("username", "email", account.getId().toString(), user);
		Person userFetched = personService.getPersonInAccount(p.getId(), account.getId(), user); 
		
		UserType defaultUt = UserType.ATTENDEE;
		UserType superUt = UserType.SUPERADMIN;
		// NEW USER GET USERTYPE ATTENDEE
		assert defaultUt.equals(userFetched.getUserType());
		assert account.getId().equals(userFetched.getAccount().getId());
		
		// EXISTING USER KEEPS THEIR USERTYPE
		assert personDao.oneFilterAncestorQuery("userId", user.getUserId(), account) != null;
		userFetched.setUserType(superUt);
		personDao.save(userFetched);
		Person userFetched2 = personService.authorizePerson("username", "email", account.getId().toString(), user);
		assert superUt.equals(userFetched2.getUserType());
		assert account.getId().equals(userFetched.getAccount().getId());
	}

	@Test
	public void insertPerson() {
		User user = this.setUpUser();
		Account account = new Account();
		accountDao.save(account);
		AccessControlList acl = new AccessControlList(permission.toString(), "true", "true", "true", "true", "true", "SUPERADMIN");
		aclDao.save(acl);

		assert personDao.oneFilterAncestorQuery("userId", user.getUserId(), account) == null;
		Person p = personService.insertPerson("username", "email", account.getId(), user);
		
		Person personFetched = personDao.retrieveAncestor(p.getId(), account); 
		assert personDao.oneFilterAncestorQuery("userId", user.getUserId(), account) != null;
		assert "username".equals(personFetched.getUsername());
		assert account.getId().equals(personFetched.getAccount().getId());
	}
	
	@Test
	public void findPersonInAccount() {
		User user = this.setUpUser();
		Account account = this.setUpAccount(user);
		Person p = new Person(account, user.getUserId(), "username", "email", "SUPERADMIN");
		personDao.save(p);
		Person userFetched = personService.getPersonInAccount(p.getId(), account.getId(), user); 
		
		assert "username".equals(userFetched.getUsername());
		assert account.getId().equals(userFetched.getAccount().getId());
	}
	
	@Test
	public void findPersonInApplication() {
		User user = this.setUpUser();
		
		Application app = new Application();
		applicationDao.save(app);
		
		Account account = new Account("test", app);
		accountDao.save(account);
		
		Person p = new Person(account, user.getUserId(), "username", "email", "OWNER");
		personDao.save(p);

		Person pFetched = personAppDao.twoFilterAncestorQuery("userId", user.getUserId(), "userType", "OWNER", app);
		
		
		assert "username".equals(pFetched.getUsername());
		assert account.getId().equals(pFetched.getAccount().getId());
	}
	
	@Test
	public void ListPersons() {
		
		User user = this.setUpUser();
		Account account = this.setUpAccount(user);
		List<Person> peopleList = personService.listPersons(account.getId().toString(),user);
		assert peopleList.size() == 1;
		
		Person peop1 = new Person(account, "1", "test1", "email", "SUPERADMIN");
		Person peop2 = new Person(account, "1", "test1", "email", "SUPERADMIN"); 

		peopleList.add(peop1);
		peopleList.add(peop2);
		personDao.save(peopleList);
		
		peopleList = personService.listPersons(account.getId().toString(),user);
		assert peopleList.size() == 3;

		assert peopleList.get(0).getAccount().getId().equals(account.getId());
		Assert.assertNotNull(peopleList);
	}
	
	@Test
	public void updatePerson() {
		
		User user = this.setUpUser();
		Account account = this.setUpAccount(user);
		Person p = new Person(account, user.getUserId(), "username", "email", "SUPERADMIN");
		personDao.save(p);

		Person personUpdated = personService.updatePerson(p.getId(),  
				account.getId(), 
				"username updated",
				"email updated",
				user);
		
		Person personFetched = personDao.retrieveAncestor(personUpdated.getId(), account);
		
		assert personUpdated.getAccount().equals(personFetched.getAccount());
		assert personUpdated.getId().equals(personFetched.getId());
		assert personUpdated.getUsername().equals("username updated");
		assert personUpdated.getEmail().equals("email updated");
	}
	
	@Test
	public void removePerson() {
		
		User user = this.setUpUser();
		Account account = this.setUpAccount(user);
		
		Person person1 = new Person(account, "1", "test1", "email", "SUPERADMIN");
		personDao.save(person1);
		
		Person person2 = new Person(account, "2", "test2", "email", "SUPERADMIN");
		personDao.save(person2);
		
		ofy().clear();
		Person personToDelete1 = personDao.retrieveAncestor(person1.getId(), account);
		Person personToDelete2 = personDao.retrieveAncestor(person2.getId(), account);
		
		assert personToDelete1 != null;
		assert personToDelete2 != null;
		
		String personId1 = personToDelete1.getId().toString();
		String personId2 = personToDelete1.getId().toString();
		String accountId = account.getId().toString();
		
		personService.removePerson(personId1, accountId, user);
		personService.removePerson(personId2, accountId, user);
		
		ofy().clear();
		
		assert ofy().load().key(personToDelete1.getKey()).now() == null;
		assert ofy().load().key(personToDelete2.getKey()).now() == null;
	}
	

}
