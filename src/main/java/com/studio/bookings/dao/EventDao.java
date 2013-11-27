package com.studio.bookings.dao;

import static com.studio.bookings.util.OfyService.ofy;

import java.util.List;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.Event;
import com.studio.bookings.entity.LessonEvent;

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
		/*for(Event event:events){
			System.out.println(event.getCalendar());
		}*/
		return events;
	}
	
	/*public List<Event> findEventsOfCalendar(Ref<Calendar> calendar) {
		List<Event> events = ofy().load().type(Event.class).ancestor(calendar).list();
		return events;
	}*/
	
}
