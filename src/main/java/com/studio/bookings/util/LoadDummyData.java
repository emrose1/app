package com.studio.bookings.util;

import java.util.Date;

import com.studio.bookings.dao.AccessControlListDao;
import com.studio.bookings.dao.BookingDao;
import com.studio.bookings.dao.CalendarDao;
import com.studio.bookings.dao.EventAttributeDao;
import com.studio.bookings.dao.EventCategoryDao;
import com.studio.bookings.dao.EventDao;
import com.studio.bookings.dao.UserDao;
import com.studio.bookings.dao.UserTypeDao;
import com.studio.bookings.entity.AccessControlList;
import com.studio.bookings.entity.Booking;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.Event;
import com.studio.bookings.entity.EventAttribute;
import com.studio.bookings.entity.EventCategory;
import com.studio.bookings.entity.User;
import com.studio.bookings.entity.UserType;
import com.studio.bookings.enums.Permission;

public class LoadDummyData {
	
	public static EventDao eventDao = new EventDao();
	public static EventAttributeDao eventAttributeDao = new EventAttributeDao();
	public static EventCategoryDao eventCategoryDao = new EventCategoryDao();
	public static CalendarDao calDao = new CalendarDao();
	public static UserDao userDao = new UserDao();
	public static UserTypeDao userTypeDao = new UserTypeDao();
	public static AccessControlListDao accessControlListDao = new AccessControlListDao();
	public static BookingDao bookingDao = new BookingDao();
	
	public void initSetup() {
	
		CalendarDao calendarDao = new CalendarDao();
		EventDao eventDao = new EventDao();
	
		Calendar calendar1 = new Calendar("calendar1");
		calendarDao.save(calendar1);
	
		Calendar calendar2 = new Calendar("calendar2");
		Calendar calendar3 = calendarDao.save(calendar2);
	
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
		
	
		Event event1 = new Event("organizer1", "event 1 of calendar 1", new Date(), new Date(), 10, calendar1, ec1, ea1);
		eventDao.save(event1);
	
		Event event2 = new Event("organizer1", "event 1 of calendar 2", new Date(), new Date(), 10, calendar2, ec2, ea2);
		eventDao.save(event2);
	
		Event event3 = new Event("organizer1", "event 2 of calendar 1", new Date(), new Date(), 10, calendar1, ec1, ea1);
		eventDao.save(event3);
	
		Event event4 = new Event("organizer1", "event 2 of calendar 2", new Date(), new Date(), 10, calendar2, ec2, ea2);
		eventDao.save(event4);
		

		UserType admin = new UserType("admin");
		UserType owner = new UserType("owner");
		UserType organizer = new UserType("organizer");
		UserType attendee = new UserType("attendee");

		userTypeDao.save(admin);
		userTypeDao.save(owner);
		userTypeDao.save(organizer);
		userTypeDao.save(attendee);

		Permission bookingPermission = Permission.BOOKING;
		Permission eventPermission = Permission.EVENT;
		Permission calendarPermission = Permission.CALENDAR;

		AccessControlList adminBooking = new AccessControlList(bookingPermission, true, true, true, true, admin);
		AccessControlList adminEvent = new AccessControlList(eventPermission, false, false, false, false, admin);
		AccessControlList adminCalendar = new AccessControlList(calendarPermission, true, true, true, true, admin);

		AccessControlList ownerBooking = new AccessControlList(bookingPermission, true, true, true, true, owner);
		AccessControlList ownerEvent = new AccessControlList(eventPermission, true, true, true, true, owner);
		AccessControlList ownerCalendar = new AccessControlList(calendarPermission, false, false, false, false, owner);

		AccessControlList organizerBooking = new AccessControlList(bookingPermission, true, true, true, true, organizer);
		AccessControlList organizerEvent = new AccessControlList(eventPermission, false, false, false, false, organizer);
		AccessControlList organizerCalendar = new AccessControlList(calendarPermission, false, false, false, false, organizer);

		accessControlListDao.save(adminBooking);
		accessControlListDao.save(adminEvent);
		accessControlListDao.save(adminCalendar);

		accessControlListDao.save(ownerBooking);
		accessControlListDao.save(ownerEvent);
		accessControlListDao.save(ownerCalendar);

		accessControlListDao.save(organizerBooking);
		accessControlListDao.save(organizerEvent);
		accessControlListDao.save(organizerCalendar);

		User user1 = new User("admin", "123", admin);
		User user2 = new User("owner", "123", owner);
		User user3 = new User("organizer", "123", organizer);
		User user4 = new User("attendee", "123", attendee);

		userDao.save(user1);
		userDao.save(user2);
		userDao.save(user3);
		userDao.save(user4);
		
		Booking booking1 = new Booking(user1, event1);
		Booking booking2 = new Booking(user2, event1);
		Booking booking3 = new Booking(user3, event1);
		Booking booking4 = new Booking(user4, event1);
		
		bookingDao.save(booking1);
		bookingDao.save(booking2);
		bookingDao.save(booking3);
		bookingDao.save(booking4);
	
	}
}
