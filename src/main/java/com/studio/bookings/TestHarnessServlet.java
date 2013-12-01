package com.studio.bookings;

import static com.studio.bookings.util.OfyService.ofy;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.studio.bookings.dao.AccessControlListDao;
import com.studio.bookings.dao.CalendarDao;
import com.studio.bookings.dao.EventDao;
import com.studio.bookings.dao.UserDao;
import com.studio.bookings.dao.UserTypeDao;
import com.studio.bookings.entity.AccessControlList;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.Event;
import com.studio.bookings.entity.User;
import com.studio.bookings.entity.UserType;
import com.studio.bookings.enums.Permission;

public class TestHarnessServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

	  PrintWriter rw = resp.getWriter();
	    resp.setContentType("text/plain");

	  CalendarDao calendarDao = new CalendarDao();
	  EventDao eventDao = new EventDao();

	  Calendar calendar1 = new Calendar("calendar1");
	  calendarDao.save(calendar1);

	  Calendar calendar2 = new Calendar("calendar2");
	  calendarDao.save(calendar2);


	  Event event1 = new Event("organizer1", "event 1 of calendar 1", new Date(), new Date(), 10, calendar1);
	  eventDao.save(event1);

	  Event event2 = new Event("organizer1", "event 1 of calendar 2", new Date(), new Date(), 10, calendar2);
	  eventDao.save(event2);

	  Event event3 = new Event("organizer1", "event 2 of calendar 1", new Date(), new Date(), 10, calendar1);
	  eventDao.save(event3);

	  Event event4 = new Event("organizer1", "event 2 of calendar 2", new Date(), new Date(), 10, calendar2);
	  eventDao.save(event4);

	  List<Event> calendar1Events = ofy().load().type(Event.class).ancestor(calendar1.getKey()).list();

	  List<Event> calendar2Events = ofy().load().type(Event.class).ancestor(calendar2.getKey()).list();

	  rw.println("calendar 1 Events");
	  for (Event event : calendar1Events) {
		  rw.println(event.getSummary());
	  }

	  rw.println("calendar 2 Events");
	  for (Event event : calendar2Events) {
		  rw.println(event.getSummary());
	  }
	  
	  UserType admin = new UserType("admin");
	  UserType owner = new UserType("owner");
	  UserType organizer = new UserType("organizer");
	  UserType attendee = new UserType("attendee");
	  
	  UserTypeDao userTypeDao = new UserTypeDao();
	  
	  userTypeDao.save(admin);
	  userTypeDao.save(owner);
	  userTypeDao.save(organizer);
	  userTypeDao.save(attendee);
	  
	  Permission bookingPermission = Permission.BOOKING;
	  Permission eventPermission = Permission.EVENT;
	  Permission calendarPermission = Permission.CALENDAR;
	 
	  AccessControlList adminBooking = new AccessControlList(bookingPermission, true, true, true, true, admin);
	  AccessControlList adminEvent = new AccessControlList(eventPermission, true, true, true, true, admin);
	  AccessControlList adminCalendar = new AccessControlList(calendarPermission, true, true, true, true, admin);
	  
	  AccessControlList ownerBooking = new AccessControlList(bookingPermission, true, true, true, true, owner);
	  AccessControlList ownerEvent = new AccessControlList(eventPermission, true, true, true, true, owner);
	  AccessControlList ownerCalendar = new AccessControlList(calendarPermission, false, false, false, false, owner);

	  AccessControlList organizerBooking = new AccessControlList(bookingPermission, true, true, true, true, organizer);
	  AccessControlList organizerEvent = new AccessControlList(eventPermission, true, true, true, true, organizer);
	  AccessControlList organizerCalendar = new AccessControlList(calendarPermission, false, false, false, false, organizer);

	  
	  AccessControlListDao accessControlListDao = new AccessControlListDao();
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
	  
	  UserDao userDao = new UserDao();
	  userDao.save(user1);
	  userDao.save(user2);
	  userDao.save(user3);
	  userDao.save(user4);
	  
	  List<User> userAdminUserType = ofy().load().type(User.class).ancestor(admin.getKey()).list();
	  
	  rw.println("admin users");
	  for (User user : userAdminUserType) {
		  rw.println(user.getUsername());
	  }
	  
	  List<User> userOwnerUserType = ofy().load().type(User.class).ancestor(owner.getKey()).list();
	  
	  rw.println("owner users");
	  for (User user : userOwnerUserType) {
		  rw.println(user.getUsername());
	  }

  }
}
