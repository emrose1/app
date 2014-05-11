package com.studio.bookings.service;

import static com.studio.bookings.util.TestObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.api.server.spi.config.ApiMethod;
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
import com.studio.bookings.enums.UserType;
import com.studio.bookings.util.TestBase;

public class AccessControlListServiceTest extends TestBase {
	
	public static AccountService accountService = new AccountService();
	static BaseDao<Account> accountDao = new BaseDao<Account>(Account.class);
	
	public static AccessControlListService aclService = new AccessControlListService();
	public static BaseDao<AccessControlList> aclDao = new BaseDao<AccessControlList>(AccessControlList.class);
	
	public static CalendarService calendarService = new CalendarService();
	BaseDao<Calendar> calendarDao = new BaseDao<Calendar>(Calendar.class);
	
	public static PersonService personService = new PersonService();
	public static ChildBaseDao<Person, Account> personDao = new ChildBaseDao<Person, Account>(Person.class, Account.class);
	
	
	
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
		AccessControlList acl = new AccessControlList(permission.toString(), "true", "true", "true", "true", "true", "SUPERADMIN");
		aclDao.save(acl);
		Person p = new Person(userAccount, user.getUserId(), "test1", "email", "family_name", "given_name", "SUPERADMIN");
		personDao.save(p);
	}
		
	@Test
	public void insertAccessControlList() {
		
		Account account = new Account();
		accountDao.save(account);
		
		User user = this.setUpUser();
		this.setUp(account, user);

		AccessControlList acl = aclService.insertAccessControlList("CALENDAR", "true", "true", "true",
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
		
		AccessControlList acl = new AccessControlList("CALENDAR", "true", "true", "true", "true", "true", "ADMIN");
		aclDao.save(acl);	
		AccessControlList aclFetched = aclService.findAccessControlList(acl.getId(), account.getId(), user);
		
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
	public void updateAccessControlList() {
		
		Account account = new Account();
		accountDao.save(account);
		
		User user = this.setUpUser();
		this.setUp(account, user);
		
		AccessControlList acl = new AccessControlList("CALENDAR", "true", "true", "true", "true", "true", "ADMIN");
		aclDao.save(acl);	
		AccessControlList aclFetched = aclService.updateAccessControlList(acl.getId(), 
				"EVENT", "false", "false", "false", "false", "false", "OWNER", account.getId(), user);
		
		Assert.assertNotNull(aclFetched);
		assert acl.getId().equals(aclFetched.getId());
		assert acl.getCanView().equals(aclFetched.getCanView());
		assert acl.getCanInsert().equals(aclFetched.getCanInsert());
		assert acl.getCanUpdate().equals(aclFetched.getCanUpdate());
		assert acl.getCanDelete().equals(aclFetched.getCanDelete());
		assert acl.getPermission().equals(aclFetched.getPermission());
		
		assert acl.getPermission().equals(Permission.EVENT);
		assert acl.getCanView().equals(new Boolean("false"));
		assert acl.getCanInsert().equals(new Boolean("false"));
		assert acl.getCanUpdate().equals(new Boolean("false"));
		assert acl.getCanDelete().equals(new Boolean("false"));
		assert acl.getUserType().equals(UserType.OWNER);
	}
	
	
	@Test
	public void deleteAccessControlList() {
		
		Account account = new Account();
		accountDao.save(account);
		
		User user = this.setUpUser();
		this.setUp(account, user);
		
		AccessControlList acl = new AccessControlList("CALENDAR", "true", "true", "true", "true", "true", "ADMIN");
		aclDao.save(acl);
		
		ofy().clear();
		AccessControlList aclDeleted = aclDao.retrieve(acl.getId());
		assert aclDeleted != null;
		aclService.deleteAccessControlList(acl.getId(), account.getId(), user);
		ofy().clear();
		assert ofy().load().key(aclDeleted.getKey()).now() == null;
	}
	
	@Test
	public void listAccessControlList() {
		
		Account account = new Account();
		accountDao.save(account);
		
		User user = this.setUpUser();
		
		AccessControlList acl = new AccessControlList(permission.toString(), "true", "true", "true", "true", "true", "SUPERADMIN");
		aclDao.save(acl);
		Person p = new Person(account, user.getUserId(), "test1", "email", "family_name", "given_name", "SUPERADMIN");
		personDao.save(p);
		
		AccessControlList acl1 = new AccessControlList("CALENDAR", "true", "true", "true", "true", "true", "ADMIN");
		AccessControlList acl2 = new AccessControlList("EVENT", "true", "true", "true", "true", "true", "ADMIN");
		AccessControlList acl3 = new AccessControlList("BOOKING", "true", "true", "true", "true", "true", "ADMIN");
				
		List<AccessControlList> aclList = new ArrayList<AccessControlList>();
		aclList.add(acl);
		aclList.add(acl1);
		aclList.add(acl2);
		aclList.add(acl3);
		
		aclDao.save(aclList);
		List<AccessControlList> aclFetched = aclService.listAccessControlList(account.getId(), user);
		
		Assert.assertNotNull(aclFetched);
		assert aclFetched.size() == aclList.size();
		assert aclFetched.size() == 4;
	}
	
	@Test
	public void allowViewAccountSuperadmin() {
		
		Account account = new Account();
		accountDao.save(account);
		
		User user = this.setUpUser();
		
		Person p = new Person(account, user.getUserId(), "test1", "email", "family_name", "given_name", "SUPERADMIN");
		personDao.save(p);
		
		AccessControlList acl1 = new AccessControlList("ACCOUNT", "true", "false", "false", "false", "false", "SUPERADMIN");
		aclDao.save(acl1);
		Assert.assertTrue(aclService.allowView(account.getId(), "ACCOUNT", user).get(0));
		AccessControlList aclFetched1 = aclDao.retrieve(acl1.getId());
		aclFetched1.setCanView(new Boolean("false"));
		aclDao.save(aclFetched1);
		Assert.assertFalse(aclService.allowView(account.getId(), "ACCOUNT", user).get(0));
    }
	
	@Test
	public void allowViewCalendarAdmin() {
		
		Account account = new Account();
		accountDao.save(account);
		
		User user = this.setUpUser();
		
		Person p = new Person(account, user.getUserId(), "test1", "email", "family_name", "given_name", "ADMIN");
		personDao.save(p);
		
		AccessControlList acl1 = new AccessControlList("CALENDAR", "true", "false", "false", "false", "false", "ADMIN");
		aclDao.save(acl1);
		Assert.assertTrue(aclService.allowView(account.getId(), "CALENDAR", user).get(0));
		AccessControlList aclFetched = aclDao.retrieve(acl1.getId());
		aclFetched.setCanView(new Boolean("false"));
		aclDao.save(aclFetched);
		Assert.assertFalse(aclService.allowView(account.getId(), "CALENDAR", user).get(0));	
	}
	
	@Test
	public void allowViewEventOwner() {
		
		Account account = new Account();
		accountDao.save(account);
		
		User user = this.setUpUser();
		
		Person p = new Person(account, user.getUserId(), "test1", "email", "family_name", "given_name", "OWNER");
		personDao.save(p);
		
		AccessControlList acl = new AccessControlList("EVENT", "true", "false", "false", "false", "false", "OWNER");
		aclDao.save(acl);
		
		Assert.assertTrue(aclService.allowView(account.getId(), "EVENT", user).get(0));
		AccessControlList aclFetched = aclDao.retrieve(acl.getId());
		aclFetched.setCanView(new Boolean("false"));
		aclDao.save(aclFetched);
		Assert.assertFalse(aclService.allowView(account.getId(), "EVENT", user).get(0));
	}
	
	
	@Test
	public void allowViewBookingInstructor() {
		
		Account account = new Account();
		accountDao.save(account);
		
		User user = this.setUpUser();
		
		Person p = new Person(account, user.getUserId(), "test1", "email", "family_name", "given_name", "OWNER");
		personDao.save(p);
		
		AccessControlList acl4 = new AccessControlList("BOOKING", "true", "false", "false", "false", "false", "OWNER");
		aclDao.save(acl4);
		Assert.assertTrue(aclService.allowView(account.getId(), "BOOKING", user).get(0));
		AccessControlList aclFetched4 = aclDao.retrieve(acl4.getId());
		aclFetched4.setCanView(new Boolean("false"));
		aclDao.save(aclFetched4);
		Assert.assertFalse(aclService.allowView(account.getId(), "BOOKING", user).get(0));
	}
	
	
	@Test
	public void allowViewUserAttendee () {
	
		Account account = new Account();
		accountDao.save(account);
		User user = this.setUpUser();
		Person p = new Person(account, user.getUserId(), "test1", "email", "family_name", "given_name", "ATTENDEE");
		personDao.save(p);
		
		AccessControlList acl5 = new AccessControlList("USER", "true", "false", "false", "false", "false", "ATTENDEE");
		aclDao.save(acl5);
		Assert.assertTrue(aclService.allowView(account.getId(), "USER", user).get(0));
		AccessControlList aclFetched5 = aclDao.retrieve(acl5.getId());
		aclFetched5.setCanView(new Boolean("false"));
		aclDao.save(aclFetched5);
		Assert.assertFalse(aclService.allowView(account.getId(), "USER", user).get(0));
	}
			
	@Test
	public void allowViewAclAttendee () {
		
		Account account = new Account();
		accountDao.save(account);
		User user = this.setUpUser();
		Person p = new Person(account, user.getUserId(), "test1", "email", "family_name", "given_name", "ATTENDEE");
		personDao.save(p);
		
		AccessControlList acl6 = new AccessControlList("ACL", "true", "false", "false", "false", "false", "ATTENDEE");
		aclDao.save(acl6);
		Assert.assertTrue(aclService.allowView(account.getId(), "ACL", user).get(0));
		AccessControlList aclFetched6 = aclDao.retrieve(acl6.getId());
		aclFetched6.setCanView(new Boolean("false"));
		aclDao.save(aclFetched6);
		Assert.assertFalse(aclService.allowView(account.getId(), "ACL", user).get(0));
	}
	
	@Test
	public void allowViewAllAclAttendee () {
		
		Account account = new Account();
		accountDao.save(account);
		User user = this.setUpUser();
		Person p = new Person(account, user.getUserId(), "test1", "email", "family_name", "given_name", "ATTENDEE");
		personDao.save(p);
		
		AccessControlList acl6 = new AccessControlList("ACL", "true", "false", "false", "false", "false", "ATTENDEE");
		aclDao.save(acl6);
		Assert.assertTrue(aclService.allowView(account.getId(), "ACL", user).get(0));
		AccessControlList aclFetched6 = aclDao.retrieve(acl6.getId());
		aclFetched6.setCanView(new Boolean("false"));
		aclDao.save(aclFetched6);
		Assert.assertFalse(aclService.allowView(account.getId(), "ACL", user).get(0));
	}
	
	@Test
	public void allowInsert() {
		
		Account account = new Account();
		accountDao.save(account);
		
		User user = this.setUpUser();
		this.setUp(account, user);
		
		AccessControlList acl = new AccessControlList("CALENDAR", "false", "false", "true", "false", "false", "SUPERADMIN");
		aclDao.save(acl);
		
		Assert.assertTrue(aclService.allowInsert(account.getId(), "CALENDAR", user).get(0));
		
		AccessControlList aclFetched = aclDao.retrieve(acl.getId());
		aclFetched.setCanInsert(new Boolean("false"));
		aclDao.save(aclFetched);

		Assert.assertFalse(aclService.allowInsert(account.getId(), "CALENDAR", user).get(0));
		
    }

	@Test
	public void allowUpdate() {
		
		Account account = new Account();
		accountDao.save(account);
		
		User user = this.setUpUser();
		this.setUp(account, user);
		
		AccessControlList acl = new AccessControlList("CALENDAR", "false", "false", "false", "true", "false", "SUPERADMIN");
		aclDao.save(acl);
		
		Assert.assertTrue(aclService.allowUpdate(account.getId(), "CALENDAR", user).get(0));
		
		AccessControlList aclFetched = aclDao.retrieve(acl.getId());
		aclFetched.setCanUpdate(new Boolean("false"));
		aclDao.save(aclFetched);

		Assert.assertFalse(aclService.allowUpdate(account.getId(), "CALENDAR", user).get(0));
		
    }
	
	@Test
	public void allowDelete() {
		
		Account account = new Account();
		accountDao.save(account);
		
		User user = this.setUpUser();
		this.setUp(account, user);
		
		AccessControlList acl = new AccessControlList("CALENDAR", "false", "false", "false", "false", "true", "SUPERADMIN");
		aclDao.save(acl);
		
		Assert.assertTrue(aclService.allowDelete(account.getId(), "CALENDAR", user).get(0));
		
		AccessControlList aclFetched = aclDao.retrieve(acl.getId());
		aclFetched.setCanDelete(new Boolean("false"));
		aclDao.save(aclFetched);

		Assert.assertFalse(aclService.allowDelete(account.getId(), "CALENDAR", user).get(0));
		
    }
}
