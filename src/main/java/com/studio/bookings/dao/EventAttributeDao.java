package com.studio.bookings.dao;

import static com.studio.bookings.util.OfyService.ofy;

import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.EventAttribute;

public class EventAttributeDao {
	
	static{
		ObjectifyService.register(EventAttribute.class);
	}


	public EventAttribute find(Long eventAttributeId) {
		EventAttribute eventAttribute;
		Key<EventAttribute> rootKey = Key.create(EventAttribute.class, eventAttributeId);
		eventAttribute = ofy().load().key(rootKey).now();
		return eventAttribute;
	}

	public EventAttribute save(EventAttribute eventAttribute) {
		ofy().save().entity(eventAttribute).now();
		return eventAttribute;
	}

	public List<EventAttribute> findAll() {
		List<EventAttribute> eventAttributes = ofy().load().type(EventAttribute.class).list();
		return eventAttributes;
	}
	
	public List<EventAttribute> findEventsAttributesByCalendar(Calendar calendar) {
		return ofy().load().type(EventAttribute.class).ancestor(calendar.getKey()).list();
	}


}
