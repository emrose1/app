package com.studio.bookings.dao;

import static com.studio.bookings.util.OfyService.ofy;

import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.ObjectifyService;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.Event;
import com.studio.bookings.entity.EventAttribute;

public class EventDao {

	static{
		ObjectifyService.register(Event.class);
	}

	public Key<Event> save(Event event) {
		Key<Event> eventKey = ofy().save().entity(event).now();
		return eventKey;
	}
	
	public Event find(Long eventId, Calendar calendar) throws NotFoundException {
		return ofy().load().type(Event.class).parent(calendar).id(eventId).safe();
	}
	
	public Event getEvent(Key<Event> eventKey) throws NotFoundException {
		return ofy().load().key(eventKey).safe();
	}

	public List<Event> findAll() {
		List<Event> events = ofy().load().type(Event.class).list();

		return events;
	}
	
	public List<Event> findEventsByCalendar(Calendar calendar) {
		return ofy().load().type(Event.class).ancestor(calendar.getKey()).list();
	}
	
	

}
