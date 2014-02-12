package com.studio.bookings.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Named;

import org.joda.time.DateTime;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.studio.bookings.dao.CalendarDao;
import com.studio.bookings.dao.EventAttributeDao;
import com.studio.bookings.dao.EventCategoryDao;
import com.studio.bookings.dao.EventDao;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.Event;
import com.studio.bookings.entity.EventAttribute;
import com.studio.bookings.entity.EventCategory;
import com.studio.bookings.entity.EventRepeatType;
import com.studio.bookings.entity.Owner;
import com.studio.bookings.util.Constants;

/**
 * Defines v1 of a booking API, which provides simple "greeting" methods.
 */
/*@Api(
    name = "booking",
    version = "v1",
    scopes = {Constants.EMAIL_SCOPE},
    clientIds = {Constants.WEB_CLIENT_ID},
    audiences = {Constants.ANDROID_AUDIENCE}
)*/

public class EventService {
	
	/*public static ArrayList<Event> events = new ArrayList<Event>();
	public static EventDao eventDao = new EventDao();
	public static CalendarDao calDao = new CalendarDao();
	public static EventAttributeDao eventAttributeDao = new EventAttributeDao();
	public static EventCategoryDao eventCategoryDao = new EventCategoryDao();
	
	@ApiMethod(name = "calendar.listCalendars")
	public List<Calendar> listCalendars() {
		return calDao.findAll();
	}
	
	@ApiMethod(name = "calendar.listEvents")
	public List<Event> listEvents(@Named("calendarId") String calendarId) {
		Long l = new Long(calendarId);
		Calendar cal =  calDao.find(l);
		List<Event> events = eventDao.findEventsByCalendar(cal);
	    return events;
	}
 
	@ApiMethod(name = "calendar.addEvent", path="calendar.addEvent", httpMethod = "post")
	public Event addEvent(
			@Named("organizer") String organizer, 
			@Named("summary") String summary, 
			@Named("calendarId") String calendarId,
			@Named("startDateTime") String startDateTime,
			@Named("startDate") Long startDate,
			@Named("duration") String duration,
			@Named("maxAttendees") String maxAttendees,
			@Named("repeatEvent") Boolean repeatEvent,
			@Named("eventRepeatType") EventRepeatType eventRepeatType
			) throws ParseException {
		
		Long calId = new Long(calendarId);
		Calendar cal =  calDao.find(calId);
		DateFormat formatter = new SimpleDateFormat("yyyy MMM dd HH mm zzz");
		String newStartDateTime = new String(startDateTime);
		DateTime dt = new DateTime(formatter.parse(newStartDateTime));
		
		
		Long eventStartDate = new Long(startDate);
		String eventDuration = new String(duration);
		Integer eventMaxAttendees = new Integer(maxAttendees);
		EventCategory ec1 = new EventCategory("Pilates Matwork", cal);
		EventCategoryDao ecDao = new EventCategoryDao();
		ecDao.save(ec1);
		EventAttribute ea1 = new EventAttribute("Beginners", cal);
		Boolean repeat = new Boolean(repeatEvent);
		EventRepeatType repeatType = eventRepeatType;
		
		EventAttributeDao eaDao = new EventAttributeDao();
		eaDao.save(ea1);
		Event response = new Event(cal, repeat, repeatType);
	    eventDao.save(response);	    
	    return response;
	}
	
	@ApiMethod(name = "calendar.addCalendar", path="calendar.addCalendar", httpMethod = "post")
	public Calendar addCalendar(@Named("description") String description, @Named("eventOwner") Owner owner) {
		Calendar res = new Calendar(description, owner);
		calDao.save(res);
	    return res; 
	}
	
	@ApiMethod(name = "calendar.listEventAttributes")
	public List<EventAttribute> listEventAttributes(@Named("calendarId") String calendarId) {
		Long l = new Long(calendarId);
		Calendar cal =  calDao.find(l);
		List<EventAttribute> eventAttributes = eventAttributeDao.findEventAttributesByCalendar(cal);
	    return eventAttributes;
	}
	
	@ApiMethod(name = "calendar.listEventCategories")
	public List<EventCategory> listEventCategories(@Named("calendarId") String calendarId) {
		Long l = new Long(calendarId);
		Calendar cal =  calDao.find(l);
		List<EventCategory> eventCategories = eventCategoryDao.findEventCategorysByCalendar(cal);
	    return eventCategories;
	}
	
	@ApiMethod(name = "calendar.listEventRepeatType")
	public List<EventRepeatType> listEventRepeatTypes(@Named("calendarId") String calendarId) {
		return Arrays.asList(EventRepeatType.values());
	}*/
}