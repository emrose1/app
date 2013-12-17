package com.studio.bookings.dao;

import static com.studio.bookings.util.OfyService.ofy;

import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.studio.bookings.entity.Calendar;

public class CalendarDao {

	static{
		ObjectifyService.register(Calendar.class);
	}

	//Not a very good practice
	//public EventDao eventDao = new EventDao();


	public Calendar find(Long calendarId) {
		Calendar cal =  new Calendar();
		Key<Calendar> rootKey = Key.create(Calendar.class,calendarId);
		cal = ofy().load().key(rootKey).now();
		return cal;
	}

	public Calendar save(Calendar calendar) {
		ofy().save().entity(calendar).now();
		return calendar;
	}

	public List<Calendar> findAll() {
		List<Calendar> calendars = ofy().load().type(Calendar.class).list();
/*		for(Calendar calendar:calendars){
			fillAllChildDetails(calendar);
		}*/
		return calendars;
	}

/*	private void fillAllChildDetails(Calendar calendar){
		List<Key<Event>> events = eventDao.findEventsOfCalendar(calendar);
		calendar.setEvents(events);

	}*/
}
