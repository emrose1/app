package com.studio.bookings.util;

import com.studio.bookings.dao.BaseDao;
import com.studio.bookings.dao.BookingDao;
import com.studio.bookings.dao.EventAttributeDao;
import com.studio.bookings.dao.EventCategoryDao;
import com.studio.bookings.dao.EventDao;
import com.studio.bookings.entity.AccessControlList;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.User;
import com.studio.bookings.enums.Permission;
import com.studio.bookings.enums.UserType;
import com.studio.bookings.service.AccountService;
import com.studio.bookings.service.BaseService;

public class LoadDummyData extends BaseService {
	
	public static EventDao eventDao = new EventDao();
	public static EventAttributeDao eventAttributeDao = new EventAttributeDao();
	public static EventCategoryDao eventCategoryDao = new EventCategoryDao();

	public static BookingDao bookingDao = new BookingDao();
	
	public static AccountService accountService = new AccountService();
	BaseDao<Account> accountDao = new BaseDao<Account>(Account.class);
	
	public void initSetup() {
		
		Account account = accountService.insertAccount("Testing Account", "test", "admin", "123", "ADMIN");
		Account account2 = accountService.insertAccount("Testing Account2", "test", "admin", "123", "ADMIN");
		Account account3 = accountService.insertAccount("Testing Account3", "test", "admin", "123", "ADMIN");
		
	
		/*CalendarDao calendarDao = new CalendarDao();
		EventDao eventDao = new EventDao();
		
		// TODO Create new owner and Settings Service
		Settings setting = new Settings();
		Key<Settings> settingsKey = settingsDao.save(setting);
		Owner owner = new Owner("Big C's", settingsKey);
		Key<Owner> ownerKey = ownerDao.save(owner);
		Owner ownerFetched = ownerDao.find(ownerKey);
	
		Calendar calendar1 = new Calendar("calendar1", ownerFetched);
		calendarDao.save(calendar1);
	
		Calendar calendar2 = new Calendar("calendar2", ownerFetched);
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
		

		/*Permission bookingPermission = Permission.BOOKING;
		Permission eventPermission = Permission.EVENT;
		Permission calendarPermission = Permission.CALENDAR;

		AccessControlList adminBooking = new AccessControlList(bookingPermission, true, true, true, true, UserType.ADMIN, account);
		AccessControlList adminEvent = new AccessControlList(eventPermission, false, false, false, false, UserType.ADMIN, account);
		AccessControlList adminCalendar = new AccessControlList(calendarPermission, true, true, true, true, UserType.ADMIN, account);

		AccessControlList ownerBooking = new AccessControlList(bookingPermission, true, true, true, true, UserType.OWNER, account);
		AccessControlList ownerEvent = new AccessControlList(eventPermission, true, true, true, true, UserType.OWNER, account);
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
		accessControlListDao.save(organizerCalendar);*/

		User user1 = new User("admin", "123", "ADMIN", account);
		User user2 = new User("owner", "123", "OWNER", account);
		User user3 = new User("organizer", "123", "INSTRUCTOR", account);
		User user4 = new User("attendee", "123", "ATTENDEE", account);

		userDao.save(user1);
		userDao.save(user2);
		userDao.save(user3);
		userDao.save(user4);
		
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
