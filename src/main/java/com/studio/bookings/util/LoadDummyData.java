package com.studio.bookings.util;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.oauth.OAuthService;
import com.google.appengine.api.oauth.OAuthServiceFactory;
import com.google.appengine.api.users.User;
import com.studio.bookings.dao.BaseDao;
import com.studio.bookings.dao.BookingDao;
import com.studio.bookings.dao.ChildBaseDao;
import com.studio.bookings.dao.EventAttributeDao;
import com.studio.bookings.dao.EventCategoryDao;
import com.studio.bookings.dao.EventDao;
import com.studio.bookings.entity.AccessControlList;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.Person;
import com.studio.bookings.enums.Permission;
import com.studio.bookings.enums.UserType;
import com.studio.bookings.service.AccessControlListService;
import com.studio.bookings.service.AccountService;
import com.studio.bookings.service.BaseService;
import com.studio.bookings.service.CalendarService;
import com.studio.bookings.service.PersonService;

public class LoadDummyData extends BaseService {
	
	public static AccountService accountService = new AccountService();
	static BaseDao<Account> accountDao = new BaseDao<Account>(Account.class);
	
	public static PersonService personService = new PersonService();
	public static ChildBaseDao<Person, Account> personDao = new ChildBaseDao<Person, Account>(Person.class, Account.class);
	
	
	CalendarService calendarService = new CalendarService();
	ChildBaseDao<Calendar, Account> calendarDao = new ChildBaseDao<Calendar, Account>(Calendar.class, Account.class);
	
	AccessControlListService aclService = new AccessControlListService();
	BaseDao<AccessControlList> aclDao = new BaseDao<AccessControlList>(AccessControlList.class);
	
	public Account setUpAccount(String accountName) {
		Account account = new Account(accountName);
		accountDao.save(account);
		return account;
	}
	
	public void setUpPerson(String username, Integer userId, Account account){
	
		List<String> userTypeList = new ArrayList<String>();
		userTypeList.add("SUPERADMIN"); 
		userTypeList.add("ADMIN");
		userTypeList.add("OWNER");
		userTypeList.add("INSTRUCTOR");
		userTypeList.add("ATTENDEE");

		Person p = new Person(account, userId.toString(), username, "email", userTypeList.get(0));
		personDao.save(p);
		
	}
	
	public void setUpAcl() {
		
		List<String> permissionList = new ArrayList<String>();
		permissionList.add("ACCOUNT"); 
		permissionList.add("CALENDAR");
		permissionList.add("EVENT");
		permissionList.add("BOOKING");
		permissionList.add("USER");
		permissionList.add("ACL");
	
		List<String> userTypeList = new ArrayList<String>();
		userTypeList.add("SUPERADMIN"); 
		userTypeList.add("ADMIN");
		userTypeList.add("OWNER");
		userTypeList.add("INSTRUCTOR");
		userTypeList.add("ATTENDEE");

		
		for (String p : permissionList) {
			for (String ut : userTypeList) {
				AccessControlList acl = new AccessControlList(p, "true", "true", "true", "true", "true", ut);
				aclDao.save(acl);
			}
		}
	}
	
	public void initSetup() {
		
		setUpAcl();
		
		List<String> accounts = new ArrayList<String>();
		List<String> persons = new ArrayList<String>();
		
		accounts.add("Testing Account1");
		accounts.add("Testing Accounts2");
		accounts.add("Testing Accounts3");
		
		persons.add("Person 1");
		persons.add("Person 2");
		persons.add("Person 3");
		
		int index = 0;
		
		for (String accountName : accounts)
		{
			Account account = setUpAccount(accountName);
			for (String personName : persons) {
				setUpPerson(personName, index++, account);
			}
		}

		
		/*OAuthService oauth = OAuthServiceFactory.getOAuthService();
	    User user = null;
		try {
			user = oauth.getCurrentUser();
		} catch (OAuthRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	
/*		Calendar calendar2 = new Calendar("calendar2", ownerFetched);
		Key<Calendar> calendar3 = calendarDao.save(calendar2);
	
		EventCategory ec1 = new EventCategory("Pilates Matwork", calendar1);
		EventCategory ec2 = new EventCategory("Pilates Reformer", calendar1);
		EventCategory ec3 = new EventCategory("Pilates Reformer", calendar2);
		EventCategory ec4 = new EventCategory("Pilates Matwork", calendar2);
		eventCategoryDao.save(ec1); 
		eventCategoryDao.save(ec2);
		eventCategoryDao.save(ec3); 
		eventCategoryDao.save(ec4);
		
		EventAttribute ea1 = new EventAttribute("Beginners", calendar1);
		EventAttribute ea2 = new EventAttribute("Intermediate", calendar2);
		eventAttributeDao.save(ea1); 
		eventAttributeDao.save(ea2);
		
		DateTime dt = new DateTime();
		dt = dt.plus(Period.months(1));
	

		DateTime JAN_1_1970 = new DateTime(1970, 1, 1, 0, 0);
		Long shortDate = new Duration(JAN_1_1970, dt).getMillis();
		
		EventRepeatType repeatDaily  = EventRepeatType.DAILY;
		
		Boolean btrue = new Boolean("true");
		
		Event event1 = new Event(calendar1,btrue, repeatDaily);
		eventDao.save(event1);
	
		Event event2 = new Event(calendar2, btrue, repeatDaily);
		eventDao.save(event2);
	
		Event event3 = new Event(calendar1, btrue, repeatDaily);
		eventDao.save(event3);
	
		Event event4 = new Event(calendar2, btrue, repeatDaily);
		eventDao.save(event4);
		
		Event event5 = new Event(calendar1, btrue, repeatDaily);
		eventDao.save(event5);
	
		Event event6 = new Event(calendar1,btrue, repeatDaily);
		eventDao.save(event6);*/
		
/*
		Permission bookingPermission = Permission.BOOKING;
		Permission eventPermission = Permission.EVENT;
		Permission calendarPermission = Permission.CALENDAR;

		AccessControlList adminBooking = new AccessControlList(bookingPermission, true, true, true, true, UserType.ADMIN, account1);
		AccessControlList adminEvent = new AccessControlList(eventPermission, false, false, false, false, UserType.ADMIN, account1);
		AccessControlList adminCalendar = new AccessControlList(calendarPermission, true, true, true, true, UserType.ADMIN, account1);

		AccessControlList ownerBooking = new AccessControlList(bookingPermission, true, true, true, true, UserType.OWNER, account1);
		AccessControlList ownerEvent = new AccessControlList(eventPermission, true, true, true, true, UserType.OWNER, account1);
		AccessControlList ownerCalendar = new AccessControlList(calendarPermission, false, false, false, false, UserType.OWNER, account);

		AccessControlList organizerBooking = new AccessControlList(bookingPermission, true, true, true, true, UserType.INSTRUCTOR, account);
		AccessControlList organizerEvent = new AccessControlList(eventPermission, false, false, false, false, UserType.INSTRUCTOR, account);
		AccessControlList organizerCalendar = new AccessControlList(calendarPermission, false, false, false, false, UserType.INSTRUCTOR, account);

		accessControlListDao.save(adminBooking);
		accessControlListDao.save(adminEvent);
		accessControlListDao.save(adminCalendar);

		accessControlListDao.save(ownerBooking);
		accessControlListDao.save(ownerEvent);
		accessControlListDao.save(ownerCalendar);

		accessControlListDao.save(organizerBooking);
		accessControlListDao.save(organizerEvent);
		accessControlListDao.save(organizerCalendar);

		Person user1 = new Person("admin", "123", "ADMIN", account, user);
		Person user2 = new Person("owner", "123", "OWNER", account, user);
		Person user3 = new Person("organizer", "123", "INSTRUCTOR", account, user);
		Person user4 = new Person("attendee", "123", "ATTENDEE", account, user);

		personDao.save(user1);
		personDao.save(user2);
		personDao.save(user3);
		personDao.save(user4);
		*/
		/*
		Booking booking1 = new Booking(user1, event1);
		Booking booking2 = new Booking(user2, event1);
		Booking booking3 = new Booking(user3, event1);
		Booking booking4 = new Booking(user4, event1);
		
		bookingDao.save(booking1);
		bookingDao.save(booking2);
		bookingDao.save(booking3);
		bookingDao.save(booking4);*/
	
	}
}
