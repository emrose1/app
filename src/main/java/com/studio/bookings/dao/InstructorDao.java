package com.studio.bookings.dao;

import static com.studio.bookings.util.OfyService.ofy;

import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.ObjectifyService;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.Instructor;

public class InstructorDao {
	
	static{
		ObjectifyService.register(Instructor.class);
	}

	public Instructor find(Long instructorId, Calendar cal) throws NotFoundException {
		return ofy().load().type(Instructor.class).parent(cal).id(instructorId).safe();
	}
	
	public Instructor findByName(String instructorName, Calendar cal) throws NotFoundException {
		Instructor instructor =  ofy().load().type(Instructor.class).ancestor(cal.getKey()).filter("name", instructorName).first().now();
		return instructor;
	}
	
	public Instructor getInstructor(Key<Instructor> instructorKey) throws NotFoundException {
		return ofy().load().key(instructorKey).safe();
	}

	public Key<Instructor> save(Instructor instructor) {
		return ofy().save().entity(instructor).now();
	}

	public List<Instructor> findAll() {
		List<Instructor> instructor = ofy().load().type(Instructor.class).list();
		return instructor;
	}
	
	public List<Instructor> findInstructorByCalendar(Instructor instructor) {
		return ofy().load().type(Instructor.class).ancestor(instructor.getKey()).list();
	}


}