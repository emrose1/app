package com.studio.bookings.dao;

import static com.studio.bookings.util.OfyService.ofy;

import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.EventCategory;

public class EventCategoryDao {
	
	static{
		ObjectifyService.register(EventCategory.class);
	}

	public EventCategory find(Long eventCategoryId, Calendar cal) throws NotFoundException {
		return ofy().load().type(EventCategory.class).parent(cal).id(eventCategoryId).safe();
	}
	
	public EventCategory findByName(String eventCategoryName, Calendar cal) throws NotFoundException {
		EventCategory ec =  ofy().load().type(EventCategory.class).ancestor(cal.getKey()).filter("name", eventCategoryName).first().now();
		return ec;
	}
	
	public EventCategory getEventCategory(Key<EventCategory> eventCategoryKey) throws NotFoundException {
		return ofy().load().key(eventCategoryKey).safe();
	}

	public Key<EventCategory> save(EventCategory eventCategory) {
		return ofy().save().entity(eventCategory).now();
	}

	public List<EventCategory> findAll() {
		List<EventCategory> eventCategorys = ofy().load().type(EventCategory.class).list();
		return eventCategorys;
	}
	
	public List<EventCategory> findEventCategorysByCalendar(Calendar calendar) {
		return ofy().load().type(EventCategory.class).ancestor(calendar.getKey()).list();
	}


}