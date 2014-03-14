package com.studio.bookings.dao;

import static com.studio.bookings.util.OfyService.ofy;

import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.ObjectifyService;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.Calendar;

public class CalendarDao {

	static{
		ObjectifyService.register(Calendar.class);
	}

	public Calendar getCalendarById(Long calendarId, Account account) throws NotFoundException {
		return ofy().load().type(Calendar.class).parent(account).id(calendarId).safe();
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
	
	public List<Calendar> getCalendarsByAccount(Account account) {
		return ofy().load().type(Calendar.class).ancestor(account.getKey()).list();
	}
}
