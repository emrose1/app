package com.studio.bookings.dao;

import static com.studio.bookings.util.OfyService.ofy;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Result;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.EventItem;

public class EventItemDao {
	
	static{
		ObjectifyService.register(EventItem.class);
	}

	public Key<EventItem> save(EventItem EventItem) {
		return ofy().save().entity(EventItem).now();
	}
	
	public Map<Key<EventItem>, EventItem> saveCollection(List<EventItem> eventItemCollection) {
		Map<Key<EventItem>, EventItem> map = ofy().save().entities(eventItemCollection).now();
		return map;
	}

	public List<EventItem> findAll() {
		List<EventItem> events = ofy().load().type(EventItem.class).list();

		return events;
	}

	public List<EventItem> findEventItemsByCalendar(Calendar calendar) {
		return ofy().load().type(EventItem.class).order("startDateTime").ancestor(calendar.getKey()).list();
	}
	
	public List<EventItem> findEventItemsByCalendar(Calendar calendar, Date beginDate, Date endDate) {
		return ofy().load().type(EventItem.class).ancestor(calendar)
			.filter("startDateTime >=", beginDate)
			.filter("startDateTime <", endDate).list();
	}
}