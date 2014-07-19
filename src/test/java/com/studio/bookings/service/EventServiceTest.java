package com.studio.bookings.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.oauth.OAuthService;
import com.google.appengine.api.oauth.OAuthServiceFactory;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.NotFoundException;
import com.studio.bookings.entity.AccessControlList;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.Event;
import com.studio.bookings.entity.EventAttribute;
import com.studio.bookings.entity.EventCategory;
import com.studio.bookings.entity.Person;
import com.studio.bookings.enums.EventRepeatType;
import com.studio.bookings.enums.Permission;
import com.studio.bookings.util.TestBase;

public class EventServiceTest extends TestBase {
	
	static Permission permission = Permission.EVENT;
	
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
		Person p = new Person(userAccount, user.getUserId(), "test1", "email", "SUPERADMIN");
		personDao.save(p);
	}
	

	@Test
	public void insertEvent() throws ParseException, NotFoundException {
		
		User user = this.setUpUser();
		
		Account account = new Account("Account1");
		accountDao.save(account);
		
		this.setUp(account, user);
		
		Calendar calendar1 = new Calendar("Testing Calendar1", account);
		Calendar calendar2 = new Calendar("Testing Calendar2", account);
		calendarDao.save(calendar1);
		calendarDao.save(calendar2);
		
		Person instructor1 = new Person( account, "0", "testing1", "testing1@test.com", "INSTRUCTOR"); 
		personDao.save(instructor1);
		
		Person instructor2 = new Person( account, "0", "testing2", "testing2@test.com", "INSTRUCTOR"); 
		personDao.save(instructor2);
		
		EventCategory eventCategory1 = new EventCategory("Event Category1", account);
		eventCategoryDao.save(eventCategory1);
		
		EventCategory eventCategory2 = new EventCategory("Event Category2", account);
		eventCategoryDao.save(eventCategory2);
		
		EventAttribute eventAttribute1 = new EventAttribute("Event Attribute1", account);
		eventAttributeDao.save(eventAttribute1);
		
		EventAttribute eventAttribute2 = new EventAttribute("Event Attribute2", account);
		eventAttributeDao.save(eventAttribute2);
		
		Event event1 = null;
		Event event2 = null;
		
		List<Integer> daysOfWeek = Arrays.asList(1, 2, 3);
		List<Date> excludeDays = Arrays.asList(new Date(), new Date());
		
		String[] excludeDaysArray = { 
				"10:00 01 12 2015",
				"10:00 01 13 2015"
			};
		
		Integer[] daysOfWeekArray = { 
			    1, 2, 3
			};
		
		Event ev = new Event(calendar1, true, EventRepeatType.WEEKLY, new Integer(1), new Date(),  new Integer(52), 
			daysOfWeek, excludeDays,  "summary", new Date(), new Date(), new Integer(10), instructor1, 
			eventCategory1, eventAttribute1);
		
		eventDao.save(ev);
		
		event1 = eventService.insertEvent(account.getId(), calendar1.getId(), "false", "", new Integer(0), "", new Integer(0), daysOfWeekArray, excludeDaysArray,
				"Event Summary 1", "10:00 01 12 2014", "11:00 01 12 2014", new Integer(10), instructor1.getId(), 
				 eventCategory1.getId(), eventAttribute1.getId());
		
		event2 = eventService.insertEvent(account.getId(), calendar2.getId(), "false", "", new Integer(0), "", new Integer(0), daysOfWeekArray, excludeDaysArray,
				"Event Summary 2", "10:00 01 12 2015", "11:00 01 12 2015", new Integer(1), instructor2.getId(), 
				 eventCategory2.getId(), eventAttribute2.getId());
		
		Event eventFetched1 = eventDao.retrieveAncestor(event1.getId(), calendar1);
		Event eventFetched2 = eventDao.retrieveAncestor(event2.getId(), calendar2);
		
		
		Assert.assertNotNull(event1);

		assert event1.getId().equals(eventFetched1.getId());
		assert event1.getSummary().equals(eventFetched1.getSummary());
		assert event1.getStartDateTime().equals(eventFetched1.getStartDateTime());
		assert event1.getEndDateTime().equals(eventFetched1.getEndDateTime());
		assert event1.getMaxAttendees().equals(eventFetched1.getMaxAttendees());
		assert event1.getInstructor().equals(eventFetched1.getInstructor());
		assert event1.getEventCategory().equals(eventFetched1.getEventCategory());
		assert event1.getEventAttribute().equals(eventFetched1.getEventAttribute());
		
		assert event2.getId().equals(eventFetched2.getId());
		assert event2.getSummary().equals(eventFetched2.getSummary());
		assert event2.getStartDateTime().equals(eventFetched2.getStartDateTime());
		assert event2.getEndDateTime().equals(eventFetched2.getEndDateTime());
		assert event2.getMaxAttendees().equals(eventFetched2.getMaxAttendees());
		assert event2.getInstructor().equals(eventFetched2.getInstructor());
		assert event2.getEventCategory().equals(eventFetched2.getEventCategory());
		assert event2.getEventAttribute().equals(eventFetched2.getEventAttribute());
		
		Assert.assertNull(eventFetched1.getRepeatType());
		Assert.assertEquals(eventFetched1.getRepeatDaysOfWeek().size(), 0);
		Assert.assertEquals(eventFetched1.getExcludeDays().size(), 0);
		Assert.assertEquals(eventFetched1.getRepeatCount(),  new Integer(0));
		Assert.assertNull(eventFetched1.getRepeatFinalDate());
		Assert.assertNotEquals(eventFetched1.getId(), eventFetched2.getId());
		Assert.assertNotEquals(eventFetched1.getSummary(), eventFetched2.getSummary());
		Assert.assertNotEquals(eventFetched1.getStartDateTime(), eventFetched2.getStartDateTime());
		Assert.assertNotEquals(eventFetched1.getMaxAttendees(), eventFetched2.getMaxAttendees());
		Assert.assertNotEquals(eventFetched1.getInstructor(), eventFetched2.getInstructor());
		Assert.assertNotEquals(eventFetched1.getEventCategory(), eventFetched2.getEventCategory());
		Assert.assertNotEquals(eventFetched1.getEventAttribute(), eventFetched2.getEventAttribute());
	}
	
	@Test
	public void insertRepeatEvent() throws ParseException, NotFoundException {
		
		User user = this.setUpUser();
		
		Account account = new Account("Account1");
		accountDao.save(account);
		
		this.setUp(account, user);
		
		Calendar calendar1 = new Calendar("Testing Calendar1", account);
		calendarDao.save(calendar1);
		
		Person instructor1 = new Person( account, "0", "testing1", "testing1@test.com", "INSTRUCTOR"); 
		personDao.save(instructor1);

		
		EventCategory eventCategory1 = new EventCategory("Event Category1", account);
		eventCategoryDao.save(eventCategory1);

		EventAttribute eventAttribute1 = new EventAttribute("Event Attribute1", account);
		eventAttributeDao.save(eventAttribute1);
		
		Event event1 = null;
		
		List<Integer> daysOfWeek = Arrays.asList(1, 2, 3);
		List<Date> excludeDays = Arrays.asList(new Date(), new Date());
		
		
		Event ev = new Event(calendar1, true, EventRepeatType.WEEKLY, new Integer(1), new Date(),  new Integer(52), 
			daysOfWeek, excludeDays,  "summary", new Date(), new Date(), new Integer(10), instructor1, 
			eventCategory1, eventAttribute1);
		
		eventDao.save(ev);
		
		String[] excludeDates = { 
				"10:00 01 12 2015",
				"10:00 01 13 2015"
			};
		
		Integer[] daysOfWeeks = { 
			    1, 2, 3
			};
				
		event1 = eventService.insertEvent(account.getId(), calendar1.getId(), "true", "WEEKLY", new Integer(0), "12:00 01 12 2015", 
				new Integer(10), daysOfWeeks, excludeDates, "Event Summary 1", 
				"10:00 01 12 2014", "11:00 01 12 2014", 
				new Integer(10), instructor1.getId(), eventCategory1.getId(), eventAttribute1.getId());
		
		Event eventFetched1 = eventDao.retrieveAncestor(event1.getId(), calendar1);
		
		Assert.assertNotNull(event1);

		assert event1.getId().equals(eventFetched1.getId());
		assert event1.getRepeatEvent().equals(eventFetched1.getRepeatEvent());
		assert event1.getRepeatType().equals(eventFetched1.getRepeatType());
		assert event1.getRepeatDaysOfWeek().equals(eventFetched1.getRepeatDaysOfWeek());
		assert event1.getExcludeDays().equals(eventFetched1.getExcludeDays());
		assert event1.getSummary().equals(eventFetched1.getSummary());
		assert event1.getStartDateTime().equals(eventFetched1.getStartDateTime());
		assert event1.getEndDateTime().equals(eventFetched1.getEndDateTime());
		assert event1.getRepeatFinalDate().equals(eventFetched1.getRepeatFinalDate());
		assert event1.getMaxAttendees().equals(eventFetched1.getMaxAttendees());
		assert event1.getInstructor().equals(eventFetched1.getInstructor());
		assert event1.getEventCategory().equals(eventFetched1.getEventCategory());
		assert event1.getEventAttribute().equals(eventFetched1.getEventAttribute());
	}
	
	@Test
	public void listAdHocEvents() throws Exception {
		
		User user = this.setUpUser();
		
		Account account = new Account("Account1");
		accountDao.save(account);
		
		this.setUp(account, user);
		
		Calendar calendar1 = new Calendar("Testing Calendar1", account);
		Calendar calendar2 = new Calendar("Testing Calendar2", account);
		calendarDao.save(calendar1);
		calendarDao.save(calendar2);
		
		Person instructor1 = new Person( account, "0", "testing1", "testing1@test.com", "INSTRUCTOR"); 
		personDao.save(instructor1);
		
		Person instructor2 = new Person( account, "0", "testing2", "testing2@test.com", "INSTRUCTOR"); 
		personDao.save(instructor2);
		
		EventCategory eventCategory1 = new EventCategory("Event Category1", account);
		eventCategoryDao.save(eventCategory1);
		
		EventCategory eventCategory2 = new EventCategory("Event Category2", account);
		eventCategoryDao.save(eventCategory2);
		
		EventAttribute eventAttribute1 = new EventAttribute("Event Attribute1", account);
		eventAttributeDao.save(eventAttribute1);
		
		EventAttribute eventAttribute2 = new EventAttribute("Event Attribute2", account);
		eventAttributeDao.save(eventAttribute2);
		
		Date dateStart1 = new DateTime().minusMonths(1).toDate();
		Date dateEnd1 = new DateTime(dateStart1).plusHours(1).toDate();
		Date dateStart2 = new DateTime().plusMonths(1).toDate();
		Date dateEnd2 = new DateTime(dateStart2).plusHours(1).toDate();
		
		
		List<Integer> daysOfWeek = Arrays.asList(1, 2, 3);
		List<Date> excludeDays = Arrays.asList(new Date(), new Date());
		
		Event ev1 = new Event(calendar1, false, EventRepeatType.WEEKLY, new Integer(1), new Date(),  new Integer(52), 
			daysOfWeek, excludeDays,  "summary", dateStart1, dateEnd1, new Integer(10), instructor1, 
			eventCategory1, eventAttribute1);
		
		Event ev2 = new Event(calendar1, false, EventRepeatType.WEEKLY, new Integer(1), new Date(),  new Integer(52), 
				daysOfWeek, excludeDays,  "summary", dateStart2, dateEnd2, new Integer(10), instructor1, 
				eventCategory1, eventAttribute1);
		
		eventDao.save(ev1);
		eventDao.save(ev2);
		
		Date today = new DateTime().minusMonths(1).toDate();
		DateFormat formatter = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss zzz");
	    String fromDate = formatter.format(today);
		
		Map<Date, Event> events = eventService.listEvents(account.getId(), calendar1.getId(), fromDate);
		Assert.assertNotNull(events);
		assert events.size() == 2;	
	}
	
	@Test
	public void listRepeatDailyEvents() throws Exception {
		
		User user = this.setUpUser();
		
		Account account = new Account("Account1");
		accountDao.save(account);
		
		this.setUp(account, user);
		
		Calendar calendar1 = new Calendar("Testing Calendar1", account);
		calendarDao.save(calendar1);
		
		Person instructor1 = new Person( account, "0", "testing1", "testing1@test.com", "INSTRUCTOR"); 
		personDao.save(instructor1);
		
		EventCategory eventCategory1 = new EventCategory("Event Category1", account);
		eventCategoryDao.save(eventCategory1);
		
		EventAttribute eventAttribute1 = new EventAttribute("Event Attribute1", account);
		eventAttributeDao.save(eventAttribute1);
		
		Date dateStart = new DateTime().plusDays(1).toDate();
		Date dateEnd = new DateTime(dateStart).plusHours(1).toDate();
		Date finalDate = new DateTime().plusWeeks(1).plusHours(1).toDate();
		List<Integer> daysOfWeek = Arrays.asList(1);
		
		Event ev1 = new Event(calendar1, true, EventRepeatType.DAILY, new Integer(1), finalDate,  null, 
			daysOfWeek, null,  "summary", dateStart, dateEnd, new Integer(10), instructor1, 
			eventCategory1, eventAttribute1);
		
		eventDao.save(ev1);
		
		Date today = new DateTime().minusMonths(1).toDate();
		DateFormat formatter = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss zzz");
	    String fromDate = formatter.format(today);
		
		Map<Date, Event> events = eventService.listEvents(account.getId(), calendar1.getId(), fromDate);
		Assert.assertNotNull(events);
	    assert events.size() == 7;
	}
	
	@Test
	public void listRepeatWeeklyEvents() throws Exception {
		
		User user = this.setUpUser();
		
		Account account = new Account("Account1");
		accountDao.save(account);
		
		this.setUp(account, user);
		
		Calendar calendar1 = new Calendar("Testing Calendar1", account);
		calendarDao.save(calendar1);
		
		Person instructor1 = new Person( account, "0", "testing1", "testing1@test.com", "INSTRUCTOR"); 
		personDao.save(instructor1);
		
		EventCategory eventCategory1 = new EventCategory("Event Category1", account);
		eventCategoryDao.save(eventCategory1);
		
		EventAttribute eventAttribute1 = new EventAttribute("Event Attribute1", account);
		eventAttributeDao.save(eventAttribute1);

		Date dateStart = calcNextMonday(new DateTime()).withTime(0, 0, 0, 0).plusHours(1).toDate();
		Date dateEnd = new DateTime(dateStart).plusHours(2).toDate();
		Date finalDate = new DateTime(dateEnd).plusWeeks(5).plusDays(1).toDate();
		List<Integer> daysOfWeek = Arrays.asList(1);
		
		Event ev1 = new Event(calendar1, true, EventRepeatType.WEEKLY, new Integer(1), finalDate,  null, 
			daysOfWeek, null,  "summary", dateStart, dateEnd, new Integer(10), instructor1, 
			eventCategory1, eventAttribute1);
		
		eventDao.save(ev1);
		
		Date today = new DateTime().minusMonths(1).toDate();
		DateFormat formatter = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss zzz");
	    String fromDate = formatter.format(today);
		
		Map<Date, Event> events = eventService.listEvents(account.getId(), calendar1.getId(), fromDate);
		Assert.assertNotNull(events);
	    assert events.size() == 6;
	}
		
	@Test
	public void listRepeatWeeklyEventsWithExcludedDates() throws Exception {
		
		User user = this.setUpUser();
		
		Account account = new Account("Account1");
		accountDao.save(account);
		
		this.setUp(account, user);
		
		Calendar calendar1 = new Calendar("Testing Calendar1", account);
		calendarDao.save(calendar1);
		
		Person instructor1 = new Person( account, "0", "testing1", "testing1@test.com", "INSTRUCTOR"); 
		personDao.save(instructor1);
		
		EventCategory eventCategory1 = new EventCategory("Event Category1", account);
		eventCategoryDao.save(eventCategory1);
		
		EventAttribute eventAttribute1 = new EventAttribute("Event Attribute1", account);
		eventAttributeDao.save(eventAttribute1);
		
		Date dateStart = calcNextMonday(new DateTime()).withTime(0, 0, 0, 0).toDate();
		Date dateEnd = new DateTime(dateStart).plusHours(2).toDate();
		Date finalDate = new DateTime(dateEnd).plusWeeks(5).plusDays(1).toDate();
		List<Integer> daysOfWeek = Arrays.asList(1);
		
		// Can't exclude start date only subsequent dates
		Date date1 = (new DateTime(dateStart)).plusWeeks(1).withTime(0, 0, 0, 0).toDate();
		Date date2 = (new DateTime(dateStart)).plusWeeks(2).withTime(0, 0, 0, 0).toDate();
		//Date date2 = (new DateTime(dateEnd)).withTime(0, 0, 0, 0).toDate();
		List<Date> excludeDays = Arrays.asList(date1, date2);
		
		Event ev1 = new Event(calendar1, true, EventRepeatType.WEEKLY, new Integer(1), finalDate,  null, 
			daysOfWeek, excludeDays,  "summary", dateStart, dateEnd, new Integer(10), instructor1, 
			eventCategory1, eventAttribute1);
		
		eventDao.save(ev1);
		
		Date today = new DateTime().minusMonths(1).toDate();
		DateFormat formatter = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss zzz");
	    String fromDate = formatter.format(today);
		
		Map<Date, Event> events = eventService.listEvents(account.getId(), calendar1.getId(), fromDate);
		Assert.assertNotNull(events);
	    assert events.size() == 4;
	}
	
	@Test
	public void listRepeatWeeklyEventsOnMondaysAndTuesday() throws Exception {
		
		User user = this.setUpUser();
		
		Account account = new Account("Account1");
		accountDao.save(account);
		
		this.setUp(account, user);
		
		Calendar calendar1 = new Calendar("Testing Calendar1", account);
		calendarDao.save(calendar1);
		
		Person instructor1 = new Person( account, "0", "testing1", "testing1@test.com", "INSTRUCTOR"); 
		personDao.save(instructor1);
		
		EventCategory eventCategory1 = new EventCategory("Event Category1", account);
		eventCategoryDao.save(eventCategory1);
		
		EventAttribute eventAttribute1 = new EventAttribute("Event Attribute1", account);
		eventAttributeDao.save(eventAttribute1);
		
		Date dateStart = calcNextMonday(new DateTime()).withTime(0, 0, 0, 0).plusHours(1).toDate();
		Date dateEnd = new DateTime(dateStart).plusHours(2).toDate();
		Date finalDate = new DateTime(dateEnd).plusWeeks(5).plusDays(1).toDate();
		
		List<Integer> daysOfWeek = Arrays.asList(1, 2);
		List<Date> excludeDays = null;
		
		Event ev1 = new Event(calendar1, true, EventRepeatType.WEEKLY, new Integer(1), finalDate,  null, 
			daysOfWeek, excludeDays,  "summary", dateStart, dateEnd, new Integer(10), instructor1, 
			eventCategory1, eventAttribute1);
		
		eventDao.save(ev1);
		
		Date today = new DateTime().minusMonths(1).toDate();
		DateFormat formatter = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss zzz");
	    String fromDate = formatter.format(today);
		
		Map<Date, Event> events = eventService.listEvents(account.getId(), calendar1.getId(), fromDate);
		Assert.assertNotNull(events);
	    assert events.size() == 12;
	}
	
	@Test
	public void listRepeatMonthlyEvents() throws Exception {
		
		User user = this.setUpUser();
		
		Account account = new Account("Account1");
		accountDao.save(account);
		
		this.setUp(account, user);
		
		Calendar calendar1 = new Calendar("Testing Calendar1", account);
		calendarDao.save(calendar1);
		
		Person instructor1 = new Person( account, "0", "testing1", "testing1@test.com", "INSTRUCTOR"); 
		personDao.save(instructor1);
		
		EventCategory eventCategory1 = new EventCategory("Event Category1", account);
		eventCategoryDao.save(eventCategory1);
		
		EventAttribute eventAttribute1 = new EventAttribute("Event Attribute1", account);
		eventAttributeDao.save(eventAttribute1);
		
		Date dateStart = calcNextMonday(new DateTime()).withTime(0, 0, 0, 0).toDate();
		Date dateEnd = new DateTime(dateStart).plusHours(1).toDate();
		Date finalDate = new DateTime(dateStart).plusMonths(4).plusDays(1).toDate();
		List<Integer> daysOfWeek = Arrays.asList(1,2,3,4,5,6,7);
		
		Event ev1 = new Event(calendar1, true, EventRepeatType.MONTHLY, new Integer(1), finalDate,  null, 
			daysOfWeek, null,  "summary", dateStart, dateEnd, new Integer(10), instructor1, 
			eventCategory1, eventAttribute1);
		
		eventDao.save(ev1);
		
		Date today = new DateTime().minusMonths(1).toDate();
		DateFormat formatter = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss zzz");
	    String fromDate = formatter.format(today);
		
		Map<Date, Event> events = eventService.listEvents(account.getId(), calendar1.getId(), fromDate);
		Assert.assertNotNull(events);
    	assert events.size() == 5;
	}

    private DateTime calcNextMonday(DateTime dateTime) {
        if (dateTime.getDayOfWeek() >= DateTimeConstants.MONDAY) {
            dateTime = dateTime.plusWeeks(1);
        }
        return dateTime.withDayOfWeek(DateTimeConstants.MONDAY);
    }
	
}
