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

import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.Event;
import com.studio.bookings.entity.EventAttribute;
import com.studio.bookings.entity.EventCategory;
import com.studio.bookings.entity.EventItem;
import com.studio.bookings.entity.EventRepeatType;
import com.studio.bookings.entity.Owner;
import com.studio.bookings.service.EventTestingService;

/**
 * Servlet implementation class EventServiceTestingServlet
 */
@SuppressWarnings("serial")
public class EventServiceTestingServlet extends HttpServlet {
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter rw = response.getWriter();
		response.setContentType("text/plain");
		
		EventTestingService ets = new EventTestingService();
		
		String organizer = new String("organizer1");
		String summary = new String("summary1");
		String duration = new String ("1 hour");
		String maxAttendees = new String ("10");
		String repeatEvent = new String("true");
		String eventRepeatType = new String("DAILY");
		String finalRepeatEvent = new String ("12:20 05 03 2014");
		
		
		// CREATE OWNER
		Owner owner = ets.createOwner("Big C's Calendar");
		Owner ownerFetched = ets.getOwnerById(owner.getId());
		
		Long ownerFetchedId = ownerFetched.getId();

		
		// CREATE CALENDARS
		Calendar cal1 = null;
		try {
			cal1 = ets.addCalendar("calendar1", ownerFetchedId);
		} catch (Exception e4) {
			// TODO Auto-generated catch block
			rw.println(e4);
		}
		try {
			Calendar cal2 = ets.addCalendar("calendar2", ownerFetchedId);
		} catch (Exception e3) {
			// TODO Auto-generated catch block
			rw.println(e3);
		}
		
		rw.println("ownerFetched: " + ownerFetched);
		
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
			category = ets.addEventCategory(pMatwork, calId1);
			category = ets.addEventCategory(pReformer, calId1);
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			rw.println(e2);
		}

		
		// CREATE EVENT ATTRIBUTE
		EventAttribute attribute = null;
		try {
			attribute = ets.addEventAttribute(beginners, calId1);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			rw.println(e1);
		}
			
		// ADD EVENTS
		try {
			List<EventItem> result = ets.addEvent(organizer, summary, calId1, start, 
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
		List<Calendar> calList = ets.listCalendars();
		
		for(Calendar calListItem : calList) {
			rw.println(calListItem);
			
			List<EventItem> eventList = ets.listEvents(calListItem.getId());
			
			rw.println("Event Items");
			for(EventItem eventItem : eventList) {
				rw.println(eventItem.getEventItemDetails());
			}
			
			rw.println("Event Repeat Types");
			List<EventRepeatType> ert = ets.listEventRepeatTypes();
			for(EventRepeatType ertItem : ert) {
				rw.println(ertItem);
			}
			
			rw.println("Event Attributes");
			List<EventAttribute> ea = ets.listEventAttributes(calListItem.getId());
			for(EventAttribute eaItem : ea) {
				rw.println(eaItem);
			}
			
			rw.println("Event Categories");
			List<EventCategory> ec = ets.listEventCategories(calListItem.getId());
			for(EventCategory ecItem : ec) {
				rw.println(ecItem);
			}
			
		}
		
		
	}
}
