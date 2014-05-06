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
import com.studio.bookings.dao.BaseDao;
import com.studio.bookings.dao.ChildBaseDao;
import com.studio.bookings.entity.AccessControlList;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.Person;
import com.studio.bookings.enums.Permission;
import com.studio.bookings.util.TestBase;

public class AccessControlListServiceTest extends TestBase {
	
	public static AccountService accountService = new AccountService();
	static BaseDao<Account> accountDao = new BaseDao<Account>(Account.class);
	public static BaseDao<AccessControlList> aclDao = new BaseDao<AccessControlList>(AccessControlList.class);
	
	public static CalendarService calendarService = new CalendarService();
	BaseDao<Calendar> calendarDao = new BaseDao<Calendar>(Calendar.class);
	public static PersonService personService = new PersonService();
	public static ChildBaseDao<Person, Account> personDao = new ChildBaseDao<Person, Account>(Person.class, Account.class);
	public static AccessControlListService aclService = new AccessControlListService();
	
	
	static Permission permission = Permission.ACL;
	
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
		AccessControlList acl = new AccessControlList(permission.toString(), "true", "true", "true", "true", "SUPERADMIN");
		aclDao.save(acl);
		Person p = new Person("username", "password", "SUPERADMIN", userAccount, user);
		personDao.save(p);
	}
		
	@Test
	public void insertAccessControlList() {
		
		Account account = new Account();
		accountDao.save(account);
		
		User user = this.setUpUser();
		this.setUp(account, user);

		AccessControlList acl = aclService.insertAccessControlList("CALENDAR", "true", "true", 
				"true", "true", "ADMIN", account.getId(), user);	
		AccessControlList aclFetched = aclDao.retrieve(acl.getId());
		Assert.assertNotNull(aclFetched);
		
		assert acl.getId().equals(aclFetched.getId());
		assert acl.getCanView().equals(aclFetched.getCanView());
		assert acl.getCanInsert().equals(aclFetched.getCanInsert());
		assert acl.getCanUpdate().equals(aclFetched.getCanUpdate());
		assert acl.getCanDelete().equals(aclFetched.getCanDelete());
		assert acl.getUserType().equals(aclFetched.getUserType());
		assert acl.getPermission().equals(aclFetched.getPermission());
		assert aclFetched.getCanView().equals(new Boolean("true"));
	}
	
	@Test
	public void findAccessControlList() {
		
		Account account = new Account();
		accountDao.save(account);
		
		User user = this.setUpUser();
		this.setUp(account, user);
		
		AccessControlList acl = new AccessControlList("CALENDAR", "true", "true", "true", "true", "ADMIN");
		aclDao.save(acl);	
		AccessControlList aclFetched = aclService.findAcl(acl.getId(), account.getId(), user);
		
		Assert.assertNotNull(aclFetched);
		assert acl.getId().equals(aclFetched.getId());
		assert acl.getCanView().equals(aclFetched.getCanView());
		assert acl.getCanInsert().equals(aclFetched.getCanInsert());
		assert acl.getCanUpdate().equals(aclFetched.getCanUpdate());
		assert acl.getCanDelete().equals(aclFetched.getCanDelete());
		assert acl.getUserType().equals(aclFetched.getUserType());
		assert acl.getPermission().equals(aclFetched.getPermission());
		assert aclFetched.getCanView().equals(new Boolean("true"));
	}
	
	@Test
	public void listAccessControlList() {
		
		Account account = new Account();
		accountDao.save(account);
		
		User user = this.setUpUser();
		
		AccessControlList acl = new AccessControlList(permission.toString(), "true", "true", "true", "true", "SUPERADMIN");
		aclDao.save(acl);
		Person p = new Person("username", "password", "SUPERADMIN", account, user);
		personDao.save(p);
		
		AccessControlList acl1 = new AccessControlList("CALENDAR", "true", "true", "true", "true", "ADMIN");
		AccessControlList acl2 = new AccessControlList("EVENT", "true", "true", "true", "true", "ADMIN");
		AccessControlList acl3 = new AccessControlList("BOOKING", "true", "true", "true", "true", "ADMIN");
				
		List<AccessControlList> aclList = new ArrayList<AccessControlList>();
		aclList.add(acl);
		aclList.add(acl1);
		aclList.add(acl2);
		aclList.add(acl3);
		
		aclDao.save(aclList);
		List<AccessControlList> aclFetched = aclService.listAcl(account.getId(), user);
		
		Assert.assertNotNull(aclFetched);
		assert aclFetched.size() == aclList.size();
		assert aclFetched.size() == 4;
	}

}
