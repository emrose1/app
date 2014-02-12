package com.studio.bookings.dao;

import static com.studio.bookings.util.OfyService.ofy;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.Owner;

public class CalendarDao {

	static{
		ObjectifyService.register(Calendar.class);
	}

	public Calendar getCalendarById(Long calendarId) throws NotFoundException {
		return ofy().load().type(Calendar.class).id(calendarId).safe();
	}

	public Key<Calendar> save(Calendar calendar) {
		return ofy().save().entity(calendar).now();
	}
	
	public Calendar getCalendarByKey(Key<Calendar> calendarKey) throws NotFoundException {
		return ofy().load().key(calendarKey).safe();
	}

	public List<Calendar> findAll() {
		List<Calendar> calendars = ofy().load().type(Calendar.class).list();
		return calendars;
	}
	
	public List<Calendar> getCalendarsByOwner(Owner owner) {
		List<Ref<Calendar>> calendarRefs = owner.getCalendars();
		List<Calendar> results = new ArrayList<Calendar>();
		for (Ref<Calendar> cal : calendarRefs) {
			Calendar calFetched = cal.get();
			results.add(calFetched);
		}
		return results;
	}

}
