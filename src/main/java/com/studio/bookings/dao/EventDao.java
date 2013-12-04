package com.studio.bookings.dao;

import static com.studio.bookings.util.OfyService.ofy;

import java.util.List;

import com.googlecode.objectify.ObjectifyService;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.Event;

public class EventDao {

	static{
		ObjectifyService.register(Event.class);
	}

	public Event save(Event event) {
		ofy().save().entity(event).now();
		return event;
	}

	public List<Event> findAll() {
		List<Event> events = ofy().load().type(Event.class).list();

		return events;
	}
	
	public List<Event> findEventsByCalendar(Calendar calendar) {
		return ofy().load().type(Event.class).ancestor(calendar.getKey()).list();
	}

}
