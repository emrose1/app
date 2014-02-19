package com.studio.bookings.dao;

import static com.studio.bookings.util.OfyService.ofy;

import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.ObjectifyService;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.EventAttribute;
import com.studio.bookings.entity.EventCategory;
import com.studio.bookings.entity.Owner;

public class EventAttributeDao {
	
	static{
		ObjectifyService.register(EventAttribute.class);
	}


	public EventAttribute find(Long eventAttributeId, Calendar calendar) throws NotFoundException {
		return ofy().load().type(EventAttribute.class).parent(calendar).id(eventAttributeId).safe();
	}
	
	public EventAttribute findByName(String eventAttributeName, Calendar cal) throws NotFoundException {
		return (EventAttribute) ofy().load().ancestor(cal).filterKey("name", eventAttributeName).first().safe();
	}
	
	public EventAttribute getEventAttribute(Key<EventAttribute> eventAttributeKey) throws NotFoundException {
		return ofy().load().key(eventAttributeKey).safe();
	}

	public Key<EventAttribute> save(EventAttribute eventAttribute) {
		return ofy().save().entity(eventAttribute).now();
	}

	public List<EventAttribute> findAll() {
		List<EventAttribute> eventAttributes = ofy().load().type(EventAttribute.class).list();
		return eventAttributes;
	}
	
	public List<EventAttribute> findEventAttributesByCalendar(Calendar calendar) {
		return ofy().load().type(EventAttribute.class).ancestor(calendar.getKey()).list();
	}

}
