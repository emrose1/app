package com.studio.bookings.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.oauth.OAuthService;
import com.google.appengine.api.oauth.OAuthServiceFactory;
import com.google.appengine.api.users.User;
import com.studio.bookings.entity.AccessControlList;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.Event;
import com.studio.bookings.entity.EventAttribute;
import com.studio.bookings.entity.EventCategory;
import com.studio.bookings.entity.Person;
import com.studio.bookings.enums.EventRepeatType;
import com.studio.bookings.enums.Permission;
import com.studio.bookings.util.Constants;

@Api(
    name = "booking",
    version = "v1",
    scopes = {Constants.EMAIL_SCOPE},
    clientIds = {Constants.WEB_CLIENT_ID},
    audiences = {Constants.ANDROID_AUDIENCE}
)

public class DummySetupService  extends BaseService {
	
	public static AccessControlListService aclService = new AccessControlListService();
	Permission permission = Permission.ACCOUNT;
	
	@ApiMethod(name = "account.listDummyAccounts", path="calendar.listDummyAccounts", httpMethod = "get")
	public List<Account> listDummyAccounts() {
		return accountDao.list();
	}
	
	public Account setUpAccount(@Named("person") String accountName) {
		Account account = new Account(accountName);
		accountDao.save(account);
		return account;
	}
	
	private Person setUpPerson(
			String username, 
			String userId, 
			Account account){
	
		List<String> userTypeList = new ArrayList<String>();
		userTypeList.add("SUPERADMIN"); 
		userTypeList.add("ADMIN");
		userTypeList.add("OWNER");
		userTypeList.add("INSTRUCTOR");
		userTypeList.add("ATTENDEE");

		Person p = new Person(account, userId, username, "email", "SUPERADMIN");
		personDao.save(p);
		return p;
	}
	
	private DateTime calcNextMonday(DateTime dateTime) {
        if (dateTime.getDayOfWeek() >= DateTimeConstants.MONDAY) {
            dateTime = dateTime.plusWeeks(1);
        }
        return dateTime.withDayOfWeek(DateTimeConstants.MONDAY);
    }
	
	private void setUpEvent(
			Calendar cal,
			Person inst,
			EventCategory eventCategory,
			EventAttribute eventAttribute,
			@Named("hours") Integer hours
			) {
		Date dateStart = calcNextMonday(new DateTime()).withTime(0, 0, 0, 0).plusHours(hours).minusWeeks(1).toDate();
		Date dateEnd = new DateTime(dateStart).plusHours(2).toDate();
		
		Date dateStart2 = calcNextMonday(new DateTime()).withTime(0, 0, 0, 0).plusHours(hours + 2).minusWeeks(1).toDate();
		Date dateEnd2 = new DateTime(dateStart2).plusHours(2).toDate();
		
		Date dateStart3 = calcNextMonday(new DateTime()).withTime(0, 0, 0, 0).plusHours(hours + 5).minusWeeks(1).toDate();
		Date dateEnd3 = new DateTime(dateStart3).plusHours(2).toDate();
		
		Date finalRepeatWeeklyDate = new DateTime(dateEnd).plusWeeks(5).plusDays(1).toDate();
		List<Integer> daysOfWeek = Arrays.asList(1);
		
		Date finalDate = new DateTime(dateStart).plusMonths(6).plusDays(1).toDate();

		Event ev1 = new Event(cal, true, EventRepeatType.DAILY, new Integer(1), finalDate,  null, 
		daysOfWeek, null,  "Matwork", dateStart, dateEnd, new Integer(10), inst, 
		eventCategory, eventAttribute);
		
		Event ev2 = new Event(cal, true, EventRepeatType.DAILY, new Integer(2), finalDate,  null, 
				daysOfWeek, null,  "Pilates Matwork", dateStart2, dateEnd2, new Integer(10), inst, 
				eventCategory, eventAttribute);
		
		Event ev3 = new Event(cal, true, EventRepeatType.WEEKLY, new Integer(1), finalDate,  null, 
				daysOfWeek, null,  "Pilates Reformer", dateStart, dateEnd, new Integer(10), inst, 
				eventCategory, eventAttribute);
		
		eventDao.save(ev1);
		eventDao.save(ev2);
		eventDao.save(ev3);
	}
	
	private Calendar setUpCalendar(
			String calendarname, 
			Account account) {
		Calendar c = new Calendar (calendarname, account);
		calendarDao.save(c);
		return c;
	}
	
	private EventAttribute setUpEventAttribute(
			String attributename, 
			Account account) {
		EventAttribute c = new EventAttribute (attributename, account);
		eventAttributeDao.save(c);
		return c;
	}
	
	private EventCategory setUpEventCategory(
			String categoryname, 
			Account account) {
		EventCategory c = new EventCategory (categoryname, account);
		eventCategoryDao.save(c);
		return c;
	}
	
	private void setUpAcl() {
		if(aclDao.list().size() == 0) { 
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
	}
	
	private User setUpUser() {
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
	
	private void setUp(Account userAccount, User user) {
		AccessControlList acl = new AccessControlList(permission.toString(), "true", "true", "true", "true", "true", "SUPERADMIN");
		aclDao.save(acl);
		Person p = new Person(userAccount, user.getUserId(), "test1", "email", "SUPERADMIN");
		personDao.save(p);
	}
	
	
	@ApiMethod(name = "calendar.dummyUsers", path="calendar.dummyUsers", httpMethod = "get")
	public List<Account> dummyUsers() {
		
		setUpAcl();
		
		List<Account> accountList2 = new ArrayList<Account>();
		List<String> accounts = new ArrayList<String>();
		List<String> persons = new ArrayList<String>();
		List<String> calendars = new ArrayList<String>();
		List<String> attributes = new ArrayList<String>();
		List<String> categories = new ArrayList<String>();
		
		accounts.add("Testing Account1");
		accounts.add("Testing Accounts2");
		accounts.add("Testing Accounts3");
		
		persons.add("Person 1");
		persons.add("Person 2");
		persons.add("Person 3");
		
		calendars.add("Calendar 1");
		calendars.add("Calendar 2");
		calendars.add("Calendar 3");
		
		attributes.add("Attribute 1");
		attributes.add("Attribute 2");
		attributes.add("Attribute 3");
		
		categories.add("Category 1");
		categories.add("Category 2");
		categories.add("Category 3");

		
		
		int index = 0;
		for (String accountName : accounts) {
			if (accountDao.list().size() < 3) {
				Account account = new Account(accountName);
				accountDao.save(account);
				accountList2.add(account);
				
				Calendar[] cals = new Calendar[3];
				EventAttribute[] eattrs = new EventAttribute[3];
				EventCategory[] eccats = new EventCategory[3];
				
				Calendar c = new Calendar();
				EventAttribute ea = new EventAttribute();
				EventCategory ec = new EventCategory();
				Person p = new Person();
				
				int index1 = 0;
				for (String personName : persons) {
					setUpPerson(personName + " " +  account, "105854312734748005380", account);
					p = setUpPerson(personName + " " +  account, "0", account);
				}
				
				index1 = 0;
				for (String calendarName : calendars) {
					 c = setUpCalendar(calendarName, account);
					 cals[index1] = c;
					 index1++;
				}
				
				index1 = 0;
				for (String attributeName : attributes) {
					ea = (setUpEventAttribute(attributeName, account));
					eattrs[index1] = ea;
					index1++;
				}
				
				index1 = 0;
				for (String categoryName : categories) {
					ec = (setUpEventCategory(categoryName, account));
					eccats[index1] = ec;
					index1++;
				}
				
				index1 = 0;
				for(Calendar cal : cals) {
					setUpEvent(cal, p, eccats[index1], eattrs[index1], index1);
					index1++;
				}
			}
		}
		return accountList2;
	}
	
}
