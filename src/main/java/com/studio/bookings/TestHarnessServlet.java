package com.studio.bookings;

import static com.studio.bookings.util.OfyService.ofy;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
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
import com.studio.bookings.service.EventService;



public class TestHarnessServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

	  PrintWriter rw = resp.getWriter();
	    resp.setContentType("text/plain");

	  CalendarDao calendarDao = new CalendarDao();
	  EventDao eventDao = new EventDao();
	  UserDao userDao = new UserDao();
	  UserTypeDao userTypeDao = new UserTypeDao();
	  AccessControlListDao accessControlListDao = new AccessControlListDao();
	  
	  
	  rw.println("EVENTS BY CALENDAR");
	  
	  List<Calendar> calendars = calendarDao.findAll();
	  for (Calendar cal : calendars) {
		  List<Event> events = ofy().load().type(Event.class).ancestor(cal.getKey()).list();

		  rw.println("calendar " + cal + ": Events");
		  for (Event event : events) {
			  rw.println(event);
		  }
	  }
	  
	  rw.println("");
	  rw.println("==========");
	  rw.println("");
	  rw.println("USERS");

	  List <UserType> userTypes = userTypeDao.findAll();
	  for (UserType ut : userTypes) {
		  List<User> users = userDao.getUsersByUserType(ut);
		  
		  rw.println("user type " + ut + ": Users");
		  for (User user : users) {
			  rw.println(user.getUsername());
		  }
		  
	  }
	  
	  rw.println("");
	  rw.println("==========");
	  rw.println("");
	  rw.println("ACL");
	  
	  AccessControlList acl = new AccessControlList();
	  for (UserType ut : userTypes) {
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
	  }
	  rw.println("==========");
	  rw.println("acl list");
	  
	  List<AccessControlList> aclList = accessControlListDao.findAll();
	  for (AccessControlList a : aclList) {
		  rw.println(a.toString());
	  }
	  
  }
}
