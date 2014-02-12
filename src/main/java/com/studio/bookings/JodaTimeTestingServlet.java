package com.studio.bookings;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JodaTimeTestingServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		/*UserDao userDao = new UserDao();
		if (userDao.findAll().size() == 0) {
			LoadDummyData ldd = new LoadDummyData();
			ldd.initSetup();
		}
		
		CalendarDao calendarDao = new CalendarDao();
		EventDao eventDao = new EventDao();
		EventInstanceDao eventInstanceDao = new EventInstanceDao();
		Event parentEvent = new Event();
		
		List<Calendar> calendars = calendarDao.findAll();
		  for (Calendar cal : calendars) {
			  List<Event> events = eventDao.findEventsByCalendar(cal);
			  for (Event event : events) {
				  parentEvent = event;
				  event.getCalendar();
				  event.getCalendar().getDescription();
				  //event.getEventCategory();  
			  }
		  }
		
		PrintWriter rw = resp.getWriter();
		resp.setContentType("text/plain");

		String dateStart = "14/01/2012 09:29:58";
		String dateStop = "15/01/2012 10:31:48";

		DateTime dt = new DateTime();
		int dtFinalYear = dt.plusYears(10).getYear();
		DateTime dtFinalRepeatDate = new DateTime(dtFinalYear, 12, 31, 23, 59);

		//rw.println(dtFinalRepeatDate);

		DateTime currentDateTime = new DateTime();

		List<EventInstance> eventInstances = new ArrayList<EventInstance>();
		 

		while (currentDateTime.isBefore(dtFinalRepeatDate.plus(Period
				.months(-1)))) {
			currentDateTime = currentDateTime.plus(Period.months(1));
			//rw.println(currentDateTime);
			Date date = currentDateTime.toDate();
			Long dateTime = date.getTime();
			//EventInstance ei = new EventInstance(date, dateTime, parentEvent);
			//eventInstances.add(ei);
		}
		
		
		
		rw.println(eventInstances.size());
		rw.println("=========================");
		eventInstanceDao.saveCollection(eventInstances);
		
		rw.println("Event Instances");
		rw.println(parentEvent.toString());
		List<EventInstance> savedEventInstances = eventInstanceDao.findEventInstancesByEvent(parentEvent);
		rw.println("bla");
		rw.println(savedEventInstances.size());
		for (EventInstance savedEventInstance : savedEventInstances) {
			rw.println(savedEventInstances.indexOf(savedEventInstance));
			rw.println(savedEventInstance.toString());
		}

		// SimpleDateFormat format = new
		// SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

		DateTimeFormatter formatter = DateTimeFormat
				.forPattern("dd/MM/yyyy HH:mm:ss");
		DateTime dt1 = formatter.parseDateTime(dateStart);
		DateTime dt2 = formatter.parseDateTime(dateStop);

		DateTime plusPeriod = dt1.plus(Period.days(1));

		rw.println(Days.daysBetween(dt1, dt2).getDays() + " days, ");
		rw.println(Hours.hoursBetween(dt1, dt2).getHours() % 24 + " hours, ");
		rw.println(Minutes.minutesBetween(dt1, dt2).getMinutes() % 60
				+ " minutes, ");
		rw.println(Seconds.secondsBetween(dt1, dt2).getSeconds() % 60
				+ " seconds.");
*/
	}
}
