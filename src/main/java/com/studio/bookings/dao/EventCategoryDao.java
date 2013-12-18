package com.studio.bookings.dao;

import static com.studio.bookings.util.OfyService.ofy;

import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.EventCategory;

public class EventCategoryDao {
	
	static{
		ObjectifyService.register(EventCategory.class);
	}

	public EventCategory find(Long eventCategoryId) {
		EventCategory eventCategory;
		Key<EventCategory> rootKey = Key.create(EventCategory.class, eventCategoryId);
		eventCategory = ofy().load().key(rootKey).now();
		return eventCategory;
	}

	public EventCategory save(EventCategory eventCategory) {
		ofy().save().entity(eventCategory).now();
		return eventCategory;
	}

	public List<EventCategory> findAll() {
		List<EventCategory> eventCategorys = ofy().load().type(EventCategory.class).list();
		return eventCategorys;
	}
	
	public List<EventCategory> findEventsCategorysByCalendar(Calendar calendar) {
		return ofy().load().type(EventCategory.class).ancestor(calendar.getKey()).list();
	}


}