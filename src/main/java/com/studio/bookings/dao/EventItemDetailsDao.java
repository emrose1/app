package com.studio.bookings.dao;

import static com.studio.bookings.util.OfyService.ofy;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Result;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.Event;
import com.studio.bookings.entity.EventItem;
import com.studio.bookings.entity.EventItemDetails;

public class EventItemDetailsDao {
	
	static{
		ObjectifyService.register(EventItemDetails.class);
	}

	public Key<EventItemDetails> save(EventItemDetails eventItemDetails) {
		return ofy().save().entity(eventItemDetails).now();
	}
	
	public Result<Map<Key<EventItemDetails>, EventItemDetails>> saveCollection(List<EventItemDetails> EventItemCollection) {
		return ofy().save().entities(EventItemCollection);
	}
	
	public EventItemDetails getEventItemDetails(Key<EventItemDetails> eventItemDetailsKey) throws NotFoundException {
		return ofy().load().key(eventItemDetailsKey).safe();
	}

	public List<EventItemDetails> findAll() {
		List<EventItemDetails> events = ofy().load().type(EventItemDetails.class).list();
		return events;
	}

	public List<EventItemDetails> findEventItemsByCalendar(Calendar calendar) {
		return ofy().load().type(EventItemDetails.class).ancestor(calendar.getKey()).list();
	}
}