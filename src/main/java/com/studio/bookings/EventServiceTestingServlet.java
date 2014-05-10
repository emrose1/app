package com.studio.bookings;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;

import com.googlecode.objectify.Ref;
import com.studio.bookings.dao.EventItemDao;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.EventAttribute;
import com.studio.bookings.entity.EventCategory;
import com.studio.bookings.entity.EventItem;
import com.studio.bookings.entity.Instructor;
import com.studio.bookings.enums.EventRepeatType;

/**
 * Servlet implementation class EventServiceTestingServlet
 */
@SuppressWarnings("serial")
public class EventServiceTestingServlet extends HttpServlet {
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter rw = response.getWriter();
		response.setContentType("text/plain");
		
		/*EventTestingService ets = new EventTestingService();
		
		
		// CREATE OWNER Account
		Account owner = ets.insertAccount("Big C's Calendar");		
		Long ownerFetchedId = owner.getId();
		
		// CREATE CALENDARS
		Calendar cal1 = null;
		try {
			cal1 = ets.insertCalendar("calendar1", ownerFetchedId);
			rw.println(cal1.getId());
			rw.println(cal1);
		} catch (Exception e4) {
			// TODO Auto-generated catch block
			rw.println(e4);
		}
		try {
			Calendar cal2 = ets.insertCalendar("calendar2", ownerFetchedId);
			rw.println(cal2.getId());
			rw.println(cal2);
		} catch (Exception e3) {
			// TODO Auto-generated catch block
			rw.println(e3);
		}
		
		rw.println("ownerFetched: " + owner);
		
		Instructor inst2 = new Instructor();
		try {
			inst2 = ets.addInstructor("teach", "email@email.com", "description", cal1.getId(), owner.getId());
		} catch (Exception e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
		Long instructor = new Long(inst2.getId());
		String summary = new String("summary1");
		String duration = new String ("1 hour");
		String maxAttendees = new String ("10");
		String repeatEvent = new String("true");
		String eventRepeatType = new String("DAILY");
		String finalRepeatEvent = new String ("12:20 05 03 2014");

		
		
		Long calId1 = cal1.getId();
		
		// CREATE DATES
		String start = new String("12:30 05 02 2014");
		DateFormat formatter = new SimpleDateFormat("HH:mm dd MM yyyy");
		
		String startMillis = new String("2014 FEB 05");
		DateFormat formatterMillis = new SimpleDateFormat("yyyy MMM dd");

		Date eventStartDateTime = new Date();
		Date eventStartDateTimeMillis = new Date();
		
		try {
			eventStartDateTime = new DateTime(formatter.parse(start)).toDate();
			eventStartDateTimeMillis =  new DateTime(formatterMillis.parse(startMillis)).toDate();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long startTime = eventStartDateTimeMillis.getTime();
		
		String pMatwork = "Event Category 1";
		String pReformer = "Event Category 2";
		String beginners = "Event Attribute";
		
		// CREATE EVENT CATEGORY
		EventCategory category = null;
		try {
			category = ets.addEventCategory(pMatwork, calId1, ownerFetchedId);
			category = ets.addEventCategory(pReformer, calId1, ownerFetchedId);
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			rw.println(e2);
		}

		
		// CREATE EVENT ATTRIBUTE
		EventAttribute attribute = null;
		try {
			attribute = ets.addEventAttribute(beginners, calId1, ownerFetchedId);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			rw.println(e1);
		}
			
		// ADD EVENTS
		try {
			List<EventItem> result = ets.addEvent(ownerFetchedId, instructor, summary, calId1, start, 
					startTime, duration, maxAttendees, pMatwork, 
					beginners, repeatEvent, eventRepeatType, finalRepeatEvent);
			for (EventItem r : result) {
				rw.println(r);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			rw.println(e);
		}
		
		
		// List Calendars by ID
		EventItemDao eventItemDao = new EventItemDao();
		try {
			rw.println("Calendars by  " + ownerFetchedId);
			List<Calendar> calList = ets.listCalendars(ownerFetchedId);
			
			for(Calendar calListItem : calList) {
				rw.println(calListItem);
				
				List<EventItem> eventList = ets.listEvents(ownerFetchedId, calListItem.getId());
				
				rw.println("Event Items");
				for(EventItem eventItem : eventList) {
					rw.println(eventItem.getEventItemDetails());
					rw.println(eventItem.getEventItemDetails().getEventCategory());
					rw.println(Ref.create(eventItem.getEventItemDetails().getEventCategory()));
				}
				
				rw.println("Event Repeat Types");
				List<EventRepeatType> ert = ets.listEventRepeatTypes();
				for(EventRepeatType ertItem : ert) {
					rw.println(ertItem);
				}
				
				rw.println("Event Attributes");
				List<EventAttribute> ea = ets.listEventAttributes(ownerFetchedId, calListItem.getId());
				for(EventAttribute eaItem : ea) {
					rw.println(eaItem);
				}
				
				rw.println("Event Categories");
				List<EventCategory> ec = ets.listEventCategories(ownerFetchedId, calListItem.getId());
				for(EventCategory ecItem : ec) {
					rw.println(ecItem);
					
					rw.println("Events in Event Category: " + ecItem);
					
					List<EventItem> eiList = eventItemDao.findEventItemsByAttributes(calListItem, Ref.create(ecItem));
					rw.println(Ref.create(ecItem));
					for (EventItem ei : eiList) {
						rw.println(ei);
					}
				}
				
			}
		} catch (Exception e) {
			rw.println(e);
		}*/
			
		
	}
}