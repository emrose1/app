package com.studio.bookings;

import java.io.IOException;


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.studio.bookings.dao.BaseDao;
import com.studio.bookings.dao.BookingDao;
import com.studio.bookings.dao.ChildBaseDao;
import com.studio.bookings.dao.EventAttributeDao;
import com.studio.bookings.dao.EventCategoryDao;
import com.studio.bookings.dao.EventDao;
import com.studio.bookings.dao.EventItemDao;
import com.studio.bookings.dao.InstructorDao;
import com.studio.bookings.entity.AccessControlList;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.Booking;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.Event;
import com.studio.bookings.entity.Person;
import com.studio.bookings.enums.Permission;
import com.studio.bookings.enums.UserType;
import com.studio.bookings.util.LoadDummyData;



public class TestHarnessServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
	  
	  ChildBaseDao<Calendar, Account> calendarDao = new ChildBaseDao<Calendar, Account>(Calendar.class, Account.class);
	  ChildBaseDao<Person, Account> userDao = new ChildBaseDao<Person, Account>(Person.class, Account.class);
	  ChildBaseDao<AccessControlList, Account> accessControlListDao = new ChildBaseDao<AccessControlList, Account>(AccessControlList.class, Account.class);


	 
	  	if (userDao.list().size() == 0) {
	  		LoadDummyData ldd = new LoadDummyData();
	  		ldd.initSetup();
	  	}

	  PrintWriter rw = resp.getWriter();
	    resp.setContentType("text/plain");

	  
	  /*
	  rw.println("EVENTS BY CALENDAR");
	  
	  List<Calendar> calendars = calendarDao.findAll();
	  for (Calendar cal : calendars) {

		  List<Event> events = eventDao.findEventsByCalendar(cal);

		  rw.println("calendar " + cal + ": Events");
		  rw.println("");
		  for (Event event : events) {
			  rw.println(event);
			  rw.println("calendar: " + event.getCalendar());
			  rw.println("description: " + event.getCalendar().getDescription());
			  
			  //rw.println("event category: " + event.getEventCategory());
			  
			  rw.println("");
			  rw.println("==========");
			  rw.println("");
			  //rw.println("Booking for " + event.getSummary());

			  List<Booking> bookings = bookingDao.getBookingsByEvent(event);
			  for (Booking b : bookings) {
				  rw.println(b.toString());
			  }
		  }
		  rw.println("");
		  rw.println("==========");
	  }*/
	  
	  rw.println("");
	  rw.println("==========");
	  rw.println("");
	  rw.println("USERS");
	  
	  //TODO FIX
	  //List <UserType> userTypes = userTypeDao.list();
	  /*for (UserType ut : userTypes) {
		  //TODO FIX List<User> users = userDao.getUsersByUserType(ut);
		  
		  rw.println("user type " + ut + ": Users");
		  for (User user : users) {
			  
			  rw.println(user.getUsername());
			  rw.println("");
			  rw.println("==========");
			  rw.println("");
			  
			  rw.println("Booking for " + user.getUsername());
			  List<Booking> bookings = bookingDao.getBookingsByUser(user);
			  for (Booking b : bookings) {
				  rw.println(b.toString());
			  }
		  }
		  
	  }*/
	  
	  rw.println("");
	  rw.println("==========");
	  rw.println("");
	  rw.println("ACL");
	  
	  AccessControlList acl = new AccessControlList();
	  //TODO FIX
	  /*for (UserType ut : userTypes) {
		  rw.println("user type " + ut + ": ACL for EVENT");
		  try {
			acl = accessControlListDao.getByUserTypeAndPermission(ut, Permission.EVENT);
			//rw.println(acl);
			rw.println(acl.toString());
		  } catch (Exception e) {
			// TODO Auto-generated catch block
			rw.println("nada");
			String s = e.toString();
			rw.println(s);
		  }
	  }*/
	  rw.println("");
	  rw.println("==========");
	  rw.println("");
	  rw.println("acl list");
	  
	  List<AccessControlList> aclList = accessControlListDao.list();
	  for (AccessControlList a : aclList) {
		  rw.println(a.toString());
	  }
	  
  }
}
