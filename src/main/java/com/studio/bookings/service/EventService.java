package com.studio.bookings.service;

import static com.studio.bookings.util.OfyService.ofy;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Nullable;
import com.studio.bookings.dao.CalendarDao;
import com.studio.bookings.dao.EventDao;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.Event;
import com.studio.bookings.util.Constants;

/**
 * Defines v1 of a booking API, which provides simple "greeting" methods.
 */
@Api(
    name = "booking",
    version = "v1",
    scopes = {Constants.EMAIL_SCOPE},
    clientIds = {Constants.WEB_CLIENT_ID},
    audiences = {Constants.ANDROID_AUDIENCE}
)

public class EventService {
	
	public static ArrayList<Event> events = new ArrayList<Event>();
	public static EventDao eventDao = new EventDao();
	public static CalendarDao calDao = new CalendarDao();
	
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
			@Named("startDate") String startDate,
			@Named("startTime") String startTime,
			@Named("endDate") String endDate,
			@Named("endTime") String endTime,
			@Named("maxAttendees") String maxAttendees
			) throws ParseException {
		
		Long calId = new Long(calendarId);
		Calendar cal =  calDao.find(calId);
		DateFormat formatter = new SimpleDateFormat("EEE MMM d yyyy HH:mm");
		String endDateTime = endDate + " " + endTime;
		String startDateTime = startDate + " " + startTime;
		Date eventStartDateTime = formatter.parse(startDateTime);
		Date eventEndDateTime = formatter.parse(endDateTime);
		Integer eventMaxAttendees = new Integer(maxAttendees);
		Event response = new Event(organizer, summary, eventStartDateTime, eventEndDateTime, eventMaxAttendees, cal);
		
	    eventDao.save(response);
	    return response;
	}
	
	@ApiMethod(name = "calendar.addCalendar", path="calendar.addCalendar", httpMethod = "post")
	public Calendar addCalendar(@Named("description") String description) {
		Calendar res = new Calendar(description);
		calDao.save(res);
	    return res; 
	}
}