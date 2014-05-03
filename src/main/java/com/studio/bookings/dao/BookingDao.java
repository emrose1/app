package com.studio.bookings.dao;

import static com.studio.bookings.util.OfyService.ofy;

import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.studio.bookings.entity.Booking;
import com.studio.bookings.entity.Event;
import com.studio.bookings.entity.Person;

public class BookingDao {
	
	static{
		ObjectifyService.register(Booking.class);
	}
	
	public Booking find(Long bookingId) {
		Booking booking;
		Key<Booking> rootKey = Key.create(Booking.class, bookingId);
		booking = ofy().load().key(rootKey).now();
		return booking;
	}

	public Booking save(Booking booking) {
		ofy().save().entity(booking).now();
		return booking;
	}

	public List<Booking> findAll() {
		List<Booking> bookings = ofy().load().type(Booking.class).list();
		return bookings;
	}
	
	public List<Booking> getBookingsByUser(Person user) {
		return ofy().load().type(Booking.class).ancestor(user).list();
	}
	
	public List<Booking> getBookingsByEvent(Event event) {
		return ofy().load().type(Booking.class).filter("eventRef", event).list();
	}

}
