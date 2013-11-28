package com.studio.bookings;

import static com.studio.bookings.util.OfyService.ofy;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.studio.bookings.dao.CalendarDao;
import com.studio.bookings.dao.EventDao;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.Event;

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

	  UserService userService = UserServiceFactory.getUserService();
	  User currentUser = userService.getCurrentUser();

	  if (currentUser != null) {
		  rw.println("Hello, " + currentUser.getNickname());
	  } else {
		  resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
	  }


	  rw.println("calendar 1 Events");
	  for (Event event : calendar1Events) {
		  rw.println(event.getSummary());
	  }

	  rw.println("calendar 2 Events");
	  for (Event event : calendar2Events) {
		  rw.println(event.getSummary());
	  }



    rw.println("Hello, ");

  }
}
